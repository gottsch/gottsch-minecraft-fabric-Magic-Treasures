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
package mod.gottsch.fabric.magic_treasures.datagen;

import com.google.common.collect.Maps;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        /*
         * map from jewelry type attribute to tag
         */
        Map<IJewelryType, TagKey<Item>> TYPE_TAG_MAP = Maps.newHashMap();
        TYPE_TAG_MAP.put(JewelryType.RING, MagicTreasuresTags.Items.RINGS);
        TYPE_TAG_MAP.put(JewelryType.BRACELET, MagicTreasuresTags.Items.BRACELETS);
//		TYPE_TAG_MAP.put(JewelryType.BROACH, GealdorCraftTags.Items.BROACHES);
//		TYPE_TAG_MAP.put(JewelryType.CHARM, MagicTreasuresTags.Items.CHARMS);
//		TYPE_TAG_MAP.put(JewelryType.EARRING, GealdorCraftTags.Items.EARRINGS);
        TYPE_TAG_MAP.put(JewelryType.NECKLACE, MagicTreasuresTags.Items.NECKLACES);
        TYPE_TAG_MAP.put(JewelryType.POCKET, MagicTreasuresTags.Items.POCKETS);

        Map<JewelryMaterial, TagKey<Item>> MATERIAL_TAG_MAP = Maps.newHashMap();
        MATERIAL_TAG_MAP.put(JewelryMaterials.WOOD, MagicTreasuresTags.Items.WOOD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.IRON, MagicTreasuresTags.Items.IRON);
        MATERIAL_TAG_MAP.put(JewelryMaterials.COPPER, MagicTreasuresTags.Items.COPPER);
        MATERIAL_TAG_MAP.put(JewelryMaterials.SILVER, MagicTreasuresTags.Items.SILVER);
        MATERIAL_TAG_MAP.put(JewelryMaterials.GOLD, MagicTreasuresTags.Items.GOLD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.BLOOD, MagicTreasuresTags.Items.BLOOD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.BONE, MagicTreasuresTags.Items.BONE);
        MATERIAL_TAG_MAP.put(JewelryMaterials.SHADOW, MagicTreasuresTags.Items.SHADOW);
        MATERIAL_TAG_MAP.put(JewelryMaterials.ATIUM, MagicTreasuresTags.Items.ATIUM);

        Map<IJewelrySizeTier, TagKey<Item>> SIZE_TAG_MAP = Maps.newHashMap();
        SIZE_TAG_MAP.put(JewelrySizeTier.REGULAR, MagicTreasuresTags.Items.REGULAR);
        SIZE_TAG_MAP.put(JewelrySizeTier.GREAT, MagicTreasuresTags.Items.GREAT);
        SIZE_TAG_MAP.put(JewelrySizeTier.LORDS, MagicTreasuresTags.Items.LORDS);

        Map<Item, TagKey<Item>> STONE_TAG_MAP = Maps.newHashMap();
        STONE_TAG_MAP.put(MagicTreasuresItems.TOPAZ, MagicTreasuresTags.Items.TOPAZ);
        STONE_TAG_MAP.put(MagicTreasuresItems.ONYX, MagicTreasuresTags.Items.ONYX);
        STONE_TAG_MAP.put(MagicTreasuresItems.JADEITE, MagicTreasuresTags.Items.JADEITE);
        STONE_TAG_MAP.put(Items.DIAMOND, MagicTreasuresTags.Items.DIAMOND);
        STONE_TAG_MAP.put(MagicTreasuresItems.RUBY, MagicTreasuresTags.Items.RUBY);
        STONE_TAG_MAP.put(MagicTreasuresItems.SAPPHIRE, MagicTreasuresTags.Items.SAPPHIRE);
        STONE_TAG_MAP.put(MagicTreasuresItems.WHITE_PEARL, MagicTreasuresTags.Items.WHITE_PEARL);
        STONE_TAG_MAP.put(MagicTreasuresItems.BLACK_PEARL, MagicTreasuresTags.Items.BLACK_PEARL);

        Map<IRarity, TagKey<Item>> JEWELRY_RARITY_TAG_MAP = Maps.newHashMap();
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.COMMON, MagicTreasuresTags.Items.JEWELRY_COMMON);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.UNCOMMON, MagicTreasuresTags.Items.JEWELRY_UNCOMMON);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.SCARCE, MagicTreasuresTags.Items.JEWELRY_SCARCE);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.RARE, MagicTreasuresTags.Items.JEWELRY_RARE);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.EPIC, MagicTreasuresTags.Items.JEWELRY_EPIC);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.LEGENDARY, MagicTreasuresTags.Items.JEWELRY_LEGENDARY);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.MYTHICAL, MagicTreasuresTags.Items.JEWELRY_MYTHICAL);

        // default stone rarity mapping
        Map<Item, IRarity> STONE_RARITY_MAP = Maps.newHashMap();
        STONE_RARITY_MAP.put(MagicTreasuresItems.TOPAZ, MagicTreasuresRarity.COMMON);
        STONE_RARITY_MAP.put(MagicTreasuresItems.ONYX, MagicTreasuresRarity.UNCOMMON);
        STONE_RARITY_MAP.put(MagicTreasuresItems.JADEITE, MagicTreasuresRarity.SCARCE);
        STONE_RARITY_MAP.put(Items.DIAMOND, MagicTreasuresRarity.SCARCE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.RUBY, MagicTreasuresRarity.RARE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.SAPPHIRE, MagicTreasuresRarity.EPIC);
        STONE_RARITY_MAP.put(MagicTreasuresItems.WHITE_PEARL, MagicTreasuresRarity.RARE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.BLACK_PEARL, MagicTreasuresRarity.EPIC);

        // default material rarity mapping
        Map<JewelryMaterial, IRarity> MATERIAL_RARITY_MAP = Maps.newHashMap();
        MATERIAL_RARITY_MAP.put(JewelryMaterials.WOOD, MagicTreasuresRarity.COMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.IRON, MagicTreasuresRarity.COMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.COPPER, MagicTreasuresRarity.UNCOMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.SILVER, MagicTreasuresRarity.SCARCE);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.GOLD, MagicTreasuresRarity.RARE);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.BONE, MagicTreasuresRarity.SCARCE);

        try {
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONES)
                    .add(MagicTreasuresItems.TOPAZ)
                    .add(MagicTreasuresItems.ONYX)
                    .add(Items.DIAMOND)
                    .add(MagicTreasuresItems.JADEITE)
                    .add(MagicTreasuresItems.RUBY)
                    .add(MagicTreasuresItems.SAPPHIRE)
                    .add(MagicTreasuresItems.WHITE_PEARL)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER1)
                    .add(MagicTreasuresItems.TOPAZ);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER2)
                    .add(MagicTreasuresItems.ONYX);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER3)
                    .add(Items.DIAMOND);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER4)
                    .add(MagicTreasuresItems.JADEITE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER5)
                    .add(MagicTreasuresItems.RUBY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER6)
                    .add(MagicTreasuresItems.SAPPHIRE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER5)
                    .add(MagicTreasuresItems.WHITE_PEARL);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER6)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_COMMON).add(MagicTreasuresItems.TOPAZ);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_UNCOMMON).add(MagicTreasuresItems.ONYX);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_SCARCE)
                    .add(Items.DIAMOND)
                    .add(MagicTreasuresItems.JADEITE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_RARE)
                    .add(MagicTreasuresItems.RUBY)
                    .add(MagicTreasuresItems.WHITE_PEARL);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_EPIC)
                    .add(MagicTreasuresItems.SAPPHIRE)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            // standard jewelry tools
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_REMOVAL_TOOLS)
                    .add(Items.SHEARS, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE);

            // TODO all this is moot as I can register the attribs on Item creation.
            // standard jewelry
            /*
             *  categorize jewelry into the different tags all items.
             *  if item has Jewelry capability, then categorize it into the different tags
             */
            /*
            MagicTreasuresItems.STANDARD_JEWELRY.forEach(registryItem -> {
                // TODO parse the name to get the attribs
                IJewelryType type = null;
                IJewelrySizeTier sizeTier = null;
                JewelryMaterial material = null;
                Item stone = Items.AIR;

                // TODO instead of explicility looking for certain names, check if it has a baseName
                // then you know the last 2 tokens are both part of the name (well, tokenize the basename to determine the length)
                if (ModUtil.getName(registryItem).getPath().contains("castle")) {
                    String[] tokens = registryItem.getRegistryEntry().registryKey().getValue().getPath().split("_");
                    // special name and shouldn't be handled here

                    type = JewelryType.RING;
                    sizeTier = JewelrySizeTier.REGULAR;
                    if (tokens.length == 3) {
                        material = JewelryMaterialRegistry.get(ModUtil.asLocation(tokens[0])).orElse(null);
                        if (material == null) return;
                    }
                    else if (tokens.length == 4) {
                        material = JewelryMaterialRegistry.get(ModUtil.asLocation(tokens[0])).orElse(null);
                        if (material == null) return;
                        stone = Registries.ITEM.get(ModUtil.asLocation(tokens[1]));
                        if (stone == null || stone == Items.AIR) return;
                    }
                    else return;

                } else {
                    String[] tokens = registryItem.getRegistryEntry().registryKey().getValue().getPath().split("_");
                    // special name and shouldn't be handled here

                    if (tokens.length == 1) return;
                    else if (tokens.length == 2) {
                        // simple jewelry
                        sizeTier = JewelrySizeTier.REGULAR;
                        material = JewelryMaterialRegistry.get(ModUtil.asLocation(tokens[0])).orElse(null);
                        if (material == null) return;
                        type = MagicTreasuresApi.getJewelryType(tokens[1]).orElse(null);
                        if (type == null) return;
                    } else if (tokens.length == 3) {
                        Optional<IJewelrySizeTier> optionalSize = MagicTreasuresApi.getJewelrySize(tokens[0]);
                        if (optionalSize.isPresent()) {
                            sizeTier = optionalSize.get();
                            material = MagicTreasuresApi.getJewelryMaterial(ModUtil.asLocation(tokens[1])).orElse(null);
                            if (material == null) return;
                            type = MagicTreasuresApi.getJewelryType(tokens[2]).orElse(null);
                            if (type == null) return;
                        } else {
                            sizeTier = JewelrySizeTier.REGULAR;
                            material = MagicTreasuresApi.getJewelryMaterial(ModUtil.asLocation(tokens[0])).orElse(null);
                            if (material == null) return;
                            // TODO can't use the StoneRegistry - it is loaded by tags and we are creating the tags.
                            stone = Registries.ITEM.get(ModUtil.asLocation(tokens[1]));
                            if (stone == null || stone == Items.AIR) return;
                            type = MagicTreasuresApi.getJewelryType(tokens[2]).orElse(null);
                            if (type == null) return;
                        }
                    } else if (tokens.length == 4) {
                        sizeTier = MagicTreasuresApi.getJewelrySize(tokens[0]).orElse(JewelrySizeTier.REGULAR);
                        material = MagicTreasuresApi.getJewelryMaterial(ModUtil.asLocation(tokens[1])).orElse(null);
                        if (material == null) return;
                        stone = Registries.ITEM.get(ModUtil.asLocation(tokens[2]));
                        if (stone == null || stone == Items.AIR) return;
                        type = MagicTreasuresApi.getJewelryType(tokens[3]).orElse(null);
                        if (type == null) return;
                    } else {
                        return;
                    }
                }
//                ItemStack stack = new ItemStack(registryItem);
//                if (stack.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)) {
//                    JewelryAttribsComponent attribs = stack.get(MagicTreasuresComponents.JEWELRY_ATTRIBS);
                    // add to the type tag
                    getOrCreateTagBuilder(TYPE_TAG_MAP.get(type)).add(registryItem);
                    // -- curios integration
//                    TagKey<Item> curiosTagKey = CURIOS_TYPE_TAG_MAP.get(handler.getJewelryType());
//                    if (curiosTagKey != null) {
//                        getOrCreateTagBuilder(curiosTagKey).add(registryItem.get());
//                    }
                    // add to the material tag
                    getOrCreateTagBuilder(MATERIAL_TAG_MAP.get(material)).add(registryItem);
                    getOrCreateTagBuilder(SIZE_TAG_MAP.get(sizeTier)).add(registryItem);
                    if (stone != null && stone != Items.AIR) {
                        getOrCreateTagBuilder(STONE_TAG_MAP.get(stone)).add(registryItem);
                    }

                    // only run this for standard jewelry (special is handled explicitly)
//                    if (!MagicTreasuresItems.STANDARD_JEWELRY.contains(registryItem)) return;

                    IRarity materialRarity = MATERIAL_RARITY_MAP.get(material);
                    IRarity rarity = materialRarity;
//                    Optional<Item> gemstoneItem = ComponentHelper.gemstoneItem(stack);
//                    Optional<Identifier> gemstone = ComponentHelper.gemstone(stack);

                    if (stone != null && stone != Items.AIR) {
                        IRarity stoneRarity = STONE_RARITY_MAP.get(stone);

                        if (stoneRarity != null) {
                            rarity = MagicTreasuresRarity.getByCode(Math.max(stoneRarity.getCode(), materialRarity.getCode()));
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + sizeTier.getCode());
                        }
                        else {
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + sizeTier.getCode());
                        }
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(registryItem);
                    } else {
                        rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + sizeTier.getCode());
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(registryItem);
                    }
//                }
            });
            */
            JewelryRegistry.getAllAttribs().forEach(pair -> {
                // add to the type tag
                getOrCreateTagBuilder(TYPE_TAG_MAP.get(pair.getRight().getType())).add(pair.getLeft());
                // add to the material tag
                getOrCreateTagBuilder(MATERIAL_TAG_MAP.get(pair.getRight().getMaterial())).add(pair.getLeft());
                // add to size tag
                getOrCreateTagBuilder(SIZE_TAG_MAP.get(pair.getRight().getSizeTier())).add(pair.getLeft());

                // calculate stone tag and stone rarity in one go.
                Item stone = null;
                if (pair.getRight().getStone() != null && !pair.getRight().getStone().equals(ModUtil.getName(Items.AIR))) {
                    // NOTE can't use the StoneRegistry - it is loaded by tags and we are creating the tags.
                    stone = Registries.ITEM.get(pair.getRight().getStone());
                    if(stone != null) {
                        // add to stone tag
                        getOrCreateTagBuilder(STONE_TAG_MAP.get(stone)).add(pair.getLeft());
                    }
                }
                // standard jewelry only
                if (!MagicTreasuresItems.STANDARD_JEWELRY.contains(pair.getLeft())) return;

                // calculate rarities
                IRarity materialRarity = MATERIAL_RARITY_MAP.get(pair.getRight().getMaterial());
                IRarity rarity = materialRarity;

                if (stone != null && stone != Items.AIR) {
                    IRarity stoneRarity = STONE_RARITY_MAP.get(stone);
                    if (stoneRarity != null) {
                        rarity = MagicTreasuresRarity.getByCode(Math.max(stoneRarity.getCode(), materialRarity.getCode()));
                        rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + pair.getRight().getSizeTier().getCode());
                    }
                    else {
                        rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + pair.getRight().getSizeTier().getCode());
                    }
                    getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(pair.getLeft());
                } else {
                    rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + pair.getRight().getSizeTier().getCode());
                    getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(pair.getLeft());
                }
            });

            /*
             * custom jewelry
             */
            // TODO Castle Rings and Haws Rings are not setup yet.
            // TODO need to somehow process these rings without explicitly coding them
            MagicTreasuresItems.CUSTOM_JEWELRY.forEach(custom -> {
                // TEMP discriminate for castle or hawk
                if (ModUtil.getName(custom).getPath().contains("castle")
                        || ModUtil.getName(custom).getPath().contains("hawk")) {
                    getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(custom);
                    ItemStack stack = new ItemStack(custom);
                    ComponentHelper.jewelryType(stack).ifPresent(type -> {
                        getOrCreateTagBuilder(TYPE_TAG_MAP.get(type)).add(custom);
                    });
                    ComponentHelper.material(stack).ifPresent(material -> {
                        getOrCreateTagBuilder(MATERIAL_TAG_MAP.get(material)).add(custom);
                    });
                    ComponentHelper.sizeTier(stack).ifPresent(sizeTier -> {
                        getOrCreateTagBuilder(SIZE_TAG_MAP.get(sizeTier)).add(custom);
                    });

                    IRarity materialRarity = MATERIAL_RARITY_MAP.get(ComponentHelper.material(stack).orElse(JewelryMaterials.NONE));
                    IRarity rarity = materialRarity;
                    Item stone = ComponentHelper.gemstoneMinecraftItem(stack).orElse(Items.AIR);

                    // calculate rarities
                    if (stone != Items.AIR) {
                        getOrCreateTagBuilder(STONE_TAG_MAP.get(stone)).add(custom);

                        IRarity stoneRarity = STONE_RARITY_MAP.get(stone);
                        if (stoneRarity != null) {
                            rarity = MagicTreasuresRarity.getByCode(Math.max(stoneRarity.getCode(), materialRarity.getCode()));
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).orElse(JewelrySizeTier.REGULAR).getCode());
                        }
                        else {
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).orElse(JewelrySizeTier.REGULAR).getCode());
                        }
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(custom);
                    } else {
                        rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).orElse(JewelrySizeTier.REGULAR).getCode());
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(custom);
                    }
                }
            });

            Item item = MagicTreasuresItems.SILBROS_RING_OF_VITALITY;
            // silbro's
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.WOOD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.REGULAR).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.SILBROS_RING_OF_VITALITY);

            // strongmans bracers
            item = MagicTreasuresItems.STRONGMANS_BRACERS;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BRACELETS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.WOOD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.REGULAR).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.STRONGMANS_BRACERS);

            // peasents fortune
            item = MagicTreasuresItems.PEASANTS_FORTUNE;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.IRON).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.PEASANTS_FORTUNE);

            // maldritchs
            item = MagicTreasuresItems.MALDRITCHS_FIRST_AMULET;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.NECKLACES).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BONE).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.ONYX).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET);

            // aqua ring
            item = MagicTreasuresItems.AQUA_RING;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.SILVER).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.TOPAZ).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.AQUA_RING);

            // amulet of defence
            item = MagicTreasuresItems.AMULET_OF_DEFENCE;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.NECKLACES).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.COPPER).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.TOPAZ).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.AMULET_OF_DEFENCE);

            // jouneymans bands
            item = MagicTreasuresItems.JOURNEYMANS_BANDS;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BRACELETS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JADEITE).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.JOURNEYMANS_BANDS);

            // adephagias bounty
            item = MagicTreasuresItems.ADEPHAGIAS_BOUNTY;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BRACELETS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JADEITE).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.ADEPHAGIAS_BOUNTY);

            // medics token
            item = MagicTreasuresItems.MEDICS_TOKEN;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.NECKLACES).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JADEITE).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.MEDICS_TOKEN);

            // salandaars ward
            item = MagicTreasuresItems.SALANDAARS_WARD;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.NECKLACES).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RUBY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.SALANDAARS_WARD);

            // angels ring
            item = MagicTreasuresItems.MEDICS_TOKEN;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.WHITE_PEARL).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.ANGELS_RING);

            // ring of fortitude
            item = MagicTreasuresItems.RING_OF_FORTITUDE;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.SAPPHIRE).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.RING_OF_FORTITUDE);

            // ring of life or death
            item = MagicTreasuresItems.RING_LIFE_DEATH;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RINGS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BLOOD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RUBY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.LORDS).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_EPIC).add(MagicTreasuresItems.RING_LIFE_DEATH);

            // eye of the phoenix
            item = MagicTreasuresItems.EYE_OF_THE_PHOENIX;
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CUSTOM_JEWELRY).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.NECKLACES).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GOLD).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.BLACK_PEARL).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.GREAT).add(item);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_EPIC).add(MagicTreasuresItems.EYE_OF_THE_PHOENIX);

            // castle rings


            // types
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addOptionalTag(MagicTreasuresTags.Items.RINGS);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addOptionalTag(MagicTreasuresTags.Items.NECKLACES);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addOptionalTag(MagicTreasuresTags.Items.BRACELETS);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addOptionalTag(MagicTreasuresTags.Items.POCKETS);
//            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addTag(MagicTreasuresTags.Items.NECKLACES);
//            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addTag(MagicTreasuresTags.Items.BRACELETS);
//            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).add(MagicTreasuresItems.ADEPHAGIAS_BOUNTY);

            // special jewelry tags
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CASTLE_RING_RUBY).add(MagicTreasuresItems.RUBY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE).add(MagicTreasuresItems.SAPPHIRE);

            // recharging stones
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RECHARGERS).add(Items.AMETHYST_SHARD);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RECHARGERS).add(Items.EMERALD);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.RECHARGERS).add(MagicTreasuresItems.RECHARGE_SCROLL);

            // spell scrolls
            Registries.ITEM.forEach(o -> {
                if (o instanceof SpellScroll) {
                    getOrCreateTagBuilder(MagicTreasuresTags.Items.SPELL_SCROLLS).add(o);
                }
            });

        } catch(Exception e) {
            MagicTreasures.LOGGER.error("error", e);
        }
//                .addOptional(MagicTreasuresItems.TOPAZ.getRegistryEntry().registryKey());
//                .addOptional(identifier(MagicTreasuresItems.ONYX));
    }

    public final Identifier identifier(Item item) {
        return Registries.ITEM.getId(item);
    }
}
