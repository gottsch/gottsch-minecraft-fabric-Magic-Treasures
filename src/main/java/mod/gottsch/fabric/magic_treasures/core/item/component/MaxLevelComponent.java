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
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;

import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
public record MaxLevelComponent(int maxLevel) {
    public static final Codec<MaxLevelComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maxLevel").forGetter(MaxLevelComponent::maxLevel)

    ).apply(instance, MaxLevelComponent::new));

    public static class Builder {
        public int maxLevel = -1;
        // transient
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;

        public Builder(JewelryMaterial material) {
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
        }

        public Builder(JewelryMaterial material, IJewelrySizeTier sizeTier) {
            this.material = material;
            this.sizeTier = sizeTier;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public MaxLevelComponent build() {
            if (this.maxLevel <= 0) {
                this.maxLevel = this.material.getMaxLevel() + this.sizeTier.getCode();
            }

            return new MaxLevelComponent(maxLevel);
        }
    }
}
