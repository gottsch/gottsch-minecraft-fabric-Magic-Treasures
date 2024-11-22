/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 5/29/2023
 */
public interface IJewelry {

    /**
     * Determines whether the jewelry is "named". ex. Gottsch's Ring of Moon.
     *
     * @return boolean is the jewelry named.
     */
    default boolean isNamed() {
        return false;
    }

    void appendLoreHoverText(ItemStack stack, Item.TooltipContext level, List<Text> tooltip, TooltipType flag);


    String getBaseName();

    IJewelry setBaseName(String baseName);

    IJewelry setAffixer(Predicate<ItemStack> acceptsAffixer);

    boolean acceptsAffixing(ItemStack stack);

    String getLoreKey();

    Item setLoreKey(String loreKey);
}
