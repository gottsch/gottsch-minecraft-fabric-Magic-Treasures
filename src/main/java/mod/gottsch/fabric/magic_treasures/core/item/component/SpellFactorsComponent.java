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
package mod.gottsch.fabric.magic_treasures.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryGemstoneTiers;
import mod.gottsch.fabric.magic_treasures.core.registry.GemstoneRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/15/2024
 */
public record SpellFactorsComponent(double spellCostFactor, double spellEffectAmountFactor,
                                    double spellFrequencyFactor, double spellDurationFactor,
                                    double spellCooldownFactor, double spellRangeFactor) {

    public static final Codec<SpellFactorsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("spellCostFactor").forGetter(SpellFactorsComponent::spellCostFactor),
            Codec.DOUBLE.fieldOf("spellEffectAmountFactor").forGetter(SpellFactorsComponent::spellEffectAmountFactor),
            Codec.DOUBLE.fieldOf("spellFrequencyFactor").forGetter(SpellFactorsComponent::spellFrequencyFactor),
            Codec.DOUBLE.fieldOf("spellDurationFactor").forGetter(SpellFactorsComponent::spellDurationFactor),
            Codec.DOUBLE.fieldOf("spellCooldownFactor").forGetter(SpellFactorsComponent::spellCooldownFactor),
            Codec.DOUBLE.fieldOf("spellRangeFactor").forGetter(SpellFactorsComponent::spellRangeFactor)
            ).apply(instance, SpellFactorsComponent::new));

    public double modifySpellCost(double cost) {
        return cost * spellCostFactor;
    }
    public double modifyEffectAmount(double amount) {
        return amount * spellEffectAmountFactor;
    }
    public int modifyDuration(int duration) {
        return (int)(duration * spellDurationFactor);
    }
    public long modifyCooldown(long cooldown) {
        return (long)(cooldown * spellCooldownFactor);
    }
    public long modifyFrequency(long frequency) {
        return (long)(frequency * spellFrequencyFactor);
    }
    public double modifyRange(double range) {
        return range * spellRangeFactor;
    }

    public static class Builder {
        public double spellCostFactor = -1.0;
        public double spellEffectAmountFactor = -1.0;
        public double spellFrequencyFactor = -1.0;
        public double spellDurationFactor = -1.0;
        public double spellCooldownFactor = -1.0;
        public double spellRangeFactor = -1.0;

        // transient
        private JewelryMaterial material;
        private JewelryStoneTier stoneTier;

        public Builder(JewelryMaterial material) {
            this.material = material;
        }

        public Builder(JewelryMaterial material, Identifier stoneName) {
            // get the stone and stone tier
            Item stone = GemstoneRegistry.get(stoneName).orElse(Items.AIR);
            // determine the tier
            Optional<JewelryStoneTier> stoneTier = GemstoneRegistry.getStoneTier(stone);
            this.material = material;
            this.stoneTier = stoneTier.orElse(JewelryGemstoneTiers.NONE);
        }

        public Builder(JewelryMaterial material, JewelryStoneTier stoneTier) {
            this.material = material;
            this.stoneTier = stoneTier;
        }

        public SpellFactorsComponent.Builder with(Consumer<SpellFactorsComponent.Builder> builder) {
            builder.accept(this);
            return this;
        }

        public SpellFactorsComponent build() {

            // spell factor calculations
            if (this.spellCostFactor < 0) {
                this.spellCostFactor = calcSpellCostFactor();
            }

            if (this.spellCooldownFactor < 0) {
                this.spellCooldownFactor = calcSpellCooldownFactor();
            }

            if (this.spellDurationFactor < 0) {
                this.spellDurationFactor = calcSpellDurationFactor();
            }

            if (this.spellEffectAmountFactor < 0) {
                this.spellEffectAmountFactor = calcSpellEffectAmountFactor();
            }

            if (this.spellFrequencyFactor < 0) {
                this.spellFrequencyFactor = calcSpellFrequencyFactor();
            }

            if (this.spellRangeFactor < 0) {
                this.spellRangeFactor = calcSpellRangeFactor();
            }

            return new SpellFactorsComponent(spellCostFactor, spellEffectAmountFactor,
                    spellFrequencyFactor, spellDurationFactor,
                    spellDurationFactor, spellRangeFactor
                    );
        }

        private double calcSpellCostFactor() {
            double materialModifier = material.getSpellCostFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellCostFactor() : 1);
        }

        private double calcSpellEffectAmountFactor() {
            double materialModifier = material.getSpellEffectAmountFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellEffectAmountFactor() : 1);
        }

        private double calcSpellDurationFactor() {
            double materialModifier = material.getSpellDurationFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellDurationFactor() : 1);
        }

        private double calcSpellCooldownFactor() {
            double materialModifier = material.getSpellCooldownFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellCooldownFactor() : 1);
        }

        private double calcSpellFrequencyFactor() {
            double materialModifier = material.getSpellFrequencyFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellFrequencyFactor() : 1);
        }

        private double calcSpellRangeFactor() {
            double materialModifier = material.getSpellRangeFactor();
            return materialModifier * (stoneTier != null ? stoneTier.getSpellRangeFactor() : 1);
        }
    }
}
