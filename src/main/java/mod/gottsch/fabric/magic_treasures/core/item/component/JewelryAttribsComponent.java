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

import blue.endless.jankson.annotation.Nullable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
public record JewelryAttribsComponent(String type, String sizeTier, Identifier material, @Nullable Identifier gemstone) {
    public static final Codec<JewelryAttribsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(JewelryAttribsComponent::type),
            Codec.STRING.fieldOf("sizeTier").forGetter(JewelryAttribsComponent::sizeTier),
            Identifier.CODEC.fieldOf("material").forGetter(JewelryAttribsComponent::material),
            Identifier.CODEC.fieldOf("gemstone").forGetter(JewelryAttribsComponent::gemstone)
    ).apply(instance, JewelryAttribsComponent::new));

}
