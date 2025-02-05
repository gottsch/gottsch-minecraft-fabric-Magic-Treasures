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

import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 5/29/2023
 */
public class Jewelry extends Item implements IJewelry{
    private String baseName;
    private IJewelryType type;
    private IJewelrySizeTier sizeTier;
    private JewelryMaterial material;
    private Identifier gemstone;
    private Predicate<ItemStack> affixer = p -> true;

    private String loreKey;

    public Jewelry(IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, Item.Settings settings) {
        this(type, sizeTier, material, null, settings);
    }

    public Jewelry(IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, Identifier gemstone, Item.Settings settings) {
        super(settings.maxCount(1));
        this.type = type == null ? JewelryType.RING : type;
        this.sizeTier = sizeTier == null ? JewelrySizeTier.REGULAR : sizeTier;
        this.material = material == null ? JewelryMaterials.GOLD : material;
        this.gemstone = gemstone;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if(stack.contains(MagicTreasuresComponents.SPELLS_COMPONENT)) {
            if (!stack.get(MagicTreasuresComponents.SPELLS_COMPONENT).spellNames().isEmpty()) {
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

        // TODO figure this out
        // add component tooltips
        if (stack.contains(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)) {
            JewelryVitalsComponent vitals = stack.get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT);

            // spell max level
            tooltip.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("jewelry.max_level"),
                    Formatting.GOLD + String.valueOf(vitals.maxLevel()))));

            // durability
            if (vitals.isInfinite()) {
                tooltip.add(Text.translatable(LangUtil.INDENT2)
                        .append(Text.translatable(LangUtil.tooltip("jewelry.durability.infinite"), Text.translatable(LangUtil.tooltip("infinite")).formatted(Formatting.GRAY))));
            } else {
                tooltip.add(Text.translatable(LangUtil.INDENT2)
                        .append(Text.translatable(LangUtil.tooltip("jewelry.durability.amount"),
                                Formatting.GRAY + String.valueOf(vitals.uses()),
                                Formatting.GRAY + String.valueOf(vitals.maxUses()))));
            }

            // mana
            tooltip.add(Text.translatable(LangUtil.INDENT2)
                    .append(Text.translatable(LangUtil.tooltip("jewelry.mana"),
                            Formatting.BLUE + String.valueOf(Math.toIntExact(Math.round(vitals.mana()))),
                            Formatting.BLUE + String.valueOf(Math.toIntExact((long)Math.ceil(vitals.maxMana()))))));
            // + getUsesGauge().getString())));

            if (stack.contains(MagicTreasuresComponents.SPELLS_COMPONENT)) {
                SpellsComponent spells = stack.get(MagicTreasuresComponents.SPELLS_COMPONENT);

                if (!spells.spellNames().isEmpty()) {
                    tooltip.add(Text.translatable(LangUtil.NEWLINE));
                    tooltip.add(Text.translatable(LangUtil.INDENT2)
                            .append(Text.translatable(LangUtil.tooltip("divider")).formatted(Formatting.GRAY)));

                    // add spells
                    for (Identifier spellIdentifier : spells.spellNames()) {
                        // get the spell
                        Optional<ISpell> spell = SpellRegistry.get(spellIdentifier);
                        if (spell.isPresent()) {
                            spell.get().addInformation(stack, context, tooltip, type);
                        }
                    }
                }
            }

        }
//        stack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).ifPresent(handler -> {
//            handler.appendHoverText(stack, level, tooltip, flag);
//        });
    }

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

    /**
     * convenience method
     * TODO should we have a default Vitals?
     * @param stack
     * @return
     */
    public static Optional<JewelryVitalsComponent> vitals(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT));
    }

    public void setMana(ItemStack stack, double mana) {
        vitals(stack).ifPresent(v -> {
            stack.set(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT,
                    new JewelryVitalsComponent(v.maxLevel(),
                    v.maxUses(), v.uses(),
                    v.maxRepairs(),
                    v.repairs(),
                    v.maxMana(),
                    mana,
                    v.maxRecharges(),
                    v.recharges()));
        });
    }

    public void setUses(ItemStack stack, int uses) {
        vitals(stack).ifPresent(v -> {
            stack.set(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT,
                    new JewelryVitalsComponent(v.maxLevel(),
                            v.maxUses(), uses,
                            v.maxRepairs(), v.repairs(),
                            v.maxMana(), v.mana(),
                            v.maxRecharges(), v.recharges()));
        });
    }

    public static Optional<SpellFactorsComponent> spellFactors(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.SPELL_FACTORS_COMPONENT));
    }

    @Override
    public IJewelryType getType() {
        return type;
    }

    @Override
    public void setType(IJewelryType type) {
        this.type = type;
    }

    @Override
    public IJewelrySizeTier getSizeTier() {
        return sizeTier;
    }

    @Override
    public void setSizeTier(IJewelrySizeTier sizeTier) {
        this.sizeTier = sizeTier;
    }

    @Override
    public JewelryMaterial getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(JewelryMaterial material) {
        this.material = material;
    }

    @Override
    public Identifier getGemstone() {
        return gemstone;
    }

    @Override
    public void setGemstone(Identifier gemstone) {
        this.gemstone = gemstone;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public void setAffixer(Predicate<ItemStack> acceptsAffixer) {
        this.affixer = acceptsAffixer;
    }

    @Override
    public boolean acceptsAffixing(ItemStack stack) {
        return affixer.test(stack);
    }

    /**
     * NOTE getNBTShareTag() and readNBTShareTag() are required to sync item capabilities server -> client. I needed this when holding charms in hands and then swapping hands
     * or having the client update when the Anvil GUI is open.
     */
//    @Override
//    public CompoundTag getShareTag(ItemStack stack) {
//        CompoundTag tag = stack.getOrCreateTag();
//        IJewelryHandler handler = stack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
//        try {
//            tag = (CompoundTag) handler.serializeNBT();
//        } catch (Exception e) {
//            MagicTreasures.LOGGER.error("Unable to write state to NBT:", e);
//        }
//        return tag;
//    }
//
//    @Override
//    public void readShareTag(ItemStack stack, @Nullable CompoundTag tag) {
//        super.readShareTag(stack, tag);
//
//        if (tag instanceof CompoundTag) {
//            IJewelryHandler handler = stack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
//            handler.deserializeNBT((CompoundTag) tag);
//        }
//    }


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
