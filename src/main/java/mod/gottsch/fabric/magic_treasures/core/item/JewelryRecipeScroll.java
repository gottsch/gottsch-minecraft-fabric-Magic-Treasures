package mod.gottsch.fabric.magic_treasures.core.item;
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

import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/**
 * Created by Mark Gottschling on May 12, 2024
 */
public class JewelryRecipeScroll extends Item {

    public JewelryRecipeScroll(Item.Settings properties) {
        super(properties);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text>tooltip, TooltipType flag) {
        super.appendTooltip(itemStack, context, tooltip, flag);
        tooltip.add(Text.translatable(LangUtil.tooltip("jewelry_recipe_scroll.usage")).formatted(Formatting.GOLD).formatted(Formatting.ITALIC));
    }
}
