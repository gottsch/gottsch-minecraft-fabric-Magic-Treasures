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
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/15/2024
 */
public record JewelryVitalsComponent(int maxLevel,
                                     int maxUses, int uses,
                                     int maxRepairs, int repairs,
                                     double maxMana, double mana,
                                     int maxRecharges, int recharges) {

    public static final Codec<JewelryVitalsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maxLevel").forGetter(JewelryVitalsComponent::maxLevel),
            Codec.INT.fieldOf("maxUses").forGetter(JewelryVitalsComponent::maxUses),
            Codec.INT.fieldOf("uses").forGetter(JewelryVitalsComponent::uses),
            Codec.INT.fieldOf("maxRepairs").forGetter(JewelryVitalsComponent::maxRepairs),
            Codec.INT.fieldOf("repairs").forGetter(JewelryVitalsComponent::repairs),
            Codec.DOUBLE.fieldOf("maxMana").forGetter(JewelryVitalsComponent::maxMana),
            Codec.DOUBLE.fieldOf("mana").forGetter(JewelryVitalsComponent::mana),
            Codec.INT.fieldOf("maxRecharges").forGetter(JewelryVitalsComponent::maxRecharges),
            Codec.INT.fieldOf("recharges").forGetter(JewelryVitalsComponent::recharges)

            ).apply(instance, JewelryVitalsComponent::new));

    public boolean isInfinite() {
        return uses == Integer.MAX_VALUE;
    }


    /*
     *
     */
    public static class Builder {
        public int maxUses = -1;
        public int uses;
        public int maxLevel = -1;
        public double maxMana = -1;
        public double mana;
        public int maxRepairs = -1;
        public int maxRecharges = -1;

        // transient
        public IJewelryType type;
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;
        public Identifier stone;


        public Builder(IJewelryType type, JewelryMaterial material) {
            this.type = type;
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
        }

        public Builder(IJewelryType type, JewelryMaterial material, IJewelrySizeTier size) {
            this.type = type;
            this.material = material;
            this.sizeTier = size;
        }

        public Builder(IJewelryType type, JewelryMaterial material, Identifier stone, IJewelrySizeTier sizeTier) {
            this.type = type;
            this.material = material;
            this.stone = stone;
            this.sizeTier = sizeTier;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public Builder withStone(Identifier stone) {
            this.stone = stone;
            return this;
        }

        public Builder setInfinite() {
            this.maxUses = Integer.MAX_VALUE;
            return this;
        }

        /**
         *
         * @return
         */
        public JewelryVitalsComponent build() {
            // get the stone and stone tier
            Item stone = StoneRegistry.get(this.stone).orElse(Items.AIR);
            // determine the tier
            Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone);

            if (this.maxUses <= 0) {
                this.maxUses = Math.round(this.material.getUses() * this.sizeTier.getUsesMultiplier());
            } else {
                this.maxUses = this.maxUses;
            }
            this.uses = this.maxUses;

            if (this.maxLevel <= 0) {
                this.maxLevel = this.material.getMaxLevel() + this.sizeTier.getCode();
            } else {
                this.maxLevel = this.maxLevel;
            }
            if (this.maxMana <= 0) {
                int mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
                this.maxMana = Math.round((this.material.getMana() + mana) * this.sizeTier.getManaMultiplier());
            } else {
                this.maxMana = this.maxMana;
            }
            this.mana = this.maxMana;

            // maxRepairs
            if (this.maxRepairs < 0) {
                this.maxRepairs = this.material.getRepairs() + this.sizeTier.getRepairs();
            }
//            else {
//                this.maxRepairs = this.maxRepairs;
//            }

            // maxRecharges
            if (this.maxRecharges < 0) {
                this.maxRecharges =
                        this.material.getRecharges() +
                                stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
            }
//            else {
//                this.maxRecharges = this.maxRecharges;
//            }

            return new JewelryVitalsComponent(
                    maxLevel,
                    maxUses, maxUses,
                    maxRepairs, maxRepairs,
                    maxMana, maxMana,
                    maxRecharges, maxRecharges);
        }

    }
}
