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
package mod.gottsch.fabric.magic_treasures.core.item;

import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


/**
 * Created by Mark Gottschling on May 7, 2024
 */
public class NamedJewelry extends Jewelry {
    public NamedJewelry(IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, Settings properties) {
        super(type, sizeTier, material, properties);
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((MutableText)super.getName(stack)).formatted(Formatting.YELLOW);
    }
}
