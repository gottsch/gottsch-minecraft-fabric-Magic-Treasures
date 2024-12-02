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
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 *
 */
public class HealingSpell extends Spell {
    public static String HEALING_TYPE = "healing";
    private static final EventType REGISTERED_EVENT = EventType.PLAYER_LIVING_TICK;

    /**
     *
     * @param builder
     */
    public HealingSpell(Builder builder) {
        super(builder);
    }

    public static class Builder extends Spell.Builder {
        public Builder(Identifier name, int level, IRarity rarity) {
            super(name, HEALING_TYPE, level, rarity);
        }

        @Override
        public ISpell build() {
            return new HealingSpell(this);
        }
    }

    @Override
    public EventType getRegisteredEvent() {
        return REGISTERED_EVENT;
    }

    /**
     * NOTE: it is assumed that only the allowable events are calling this action.
     */

    @Override
    public SpellResult cast(World level, Random random, ICoords coords, ICastSpellContext context) {
        SpellResult result = new SpellResult();
        ManaComponent manaComponent = ComponentHelper.mana(context.getJewelry()).orElseThrow(IllegalStateException::new);
        // TODO update the jewelry with the generic spell factor is not found OR generate a new one based on it's attribs and stone. If attribs = null then use default NONE.
        // TODO expand this for all instances where an IllegalStateException is thrown
        SpellFactorsComponent spellFactorsComponent =  ComponentHelper.spellFactors(context.getJewelry()).orElse(new SpellFactorsComponent.Builder(JewelryMaterials.NONE).build());

        if (level.getTime() % spellFactorsComponent.modifyFrequency(getFrequency()) == 0) {
            if (manaComponent.mana() > 0 && context.getPlayer().getHealth() < context.getPlayer().getMaxHealth() && context.getPlayer().isAlive()) {

                // determine the actual amount of health (0.0 -> getAmount())
                float amount = Math.min((float)spellFactorsComponent.modifyEffectAmount(getEffectAmount()), context.getPlayer().getMaxHealth() - context.getPlayer().getHealth());
                context.getPlayer().setHealth(MathHelper.clamp(context.getPlayer().getHealth() + amount, 0.0F, context.getPlayer().getMaxHealth()));
                applyCost(level, random, coords, context, spellFactorsComponent.modifySpellCost(getSpellCost()));
                result = new SpellResult(true, 0);
            }
        }
        return result;
    }

    @Override
    public Text getSpellDesc() {
        return Text.translatable(LangUtil.tooltip("spell.healing.rate"),
                MathUtil.r1d(getEffectAmount()),
                MathUtil.r1d(getFrequency()/20.0));
    }

    @Override
    public Text getSpellDesc(ItemStack jewelry) {
        return Text.translatable(LangUtil.tooltip("spell.healing.rate"),
                MathUtil.r1d(modifyEffectAmount(jewelry)),
                MathUtil.r1d(modifyFrequency(jewelry)/20.0));
    }

    @Override
    public Formatting getSpellLabelColor() {
        return Formatting.DARK_RED;
    }

}
