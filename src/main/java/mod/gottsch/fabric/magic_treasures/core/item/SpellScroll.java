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

import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Gottschling on 5/04/2024
 */
public class SpellScroll extends Item {

    private ISpell spell;

    public SpellScroll(Item.Settings settings, ISpell spell) {
        super(settings);
        this.spell = spell;
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((MutableText)super.getName(stack)).formatted(getNameColor());
    }

    public Formatting getNameColor() {
        return Formatting.BLUE;
    }

    /**
     *
     */
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable(LangUtil.tooltip("spell_scroll.usage")).formatted(Formatting.GOLD).formatted(Formatting.ITALIC));
        tooltip.add(Text.translatable(LangUtil.NEWLINE));

        Optional.ofNullable(spell.getSpellDesc()).ifPresent(desc -> {
            tooltip.add(((MutableText)desc)
                    .formatted(Formatting.LIGHT_PURPLE)
                    .formatted(Formatting.ITALIC));
        });


        // advanced tooltip (hold shift)
        LangUtil.appendAdvancedHoverText(tooltip, tt -> {
            tooltip.add(Text.translatable(LangUtil.NEWLINE));
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("divider")).formatted(Formatting.GRAY)));

            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.name"), Text.translatable(LangUtil.tooltip("spell.name." + getSpell().getName().getPath())).formatted(Formatting.AQUA)))); //WordUtils.capitalizeFully(getSpell().getName().getPath().replace("_", " "))
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.level"), Formatting.GOLD + String.valueOf(getSpell().getLevel()))));
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.rarity"), Formatting.GOLD + getSpell().getRarity().getName())));
            if (spell.getSpellCost() <= 0) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.cost.varies")).formatted(Formatting.BLUE)));
            } else {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.cost"), Formatting.BLUE + MathUtil.r1d(getSpell().getSpellCost()))));
            }
            if (spell.getEffectAmount() > 0) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.effect_amount"), Formatting.RED + MathUtil.r1d(getSpell().getEffectAmount()))));
            }
            if (spell.getCooldown() > 0) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.cooldown"), Formatting.BLUE + MathUtil.r1d(getSpell().getCooldown()/20.0))));
            }
            if (spell.getFrequency() > 0) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.frequency"), Formatting.GREEN + MathUtil.r1d(getSpell().getFrequency()/20.0))));
            }
            if (spell.getRange() > 0) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("spell.range"), Formatting.AQUA + MathUtil.r1d(getSpell().getRange()))));
            }
        });
    }

    public ISpell getSpell() {
        return spell;
    }

    public void setSpell(ISpell spell) {
        this.spell = spell;
    }
}
