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

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 5/29/2023
 */
public class Jewelry extends Item implements IJewelry{
    // TODO if Jewelry is truely to be data-driven ie any item from any mod
    // can be registered as a Jewelry item, then these properties need to be components
    // as well and there isn't any custom methods to Jewelry.
    // TODO need another name property like boolean useItemName so that an item name isn't constructed
    // TODO affixers need a registery and only save the identifier in a component
    private String baseName;
    private Predicate<ItemStack> affixer = p -> true;

    private String loreKey;

    public Jewelry(Item.Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if(stack.contains(MagicTreasuresComponents.SPELLS)) {
            if (!stack.get(MagicTreasuresComponents.SPELLS).spellNames().isEmpty()) {
                return true;
            }
        }
        return super.hasGlint(stack);
    }

    @Override
    public Text getName(ItemStack itemStack) {
        if (isNamed()) {
            return Text.translatable(this.getTranslationKey(itemStack)).formatted(Formatting.YELLOW);
        } else {
            return Text.translatable(this.getTranslationKey(itemStack));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable(LangUtil.NEWLINE));

        // hide when [shift]
        LangUtil.appendHideableHoverText(tooltip, tt -> {
            tooltip.add(Text.translatable(LangUtil.tooltip("jewelry.usage")).formatted(Formatting.GOLD).formatted(Formatting.ITALIC));
            tooltip.add(Text.translatable(LangUtil.NEWLINE));

            if (StringUtils.isNotBlank(getLoreKey())) {
                appendLoreHoverText(stack, context, tooltip, type);
            }
        });

        // add component tooltips
        Optional<JewelryAttribsComponent> attribsComponent = ComponentHelper.attribs(stack);
        Optional<MaxLevelComponent> maxLevelComponent = ComponentHelper.maxLevel(stack);
        Optional<UsesComponent> usesComponent = ComponentHelper.uses(stack);
        Optional<ManaComponent> manaComponent = ComponentHelper.mana(stack);

        // spell max level
        if (maxLevelComponent.isPresent()) {
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.max_level"),
                    Formatting.GOLD + String.valueOf(maxLevelComponent.get().maxLevel()))));
        }

        if (usesComponent.isPresent()) {
            // durability
            if (usesComponent.get().isInfinite()) {
                tooltip.add(Text.translatable(LangUtil.INDENT2)
                        .append(Text.translatable(LangUtil.tooltip("jewelry.durability.infinite"), Text.translatable(LangUtil.tooltip("infinite")).formatted(Formatting.GRAY))));
            } else {
                tooltip.add(Text.translatable(LangUtil.INDENT2)
                        .append(Text.translatable(LangUtil.tooltip("jewelry.durability.amount"),
                                Formatting.GRAY + String.valueOf(usesComponent.get().uses()),
                                Formatting.GRAY + String.valueOf(usesComponent.get().maxUses()))));
            }
        }

        // mana
        if (manaComponent.isPresent()) {

            tooltip.add(Text.translatable(LangUtil.INDENT2)
                    .append(Text.translatable(LangUtil.tooltip("jewelry.mana"),
                            Formatting.BLUE + String.valueOf(Math.toIntExact(Math.round(manaComponent.get().mana()))),
                            Formatting.BLUE + String.valueOf(Math.toIntExact((long) Math.ceil(manaComponent.get().maxMana()))))));
            // + getUsesGauge().getString())));
        }

        // TODO update like the above code
        if (stack.contains(MagicTreasuresComponents.SPELLS)) {
            SpellsComponent spells = stack.get(MagicTreasuresComponents.SPELLS);

            if (!spells.spellNames().isEmpty()) {
                tooltip.add(Text.translatable(LangUtil.NEWLINE));
                tooltip.add(Text.translatable(LangUtil.INDENT2)
                        .append(Text.translatable(LangUtil.tooltip("divider")).formatted(Formatting.GRAY)));

                // add spells
                for (Identifier spellIdentifier : spells.spellNames()) {
                    // get the spell
                    Optional<ISpell> spell = SpellRegistry.get(spellIdentifier);
                    spell.ifPresent(iSpell -> iSpell.addInformation(stack, context, tooltip, type));
                }
            }
        }

        // -----------
        MutableText component = Text.translatable(LangUtil.INDENT2);
        Optional<MutableText> c = Optional.empty();
        Optional<SpellFactorsComponent> spellFactors = ComponentHelper.spellFactors(stack);
        if (spellFactors.isPresent()) {
            if (spellFactors.get().spellCostFactor() != 1.0) {
                c = Optional.of(component);
                component.append(Text.translatable(LangUtil.tooltip("jewelry.stats.cost_factor"), Formatting.AQUA + formatStat(spellFactors.get().spellCostFactor())))
                        .append(" ");
            }
            if (spellFactors.get().spellCooldownFactor() != 1.0) {
                c = c.isEmpty() ? Optional.of(component) : c;
                component.append(Text.translatable(LangUtil.tooltip("jewelry.stats.cooldown_factor"), Formatting.AQUA + formatStat(spellFactors.get().spellCooldownFactor())))
                        .append(" ");
            }
            if (spellFactors.get().spellEffectAmountFactor() != 1.0) {
                c = c.isEmpty() ? Optional.of(component) : c;
                component.append(Text.translatable(LangUtil.tooltip("jewelry.stats.effect_amount_factor"), Formatting.AQUA + formatStat(spellFactors.get().spellEffectAmountFactor())))
                        .append(" ");
            }
            if (spellFactors.get().spellFrequencyFactor() != 1.0) {
                c = c.isEmpty() ? Optional.of(component) : c;
                component.append(Text.translatable(LangUtil.tooltip("jewelry.stats.frequency_factor"), Formatting.AQUA + formatStat(1.0 + (1.0 - spellFactors.get().spellFrequencyFactor()))))
                        .append(" ");
            }
            if (spellFactors.get().spellRangeFactor() != 1.0) {
                c = c.isEmpty() ? Optional.of(component) : c;
                component.append(Text.translatable(LangUtil.tooltip("jewelry.stats.range_factor"), Formatting.AQUA + formatStat(spellFactors.get().spellRangeFactor())))
                        .append(" ");
            }
        }
        c.ifPresent(x -> {
            tooltip.add(Text.translatable(LangUtil.NEWLINE));
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("divider")).formatted(Formatting.GRAY)));
            tooltip.add(component);
        });
        // ------------

        // advanced tooltip (hold shift)
        LangUtil.appendAdvancedHoverText(tooltip, tt -> {
            tooltip.add(Text.translatable(LangUtil.NEWLINE));
            // material
            attribsComponent.ifPresent(jewelryAttribsComponent -> tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.material"), Formatting.GREEN + WordUtils.capitalizeFully(jewelryAttribsComponent.material().getPath())))));
            // stones
            if (ComponentHelper.hasGemstone(stack)) {
                tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.stone"), Formatting.YELLOW + WordUtils.capitalizeFully(ModUtil.getName(ComponentHelper.gemstoneItem(stack).get()).getPath().replace("_", " ")))));
            }
            if (!usesComponent.map(UsesComponent::isInfinite).orElse(false)) {
                ComponentHelper.repairs(stack).ifPresent(rc -> tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.durability.repairs"), Formatting.GRAY + String.valueOf(ComponentHelper.repairsValue(stack).get())))));
            }
            ComponentHelper.recharges(stack).ifPresent(rc -> tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.mana.recharges"), Formatting.BLUE + String.valueOf(ComponentHelper.rechargesValueOrDefault(stack, 0))))));
            tooltip.add(Text.translatable(LangUtil.NEWLINE));

//            appendSpecialHoverText(stack, level, tooltip, flag);
        });

    }

//    @Override
//    public void appendSpecialHoverText(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType flag) {
//        // TODO this might be moot as this can't be anonymously set because a Handler class is instantiated by a Builder.
//    }

    @Override
    public void appendLoreHoverText(ItemStack stack, TooltipContext level, List<Text> tooltip, TooltipType flag) {

        // lore may be multiple lines, so separate on \n and add to tooltip
        Text lore = Text.translatable(LangUtil.tooltip(getLoreKey()));
        for (String s : lore.getString().split("~")) {
            tooltip.add(Text.literal(LangUtil.INDENT2)
                    .append(Text.translatable(s))
                    .formatted(Formatting.DARK_AQUA).formatted(Formatting.ITALIC));
        }
        tooltip.add(Text.literal(LangUtil.NEWLINE));
    }

    private String formatStat(double value) {
        if (value < 1.0) {
            return LangUtil.negativePercent(value);
        } else if (value > 1.0) {
            return LangUtil.positivePercent(value);
        }
        return "";
    }

    @Deprecated
    public static boolean hasStone(ItemStack stack) {
        return attribs(stack).map(a -> {
            if (a.gemstone() != null) {
                Item stoneItem = StoneRegistry.get(a.gemstone()).orElse(Items.AIR);
                return stoneItem != null
                        && stoneItem != Items.AIR
                        && stoneItem.getRegistryEntry().isIn(MagicTreasuresTags.Items.STONES);
            }
            return false;
        }).orElse(false);
    }

    /*
     * component accessors
     */
    @Deprecated
    // attribs
    public static Optional<JewelryAttribsComponent> attribs(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.JEWELRY_ATTRIBS));
    }

    @Deprecated
    public static Optional<IJewelryType> jewelryType(ItemStack stack) {
        return attribs(stack).flatMap(a -> MagicTreasuresApi.getJewelryType(a.type()));
    }
    @Deprecated
    // TODO make convenience methods for all the component properties
    public static Optional<Item> gemstone(ItemStack stack) {
        return attribs(stack).flatMap(a -> StoneRegistry.get(a.gemstone()));
    }

    @Deprecated
    // max level
    public static Optional<MaxLevelComponent> maxLevel(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.MAX_LEVEL));
    }

    @Deprecated
    // uses
    public static Optional<UsesComponent> uses(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.USES));
    }

    @Deprecated
    // repairs
    public static Optional<RepairsComponent> repairs(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.REPAIRS));
    }

    @Deprecated
    // recharges
    public static Optional<RechargesComponent> recharges(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.RECHARGES));
    }

    @Deprecated
    // spells
    public static Optional<SpellsComponent> spells(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.SPELLS));
    }

    @Deprecated
    public static void setUses(ItemStack stack, int uses) {
        uses(stack).ifPresent(component -> {
            stack.set(MagicTreasuresComponents.USES, new UsesComponent(component.maxUses(), uses));
        });
    }

    @Deprecated
    public static void setGemstone(ItemStack stack, Identifier gemstone) {
        attribs(stack).ifPresent(component -> {
            stack.set(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                    new JewelryAttribsComponent(component.type(), component.sizeTier(),
                            component.material(), gemstone));
        });
    }

    @Deprecated
    public static Optional<SpellFactorsComponent> spellFactors(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.SPELL_FACTORS));
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public IJewelry setBaseName(String baseName) {
        this.baseName = baseName;
        return this;
    }

    @Override
    public IJewelry setAffixer(Predicate<ItemStack> acceptsAffixer) {
        this.affixer = acceptsAffixer;
        return this;
    }

    @Override
    public boolean acceptsAffixing(ItemStack stack) {
        return affixer.test(stack);
    }

    @Override
    public String getLoreKey() {
        return loreKey;
    }

    @Override
    public Item setLoreKey(String loreKey) {
        this.loreKey = loreKey;
        return this;
    }
}
