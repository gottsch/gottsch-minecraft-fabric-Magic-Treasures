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
package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.integration.MagicTreasuresIntegrations;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryGemstoneTiers;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Items;

/**
 * Created by Mark Gottschling on Nov 13, 2024 (fabric version)
 */
public class CommonSetup {
    public static void init() {
        // register rarities
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.COMMON);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.UNCOMMON);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.SCARCE);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.RARE);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.EPIC);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.LEGENDARY);
        MagicTreasuresApi.registerRarity(MagicTreasuresRarity.MYTHICAL);

        // register jewelry types
        MagicTreasuresApi.registerJewelryType(JewelryType.RING);
        MagicTreasuresApi.registerJewelryType(JewelryType.NECKLACE);
        MagicTreasuresApi.registerJewelryType(JewelryType.BRACELET);
        MagicTreasuresApi.registerJewelryType(JewelryType.POCKET);

        // register jewelry size tiers
        MagicTreasuresApi.registerJewelrySize(JewelrySizeTier.REGULAR);
        MagicTreasuresApi.registerJewelrySize(JewelrySizeTier.GREAT);
        MagicTreasuresApi.registerJewelrySize(JewelrySizeTier.LORDS);

        // register jewelry size tier tags
        MagicTreasuresApi.registerJewerlySizeTierTag(JewelrySizeTier.REGULAR, MagicTreasuresTags.Items.REGULAR);
        MagicTreasuresApi.registerJewerlySizeTierTag(JewelrySizeTier.GREAT, MagicTreasuresTags.Items.GREAT);
        MagicTreasuresApi.registerJewerlySizeTierTag(JewelrySizeTier.LORDS, MagicTreasuresTags.Items.LORDS);

        // TODO move this to MagicTreasuresItems
        // TODO register all known (Magic Treasures mod) gemstones
        // TODO the loading of additional stones (external mods) happen in the TagLoad event

        // register jewelry stone tags
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.TOPAZ), MagicTreasuresTags.Items.TOPAZ);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.ONYX), MagicTreasuresTags.Items.ONYX);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.JADEITE), MagicTreasuresTags.Items.JADEITE);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(Items.DIAMOND), MagicTreasuresTags.Items.DIAMOND);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.RUBY), MagicTreasuresTags.Items.RUBY);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.SAPPHIRE), MagicTreasuresTags.Items.SAPPHIRE);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.WHITE_PEARL), MagicTreasuresTags.Items.WHITE_PEARL);
        MagicTreasuresApi.registerJewerlyGemstoneTag(ModUtil.getName(MagicTreasuresItems.BLACK_PEARL), MagicTreasuresTags.Items.BLACK_PEARL);

        // register jewelry stone tiers
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER1);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER2);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER3);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER4);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER5);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryGemstoneTiers.TIER6);
//        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.SKELETONS_HEART);

        // register stone tier tags
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER1, MagicTreasuresTags.Items.STONE_TIER1);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER2, MagicTreasuresTags.Items.STONE_TIER2);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER3, MagicTreasuresTags.Items.STONE_TIER3);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER4, MagicTreasuresTags.Items.STONE_TIER4);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER5, MagicTreasuresTags.Items.STONE_TIER5);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryGemstoneTiers.TIER6, MagicTreasuresTags.Items.STONE_TIER6);
//        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.SKELETONS_HEART, MagicTreasuresTags.Items.STONE_TIER_SKELETONS_HEART);

        // register stone rarity tags
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.COMMON, MagicTreasuresTags.Items.STONE_RARITY_COMMON);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.UNCOMMON, MagicTreasuresTags.Items.STONE_RARITY_UNCOMMON);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.SCARCE, MagicTreasuresTags.Items.STONE_RARITY_SCARCE);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.RARE, MagicTreasuresTags.Items.STONE_RARITY_RARE);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.EPIC, MagicTreasuresTags.Items.STONE_RARITY_EPIC);

        // register jewelry materials
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.WOOD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.IRON);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.COPPER);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.SILVER);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.GOLD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.BLOOD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.BONE);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.SHADOW);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.ATIUM);

        // register jewelry material tags
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.WOOD, MagicTreasuresTags.Items.WOOD);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.IRON, MagicTreasuresTags.Items.IRON);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.COPPER, MagicTreasuresTags.Items.COPPER);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.SILVER, MagicTreasuresTags.Items.SILVER);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.GOLD, MagicTreasuresTags.Items.GOLD);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.BLOOD, MagicTreasuresTags.Items.BLOOD);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.BONE, MagicTreasuresTags.Items.BONE);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.SHADOW, MagicTreasuresTags.Items.SHADOW);
        MagicTreasuresApi.registerJewelryMaterialTag(JewelryMaterials.ATIUM, MagicTreasuresTags.Items.ATIUM);

        // register jewelry rarity tags
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.COMMON, MagicTreasuresTags.Items.JEWELRY_COMMON);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.UNCOMMON, MagicTreasuresTags.Items.JEWELRY_UNCOMMON);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.SCARCE, MagicTreasuresTags.Items.JEWELRY_SCARCE);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.RARE, MagicTreasuresTags.Items.JEWELRY_RARE);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.EPIC, MagicTreasuresTags.Items.JEWELRY_EPIC);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.LEGENDARY, MagicTreasuresTags.Items.JEWELRY_LEGENDARY);
        MagicTreasuresApi.registerJewelryRarityTag(MagicTreasuresRarity.MYTHICAL, MagicTreasuresTags.Items.JEWELRY_MYTHICAL);

        // register type tags (TODO this could be a stream of all JewelryTypes ?)
        MagicTreasuresApi.registerJewerlyTypeTag(JewelryType.RING, MagicTreasuresTags.Items.RINGS);
        MagicTreasuresApi.registerJewerlyTypeTag(JewelryType.BRACELET, MagicTreasuresTags.Items.BRACELETS);
        MagicTreasuresApi.registerJewerlyTypeTag(JewelryType.NECKLACE, MagicTreasuresTags.Items.NECKLACES);
        MagicTreasuresApi.registerJewerlyTypeTag(JewelryType.POCKET, MagicTreasuresTags.Items.POCKETS);
//        MagicTreasuresApi.registerJewerlyTypeTag(JewelryType.CHARM, MagicTreasuresTags.Items.CHARMS);

        // curios integration
        MagicTreasuresIntegrations.registerTrinketsIntegration();
    }

    public static void registerJewelryCustomComponentsBuilders() {
        MagicTreasuresApi.registerJewelryCustomComponentBuilder(
                MagicTreasuresItems.SILBROS_RING_OF_VITALITY,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.WOOD)
                        .with($ -> {
                            $.gemstone = ModUtil.getName(Items.BEDROCK);
                            $.maxLevelComponent = new MaxLevelComponent(2);
                            $.repairsComponent = new RepairsComponent(1);
                            $.manaComponent = new ManaComponent(80);
                            $.rechargesComponent = new RechargesComponent(0);
                            $.spellsComponent = new SpellsComponent(MagicTreasuresSpells.DEFAULT_HEALING);
                            $.rarity = MagicTreasuresRarity.COMMON;
                        })
        );
    }
}
