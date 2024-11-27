/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * Magic Treasures is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magic Treasures is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Magic Treasures.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.magic_treasures.core.spell;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.spell.cost.CostEvaluator;
import mod.gottsch.fabric.magic_treasures.core.spell.cost.ICostEvaluator;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Spells are a single instance within the mod like Blocks and Items.
 * // TODO next line is deprecated - don't need an entity
 * They can generate a SpellEntity which has individual state like BlockEntity and ItemEntity.
 */
public abstract class Spell implements ISpell {
    protected static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
    public static final int TICKS_PER_SECOND = 20;
    public static final Formatting SPELL_COLOR = Formatting.AQUA;
    public static final Formatting SPELL_DESC_COLOR = Formatting.LIGHT_PURPLE;

    // TODO getEffectAmount needs to take into account the material and stone
    // -- see CostEvaluator

    private final Identifier name;
    private final String type;
    private final int level;
    private final IRarity rarity;

    private double spellCost;
    private double effectAmount;
    private int duration;
    // TODO frequency and cooldown are mutually exclusive, so make classes for each of them
    private long frequency;
    private double range;
    private long cooldown;
    private boolean effectStackable;
    private boolean exclusive;
    private int priority;

    private ICostEvaluator costEvaluator;

    /*
     * builder constructor
     */
    public Spell(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.level = builder.level;
        this.rarity = builder.rarity;
        this.spellCost = builder.spellCost;
        this.effectAmount = builder.effectAmount;
        this.duration = builder.duration;
        this.frequency = builder.frequency;
        this.range = builder.range;
        this.cooldown = builder.cooldown;
        this.effectStackable = builder.effectStackable;
        this.exclusive = builder.exclusive;
        this.priority = builder.priority;
        this.costEvaluator = builder.costEvaluator;
    }

//    @Override
//    public SpellEntity entity() {
//        return new SpellEntity(this);
//    }

    /**
     * wrapper method that checks for the existence of a ICostEvaluator else uses cost property
     * @param amount
     * @return
     */
    public double applyCost(World level, Random random, ICoords coords, ICastSpellContext context, double amount) {

        if (getCostEvaluator() != null) {
//			Treasure.logger.debug("entity -> {} has a cost eval -> {}", entity.getClass().getSimpleName(), entity.getCostEvaluator().getClass().getSimpleName());
            return getCostEvaluator().apply(level, random, coords, context, amount);
        }
        else {
            MagicTreasures.LOGGER.debug("Spell does not have a cost eval.");
            ManaComponent manaComponent = ComponentHelper.mana(context.getJewelry()).orElseThrow(IllegalStateException::new);
            ComponentHelper.updateMana(context.getJewelry(), MathHelper.clamp(manaComponent.mana() - 1.0,  0D, manaComponent.mana()));
        }
        return amount;
    }

    public double modifySpellCost(ItemStack jewelry) {
       SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
       return factorsComponent.modifySpellCost(getSpellCost());
   }

    public double modifyEffectAmount(ItemStack jewelry) {
        SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
        return factorsComponent.modifyEffectAmount(getEffectAmount());
   }

    public long modifyCooldown(ItemStack jewelry) {
        SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
        return factorsComponent.modifyCooldown(getCooldown());
   }

    public long modifyDuration(ItemStack jewelry) {
        SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
        return factorsComponent.modifyDuration(getDuration());
    }

    public long modifyFrequency(ItemStack jewelry) {
        SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
        return factorsComponent.modifyFrequency(getFrequency());
   }

    public double modifyRange(ItemStack jewelry) {
        SpellFactorsComponent factorsComponent = getSpellFactors(jewelry);
        return factorsComponent.modifyRange(getRange());
   }

   private SpellFactorsComponent getSpellFactors(ItemStack jewelry) {
        return Jewelry.spellFactors(jewelry).orElseThrow(IllegalStateException::new);
   }

    @SuppressWarnings("deprecation")
    @Override
    public void addInformation(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType flagIn) {
        tooltip.add(getLabel());
        getDesc(stack).ifPresent(tooltip::add);
    }

    private Text getLabel() {
        MutableText label = Text.translatable(LangUtil.tooltip("spell.name.") + getName().getPath().toLowerCase());
        label.append(" ").append((this.effectStackable ? "+" : ""));
        return Text.translatable(LangUtil.INDENT2).append(label.formatted(getSpellLabelColor()).formatted(Formatting.BOLD));
    }

    // a short desc of its effect ex "Heals 1hp / 10 sec"
    private Optional<Text> getDesc(ItemStack jewelry) {
        Text desc = getSpellDesc(jewelry);
        return desc != null ? Optional.of(Text.translatable(
                LangUtil.INDENT4).append(desc)
                .formatted(Formatting.ITALIC).formatted(getSpellDescColor()))
                : Optional.empty();
    }

    // TODO would be nice if this returned Optional instead
    /**
     * Implemented by concrete Spell.
     * @return
     */
    @Override
    public Text getSpellDesc() { return null;}

    @Override
    public Text getSpellDesc(ItemStack jewelry) { return null;}

    @Override
    public Formatting getSpellLabelColor() {
        return SPELL_COLOR;
    }

    @Override
    public Formatting getSpellDescColor() {
        return SPELL_DESC_COLOR;
    }

    /**
     *
     */
    abstract public static class Builder {
        public Identifier name;
        public String type;
        public int level;
        public IRarity rarity;
        public double spellCost;
        public double effectAmount;
        public int duration;
        public long frequency;
        public double range;
        public long cooldown;
        public boolean effectStackable;
        public boolean exclusive;
        public int priority;

        public ICostEvaluator costEvaluator;

        public Builder(Identifier name, String type, int level) {
            this(name, type, level, MagicTreasuresRarity.COMMON);
        }

        public Builder(Identifier name, String type, int level, IRarity rarity) {
            this.name = name;
            this.type = type;
            this.level = level;
            this.rarity = rarity;
            this.costEvaluator = new CostEvaluator();
        }

        abstract ISpell build();

        public Builder with(Consumer<Builder> builder)  {
            builder.accept(this);
            return this;
        }
    }

    ///////////////////////////////
    @Override
    public Identifier getName() {
        return name;
    }
//
//    public void setName(Identifier name) {
//        this.name = name;
//    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public IRarity getRarity() {
        return this.rarity;
    }

    @Override
    public double getSpellCost() {
        return spellCost;
    }

    @Override
    public void setSpellCost(double spellCost) {
        this.spellCost = spellCost;
    }

    public double getEffectAmount() {
        return effectAmount;
    }

    public void setEffectAmount(double effectAmount) {
        this.effectAmount = effectAmount;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public long getFrequency() {
        return frequency;
    }

    @Override
    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    @Override
    public double getRange() {
        return range;
    }

    @Override
    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public boolean isExclusive() {
        return exclusive;
    }

    @Override
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public ICostEvaluator getCostEvaluator() {
        return costEvaluator;
    }

    @Override
    public void setCostEvaluator(ICostEvaluator costEvaluator) {
        this.costEvaluator = costEvaluator;
    }

    @Override
    public boolean isEffectStackable() {
        return effectStackable;
    }

    @Override
    public void setEffectStackable(boolean effectStackable) {
        this.effectStackable = effectStackable;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Spell{" +
                "priority=" + priority +
                ", exclusive=" + exclusive +
                ", effectStackable=" + effectStackable +
                ", cooldown=" + cooldown +
                ", range=" + range +
                ", frequency=" + frequency +
                ", duration=" + duration +
                ", effectAmount=" + effectAmount +
                ", spellCost=" + spellCost +
                ", rarity=" + rarity +
                ", level=" + level +
                ", type='" + type + '\'' +
                ", name=" + name +
                '}';
    }
}
