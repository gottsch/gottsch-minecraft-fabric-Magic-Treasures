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
 * Created by Mark Gottschling on 11/19/2024
 */
public record ManaComponent(double maxMana, double mana) {

    public static final Codec<ManaComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("maxMana").forGetter(ManaComponent::maxMana),
            Codec.DOUBLE.fieldOf("mana").forGetter(ManaComponent::mana)

    ).apply(instance, ManaComponent::new));

    public ManaComponent(double maxMana) {
        this(maxMana, maxMana);
    }

    public static class Builder {
        public double maxMana = -1;
        public double mana = -1;

        // transient
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;
        public Identifier stone;

        public Builder(JewelryMaterial material) {
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
        }

        public Builder(JewelryMaterial material, IJewelrySizeTier sizeTier) {
            this.material = material;
            this.sizeTier = sizeTier;
        }

        public Builder(JewelryMaterial material, IJewelrySizeTier sizeTier, Identifier gemstone) {
            this.material = material;
            this.sizeTier = sizeTier;
            this.stone = gemstone;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public Builder withStone(Identifier stone) {
            this.stone = stone;
            return this;
        }

        public ManaComponent build() {
            // get the stone and stone tier
            Item stone = StoneRegistry.get(this.stone).orElse(Items.AIR);
            // determine the tier
            Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone);

            if (this.maxMana <= 0) {
                int mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
                this.maxMana = Math.round((this.material.getMana() + mana) * this.sizeTier.getManaMultiplier());
            }
            this.mana = this.maxMana;

            return new ManaComponent(maxMana, mana);
        }
    }
}
