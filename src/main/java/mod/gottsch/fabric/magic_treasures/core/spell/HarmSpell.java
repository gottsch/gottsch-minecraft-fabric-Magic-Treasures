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
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * @author Mark Gottschling on June 17, 2024
 */
public class HarmSpell extends CooldownSpell {
    public static final String TYPE = "harm";
    private static final EventType REGISTERED_EVENT = EventType.PLAYER_LIVING_TICK;

    /**
     * @param builder
     */
    HarmSpell(Builder builder) {
        super(builder);
    }

    @Override
    public EventType getRegisteredEvent() {
        return REGISTERED_EVENT;
    }

    /**
     * NOTE: it is assumed that only the allowable events are calling this action.
     */
    @Override
    public SpellResult execute(World level, Random random, ICoords coords, ICastSpellContext context) {
        SpellResult result = new SpellResult();
        ManaComponent manaComponent = Jewelry.mana(context.getJewelry()).orElseThrow(IllegalStateException::new); //context.getJewelry().get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)
        SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(context.getJewelry()).orElseThrow(IllegalStateException::new);
        PlayerEntity player = context.getPlayer();
        if (manaComponent.mana() > 0 && context.getPlayer().isAlive()) {
            // get player position
            double px = player.getX();
            double py = player.getY();
            double pz = player.getZ();
            double range = spellFactorsComponent.modifyRange(getRange());

            List<MobEntity> mobs = level.getEntitiesByType(
                    TypeFilter.equals(MobEntity.class),
                    new Box(px - range, py - range, pz - range, px + range, py + range, pz + range),
                    EntityPredicates.EXCEPT_SPECTATOR);if (mobs.isEmpty()) {
                return result;
            }
            MagicTreasures.LOGGER.debug("number of mobs in range -> {}", mobs.size());

            double effectAmount = spellFactorsComponent.modifyEffectAmount(getEffectAmount());
            for (MobEntity mob : mobs) {
                boolean flag = mob.damage(level.getDamageSources().magic(), (float) effectAmount);
                if (flag) {
                    MagicTreasures.LOGGER.debug("inflict {} hp of damage. resulting health -> {}", effectAmount, mob.getHealth());
                }
                // TODO create effects
                // TODO add number of mobs to affect and break if reached - update: only going to affect 1 mob. else it is an Aura spell.
                break;
            }

            double c = applyCost(level, random, coords, context, spellFactorsComponent.modifySpellCost(getSpellCost()));
            result = new SpellResult(true, 0);
        }
        return result;
    }

    @Override
    public Text getSpellDesc() {
        return Text.translatable(LangUtil.tooltip("spell.harm.rate"),
                MathUtil.r1d(getEffectAmount()),
                MathUtil.r1d(getRange()),
                MathUtil.r1d(getCooldown() / 20.0));
    }

    @Override
    public Text getSpellDesc(ItemStack jewelry) {
        return Text.translatable(LangUtil.tooltip("spell.harm.rate"),
                MathUtil.r1d(modifyEffectAmount(jewelry)),
                MathUtil.r1d(modifyRange(jewelry)),
                MathUtil.r1d(modifyCooldown(jewelry) / 20.0));
    }

    @Override
    public Formatting getSpellLabelColor() {
        return Formatting.RED;
    }

    public static class Builder extends Spell.Builder {
        public int amplifier;

        public Builder(Identifier name, int level, IRarity rarity) {
            super(name, TYPE, level, rarity);
        }

        public Builder withAmplifier(int amplifier) {
            this.amplifier = amplifier;
            return this;
        }

        @Override
        public Spell build() {
            return new HarmSpell(this);
        }
    }
}
