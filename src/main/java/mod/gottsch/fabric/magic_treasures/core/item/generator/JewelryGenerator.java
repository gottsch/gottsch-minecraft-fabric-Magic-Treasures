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

import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

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
//        IJewelryHandler sourceHandler = jewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        Jewelry jewelryItem = (Jewelry)jewelry.getItem();
        JewelryVitalsComponent sourceVitals = Jewelry.vitals(jewelry).orElseThrow(IllegalStateException::new);

        // ensure a valid stone stack
        if (stone == null || stone == ItemStack.EMPTY || !jewelryItem.acceptsAffixing(stone)
            // TODO
//            || sourceHandler.getJewelryMaterial().acceptsAffixer(stone)
//                || stoneHandler.canAffix(jewelry.getItem())
        ) {
            return ItemStack.EMPTY;
        }

        Identifier location = ModUtil.asLocation(namer.name(jewelry, stone));

        ItemStack destJewelry = JewelryRegistry.get(location).map(ItemStack::new).orElseGet(() -> new ItemStack(jewelry.getItem()));

        // get the dest capability handlers
//        IJewelryHandler destHandler = destJewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        Jewelry destJewelryItem = (Jewelry)jewelry.getItem();
        JewelryVitalsComponent destVitals = Jewelry.vitals(destJewelry).orElseThrow(IllegalStateException::new);

        int mana = 0;
        int recharges = 0;

        Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone.getItem());

        if (stoneTier.isPresent() && stoneTier.get().canAffix(jewelry)) {

            // TODO this is wrong - you need to get the item that is registered
            // with this gemstone.
            // TODO FRACK! gemstone needs to be a component. it is not part of the
            // Item because you are able to create jewelry with stones that do not
            // have a specific item. ie it is data/tag driven.
            // TODO AWWW SHITE! all the jewelry attribs need to be components.
            // they are NOT part of the singleton Item!! ARG.
            // this only holds true IFF we want other mods items to be able to be used
            // as jewelry in this mod.

            // ensure stone is set
            destJewelryItem.setStone(ModUtil.getName(stone.getItem()));


            // update mana
            mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
            mana = Math.round(mana * destHandler.getJewelrySizeTier().getManaMultiplier());

            // update recharges
            recharges = stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
        }

        // update mana
        destHandler.setMaxMana(sourceHandler.getMaxMana() + mana);
        destHandler.setMana(sourceHandler.getMana() + mana);

        // update recharges
        destHandler.setMaxRecharges(sourceHandler.getMaxRecharges() + recharges);
        destHandler.setRecharges(sourceHandler.getRecharges() + recharges);

        // update repairs
        destHandler.setRepairs(sourceHandler.getRepairs());

        // update uses and item damage
        destJewelry.setDamageValue(jewelry.getDamageValue());
        destHandler.setUses(sourceHandler.getUses());

        // transfer spells
        destHandler.setSpells(sourceHandler.getSpells());

        return destJewelry;
    }

    public Optional<ItemStack> removeStone(ItemStack jewelry) {
        return removeStone(jewelry, STANDARD_NAMER);
    }

    public Optional<ItemStack> removeStone(ItemStack jewelry, Namer namer) {
        Identifier location = ModUtil.asLocation(namer.name(jewelry));
        ItemStack destJewelry = JewelryRegistry.get(location).map(ItemStack::new).orElseGet(() -> new ItemStack(jewelry.getItem()));

        // TODO get components
        IJewelryHandler sourceHandler = null;
        IJewelryHandler destHandler = null;
        try {
            sourceHandler = jewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
            destHandler = destJewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        } catch(Exception e) {
            return Optional.empty();
        }

        // get the stone item from the sourceHandler
        Item stone = ForgeRegistries.ITEMS.getValue(sourceHandler.getStone());

        int mana = 0;
        int recharges = 0;
        if (stone != null) {
            Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone);

            // remove the stone
            destHandler.setStone(null);

            if (stoneTier.isPresent()) {
                // get stone mana
                mana = stoneTier.map(JewelryStoneTier::getMana).orElseGet(() -> 0);
                mana = Math.round(mana * destHandler.getJewelrySizeTier().getManaMultiplier());

                // get stone recharges
                recharges = stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
            }
        }

        // NOTE at this point, destHandler contains the correct max values (it is the item w/o the gem)
        // remove mana
//        destHandler.setMaxMana(sourceHandler.getMaxMana() - mana);
        destHandler.setMana(Math.min(sourceHandler.getMana(), destHandler.getMaxMana()));

        // remove recharges
//        destHandler.setMaxRecharges(sourceHandler.getMaxRecharges() - recharges);
        destHandler.setRecharges(Math.min(sourceHandler.getRecharges(), destHandler.getMaxRecharges()));

        // update repairs
        destHandler.setRepairs(sourceHandler.getRepairs());

        // update uses and item damage
//        destJewelry.setDamageValue(jewelry.getDamageValue());
        destHandler.setUses(sourceHandler.getUses());

        // transfer spells
        destHandler.setSpells(sourceHandler.getSpells());

        return Optional.of(destJewelry);
    }

    public Optional<ItemStack> addSpells(ItemStack jewelry, ItemStack spellStack) {
        if (!spellStack.isIn(MagicTreasuresTags.Items.SPELL_SCROLLS)) return Optional.empty();

        // create a new stack based on the jewelry
        ItemStack destStack = new ItemStack(jewelry.getItem());
        // copy values from jewelry to destStack
        copyStack(jewelry, destStack);

        IJewelryHandler handler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        ISpell spell = ((SpellScroll)spellStack.getItem()).getSpell();
        if (handler.getMaxLevel() >= spell.getLevel()) {
            handler.getSpells().add(spell.entity());
            return Optional.of(destStack);
        }
        return Optional.empty();
    }

    private void copyStack(ItemStack sourceStack, ItemStack destStack) {
        IJewelryHandler handler = sourceStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        IJewelryHandler destHandler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);

        // copy over current state
        copyHandlers(handler, destHandler);

        // update the itemstack
        destStack.setDamageValue(sourceStack.getDamageValue());
    }

    private void copyHandlers(IJewelryHandler handler, IJewelryHandler destHandler) {
        destHandler.setStone(handler.getStone());

        destHandler.setBaseName(handler.getBaseName());
        destHandler.setMaxMana(handler.getMaxMana());
        destHandler.setMaxRecharges(handler.getMaxRecharges());
        destHandler.setMaxRepairs(handler.getMaxRepairs());
        destHandler.setMana(handler.getMana());
        destHandler.setRecharges(handler.getRecharges());
        destHandler.setRepairs(handler.getRepairs());
        destHandler.getSpells().addAll(handler.getSpells());
        destHandler.setUses(handler.getUses());
    }

    public Optional<ItemStack> recharge(ItemStack jewelry) {
        // create a new stack based on the jewelry
        ItemStack destStack = new ItemStack(jewelry.getItem());
        // copy values from jewelry to destStack
        copyStack(jewelry, destStack);

        IJewelryHandler handler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        if (handler.getRecharges() > 0) {
            handler.setMana(handler.getMaxMana());
            handler.setRecharges(handler.getRecharges() - 1);
            return Optional.of(destStack);
        }
        return Optional.empty();
    }

    public Optional<ItemStack> repair(ItemStack jewelry) {
        // create a new stack based on the jewelry
        ItemStack destStack = new ItemStack(jewelry.getItem());
        // copy values from jewelry to destStack
        copyStack(jewelry, destStack);

        IJewelryHandler handler = destStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
        if (handler.getRepairs() > 0) {
            handler.setUses(handler.getMaxUses());
            handler.setRepairs(Math.max(0, handler.getRepairs() - 1));
            return Optional.of(destStack);
        }
        return Optional.empty();
    }

    public static class Namer {
        public String name(ItemStack jewelry, ItemStack stone) {
            StringBuilder buffer = new StringBuilder();
            Jewelry jewelryItem = (Jewelry)jewelry.getItem();

//            IJewelryHandler handler = jewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
           return buffer.append((jewelryItem.getSizeTier() != JewelrySizeTier.REGULAR ? (jewelryItem.getSizeTier().getName() + "_") : ""))
                   .append(jewelryItem.getMaterial().getName()).append("_")
                   .append(ModUtil.getName(stone.getItem()).getPath()).append("_")
                   .append(StringUtils.isNotBlank(jewelryItem.getBaseName()) ? jewelryItem.getBaseName() : jewelryItem.getType().getName())
                   .toString().toLowerCase();
//                   .append(handler.getJewelryType().getName()).toString().toLowerCase();
        }

        public String name(ItemStack jewelry) {
            StringBuilder buffer = new StringBuilder();
            Jewelry jewelryItem = (Jewelry)jewelry.getItem();
//            IJewelryHandler handler = jewelry.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);
           return buffer.append((jewelryItem.getSizeTier() != JewelrySizeTier.REGULAR ? (jewelryItem.getSizeTier().getName() + "_") : ""))
                    .append(jewelryItem.getMaterial().getName()).append("_")
                    .append(
//                      jewelryItem.getJewelryType().getName()
                        StringUtils.isNotBlank(jewelryItem.getBaseName()) ? jewelryItem.getBaseName() : jewelryItem.getType().getName()
                    ).toString().toLowerCase();
        }
    }

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
