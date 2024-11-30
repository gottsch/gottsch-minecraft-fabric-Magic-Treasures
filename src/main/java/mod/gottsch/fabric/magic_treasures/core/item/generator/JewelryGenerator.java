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
package mod.gottsch.fabric.magic_treasures.core.item.generator;

import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mark Gottschling May 6, 2024
 *
 */
public class JewelryGenerator {
    public static final Namer STANDARD_NAMER = new Namer();

    /**
     *
     */
    public JewelryGenerator() {
    }

    public ItemStack addStone(ItemStack jewelry, ItemStack stone) {
        return addStone(jewelry, stone, STANDARD_NAMER);
    }

    public ItemStack addStone(ItemStack jewelry, ItemStack stone, Namer namer) {
        Jewelry jewelryItem = (Jewelry)jewelry.getItem();

        // ensure a valid stone stack
        if (stone == null || stone.isEmpty() || !jewelryItem.acceptsAffixing(stone)) {
            return ItemStack.EMPTY;
        }

        Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone.getItem());
        if (stoneTier.isPresent() && stoneTier.get().canAffix(jewelry)) {
            int gemMana = 0;
            int gemRecharges = 0;
            Identifier location = ModUtil.asLocation(namer.name(jewelry, stone));

            // duplicate (create new) the jewelry itemstack
            ItemStack destJewelry = JewelryRegistry.get(location).map(ItemStack::new).orElseGet(() -> new ItemStack(jewelry.getItem()));
            // NOTE that destJewelry at this point is created with the default values of the jewelry + stone version.
            // therefor, the max values don't need to be changed but the current values do.

            // ensure stone is set (in the component)
//            ComponentHelper.updateGemstone(destJewelry, ModUtil.getName(stone.getItem()));

            // TODO working for specials like castle ring to build the correct item
            // however, it is not using the correct component builder.
            // TODO need to check if it has a component builder first

            /*
             * calculate mana derived from the gem
             * NOTE only applying the sizeTier multiplier to the gem as that is the only thing changing.
             */
            IJewelrySizeTier destSizeTier = ComponentHelper.sizeTier(destJewelry).orElse(JewelrySizeTier.REGULAR);
            gemMana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
            gemMana = Math.round(gemMana * destSizeTier.getManaMultiplier());
            // TODO get min of maxMana and current+gemMana
            gemMana = (int) Math.min(ComponentHelper.maxManaValue(destJewelry).get(), ComponentHelper.manaValueOrDefault(destJewelry, 0) + gemMana);

            // TODO could use ManaComponent.Builder here instead. it will calculate the mana like above.
            // update mana
            ComponentHelper.updateMana(destJewelry, gemMana);
//        ComponentHelper.incrementMana(destJewelry, gemMana);//

            // update recharges
            gemRecharges = stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
            gemRecharges = Math.min(ComponentHelper.maxRechargesValue(destJewelry).get(), ComponentHelper.rechargesValueOrDefault(destJewelry, 0) + gemRecharges);
//        ComponentHelper.incrementRecharges(destJewelry, gemRecharges);
            ComponentHelper.updateRecharges(destJewelry, gemRecharges);

            // update repairs
            ComponentHelper.copyRepairsComponent(jewelry, destJewelry);

            // update uses and item damage
            destJewelry.set(DataComponentTypes.DAMAGE, jewelry.get(DataComponentTypes.DAMAGE));
            ComponentHelper.copyUsesComponent(jewelry, destJewelry);

            // copy spells
            ComponentHelper.copySpellsComponent(jewelry, destJewelry);

            return destJewelry;
        }

        return ItemStack.EMPTY;
    }


    public Optional<ItemStack> removeStone(ItemStack jewelry) {
        return removeStone(jewelry, STANDARD_NAMER);
    }

    public Optional<ItemStack> removeStone(ItemStack jewelry, Namer namer) {
        // TODO this is wrong. need to build the name without them gem.
        Identifier location = ModUtil.asLocation(namer.name(jewelry));
        ItemStack destJewelry = JewelryRegistry.get(location).map(ItemStack::new).orElseGet(() -> new ItemStack(jewelry.getItem()));

        if (!jewelry.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)
            || !destJewelry.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)) {
            return Optional.empty();
        }

        // get the stone item from the sourceHandler
        Item stone = ComponentHelper.gemstoneItem(jewelry).orElse(Items.AIR);

        int mana = 0;
        int recharges = 0;
        if (stone != null && stone != Items.AIR) {
            Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone);

            // remove the stone
            ComponentHelper.updateGemstone(destJewelry, ModUtil.getName(Items.AIR));

            if (stoneTier.isPresent()) {
                // get stone mana
//                mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
//                mana = Math.round(mana * destHandler.getJewelrySizeTier().getManaMultiplier());
                // calculate mana
//                IJewelrySizeTier destSizeTier = ComponentHelper.sizeTier(destJewelry).orElse(JewelrySizeTier.REGULAR);
//                mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
//                mana = Math.round(mana * destSizeTier.getManaMultiplier());

                // get stone recharges
//                recharges = stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
            }
        }

        // NOTE at this point, destHandler contains the correct max values (it is the item w/o the gem)
        // remove mana
////        destHandler.setMaxMana(sourceHandler.getMaxMana() - mana);
//        destHandler.setMana(Math.min(sourceHandler.getMana(), destHandler.getMaxMana()));
        ComponentHelper.updateMana(destJewelry,
                Math.min(ComponentHelper.manaValue(jewelry).orElse(0D), ComponentHelper.maxManaValue(destJewelry).orElse(0D)));

        // remove recharges
//        destHandler.setMaxRecharges(sourceHandler.getMaxRecharges() - recharges);
//        destHandler.setRecharges(Math.min(sourceHandler.getRecharges(), destHandler.getMaxRecharges()));
        ComponentHelper.updateRecharges(destJewelry,
                Math.min(ComponentHelper.rechargesValue(jewelry).orElse(0),
                        ComponentHelper.maxRechargesValue(destJewelry).orElse(0)));


        // update repairs
//        destHandler.setRepairs(sourceHandler.getRepairs());
        ComponentHelper.copyRepairsComponent(jewelry, destJewelry);


        // update uses and item damage
////        destJewelry.setDamageValue(jewelry.getDamageValue());
//        destHandler.setUses(sourceHandler.getUses());
        ComponentHelper.copyUsesComponent(jewelry, destJewelry);

        // copy spells
        ComponentHelper.copySpellsComponent(jewelry, destJewelry);

        return Optional.of(destJewelry);
    }

    public Optional<ItemStack> addSpells(ItemStack jewelry, ItemStack spellStack) {
        if (!spellStack.isIn(MagicTreasuresTags.Items.SPELL_SCROLLS)) return Optional.empty();

        // create a new stack based on the jewelry
        ItemStack destStack = new ItemStack(jewelry.getItem());
        // copy values from jewelry to destStack
        copyStack(jewelry, destStack);

        Optional<MaxLevelComponent> maxLevelComponent = ComponentHelper.maxLevel(destStack);
        Optional<SpellsComponent> spellsComponent = ComponentHelper.spells(destStack);
        if (maxLevelComponent.isPresent() && spellsComponent.isPresent()) {
            ISpell spell = ((SpellScroll) spellStack.getItem()).getSpell();

            if (maxLevelComponent.get().maxLevel() >= spell.getLevel()) {
                List<Identifier> spellNames = new ArrayList<>();
                spellNames.add(spell.getName());
                // NOTE need to call set() even though it is not a new component (since adding to list)
                // because there are more operations behind the scenes in set().
                destStack.set(MagicTreasuresComponents.SPELLS, new SpellsComponent(spellNames));
                return Optional.of(destStack);
            }
        }
        return Optional.empty();
    }

    private void copyStack(ItemStack sourceStack, ItemStack destStack) {
        // copy over current state
        copyDataComponents(sourceStack, destStack);

        // update the itemstack
        destStack.setDamage(sourceStack.getDamage());
    }

    private void copyDataComponents(ItemStack source, ItemStack dest) {
        if (source.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)) {
            dest.set(MagicTreasuresComponents.JEWELRY_ATTRIBS, source.get(MagicTreasuresComponents.JEWELRY_ATTRIBS));
        }
        if (source.contains(MagicTreasuresComponents.MAX_LEVEL)) {
            dest.set(MagicTreasuresComponents.MAX_LEVEL, source.get(MagicTreasuresComponents.MAX_LEVEL));
        }
        if (source.contains(MagicTreasuresComponents.USES)) {
            dest.set(MagicTreasuresComponents.USES, source.get(MagicTreasuresComponents.USES));
        }
        if (source.contains(MagicTreasuresComponents.REPAIRS)) {
            dest.set(MagicTreasuresComponents.REPAIRS, source.get(MagicTreasuresComponents.REPAIRS));
        }
        if (source.contains(MagicTreasuresComponents.MANA)) {
            dest.set(MagicTreasuresComponents.MANA, source.get(MagicTreasuresComponents.MANA));
        }
        if (source.contains(MagicTreasuresComponents.RECHARGES)) {
            dest.set(MagicTreasuresComponents.RECHARGES, source.get(MagicTreasuresComponents.RECHARGES));
        }
        if (source.contains(MagicTreasuresComponents.SPELL_FACTORS)) {
            dest.set(MagicTreasuresComponents.SPELL_FACTORS, source.get(MagicTreasuresComponents.SPELL_FACTORS));
        }
        if (source.contains(MagicTreasuresComponents.SPELLS)) {
            dest.set(MagicTreasuresComponents.SPELLS, source.get(MagicTreasuresComponents.SPELLS));
        }
    }
//    private void copyHandlers(IJewelryHandler handler, IJewelryHandler destHandler) {
//        destHandler.setStone(handler.getStone());
//
//        destHandler.setBaseName(handler.getBaseName());
//        destHandler.setMaxMana(handler.getMaxMana());
//        destHandler.setMaxRecharges(handler.getMaxRecharges());
//        destHandler.setMaxRepairs(handler.getMaxRepairs());
//        destHandler.setMana(handler.getMana());
//        destHandler.setRecharges(handler.getRecharges());
//        destHandler.setRepairs(handler.getRepairs());
//        destHandler.getSpells().addAll(handler.getSpells());
//        destHandler.setUses(handler.getUses());
//    }
//
//    public Optional<ItemStack> recharge(ItemStack jewelry) {
//        // create a new stack based on the jewelry
//        ItemStack destStack = new ItemStack(jewelry.getItem());
//        // copy values from jewelry to destStack
//        copyStack(jewelry, destStack);
//
//        IJewelryHandler handler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
//        if (handler.getRecharges() > 0) {
//            handler.setMana(handler.getMaxMana());
//            handler.setRecharges(handler.getRecharges() - 1);
//            return Optional.of(destStack);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<ItemStack> repair(ItemStack jewelry) {
//        // create a new stack based on the jewelry
//        ItemStack destStack = new ItemStack(jewelry.getItem());
//        // copy values from jewelry to destStack
//        copyStack(jewelry, destStack);
//
//        IJewelryHandler handler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
//        if (handler.getRepairs() > 0) {
//            handler.setUses(handler.getMaxUses());
//            handler.setRepairs(Math.max(0, handler.getRepairs() - 1));
//            return Optional.of(destStack);
//        }
//        return Optional.empty();
//    }

    public static class Namer {
        public String name(ItemStack jewelry, ItemStack stone) {
            StringBuilder buffer = new StringBuilder();
            Jewelry jewelryItem = (Jewelry)jewelry.getItem();
            JewelryAttribsComponent attribsComponent = ComponentHelper.attribs(jewelry).orElseThrow(IllegalStateException::new);

            String name = buffer.append((!attribsComponent.sizeTier().equals(JewelrySizeTier.REGULAR.getName()) ? (attribsComponent.sizeTier() + "_") : ""))
                    .append(attribsComponent.material().getPath()).append("_")
                    .append(ModUtil.getName(stone.getItem()).getPath()).append("_")
                    .append(StringUtils.isNotBlank(jewelryItem.getBaseName()) ? jewelryItem.getBaseName() : attribsComponent.type())
                    .toString().toLowerCase();
//                   .append(handler.getJewelryType().getName()).toString().toLowerCase();
            return name;
        }

        public String name(ItemStack jewelry) {
            StringBuilder buffer = new StringBuilder();
            Jewelry jewelryItem = (Jewelry)jewelry.getItem();
            JewelryAttribsComponent attribsComponent = ComponentHelper.attribs(jewelry).orElseThrow(IllegalStateException::new);

           String name = buffer.append((!attribsComponent.sizeTier().equals(JewelrySizeTier.REGULAR.getName()) ? (attribsComponent.sizeTier() + "_") : ""))
                    .append(attribsComponent.material().getPath()).append("_")
                    .append(
//                      jewelryItem.getJewelryType().getName()
                            StringUtils.isNotBlank(jewelryItem.getBaseName()) ? jewelryItem.getBaseName() : attribsComponent.type()
                    ).toString().toLowerCase();
            return name;
        }
    }

    // NOT IMPLEMENTED
//    public static class NamingRules {
//        private boolean useSize = true;
//        private boolean useMaterial = true;
//        private boolean useStone = true;
//        private boolean useType = true;
//
//        private boolean useBaseName;
//        private String baseName = "";
//
//        public NamingRules() {
//            // default constructor which uses the main attributes to build generic jewelry ex. gold_ring
//        }
//
//        public NamingRules(Builder builder) {
//            this.useSize = builder.useSize;
//            this.useMaterial = builder.useMaterial;
//            this.useStone = builder.useStone;
//            this.useType = builder.useType;
//            this.useBaseName = builder.useBaseName;
//            this.baseName = builder.baseName;
//        }
//
//        public static class Builder {
//            public boolean useSize = true;
//            public boolean useMaterial = true;
//            public boolean useStone = true;
//            public boolean useType = true;
//
//            public boolean useBaseName;
//            public String baseName = "";
//
//            public Builder() {}
//
//            public NamingRules build() {
//                return new NamingRules(this);
//            }
//
//            public Builder with(Consumer<Builder> builder)  {
//                builder.accept(this);
//                return this;
//            }
//        }
//
//        public boolean isUseSize() {
//            return useSize;
//        }
//
//        public void setUseSize(boolean useSize) {
//            this.useSize = useSize;
//        }
//
//        public boolean isUseMaterial() {
//            return useMaterial;
//        }
//
//        public void setUseMaterial(boolean useMaterial) {
//            this.useMaterial = useMaterial;
//        }
//
//        public boolean isUseStone() {
//            return useStone;
//        }
//
//        public void setUseStone(boolean useStone) {
//            this.useStone = useStone;
//        }
//
//        public boolean isUseType() {
//            return useType;
//        }
//
//        public void setUseType(boolean useType) {
//            this.useType = useType;
//        }
//
//        public boolean isUseBaseName() {
//            return useBaseName;
//        }
//
//        public void setUseBaseName(boolean useBaseName) {
//            this.useBaseName = useBaseName;
//        }
//
//        public String getBaseName() {
//            return baseName;
//        }
//
//        public void setBaseName(String baseName) {
//            this.baseName = baseName;
//        }
//    }
}
