package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTiers;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;

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

        // register jewelry stone tiers
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER1);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER2);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER3);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER4);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER5);
        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.TIER6);
//        MagicTreasuresApi.registerJewelryStoneTier(JewelryStoneTiers.SKELETONS_HEART);

        // register stone tier tags
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER1, MagicTreasuresTags.Items.STONE_TIER1);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER2, MagicTreasuresTags.Items.STONE_TIER2);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER3, MagicTreasuresTags.Items.STONE_TIER3);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER4, MagicTreasuresTags.Items.STONE_TIER4);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER5, MagicTreasuresTags.Items.STONE_TIER5);
        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.TIER6, MagicTreasuresTags.Items.STONE_TIER6);
//        MagicTreasuresApi.registerJewerlyStoneTierTag(JewelryStoneTiers.SKELETONS_HEART, MagicTreasuresTags.Items.STONE_TIER_SKELETONS_HEART);

        // register stone rarity tags
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.COMMON, MagicTreasuresTags.Items.STONE_RARITY_COMMON);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.UNCOMMON, MagicTreasuresTags.Items.STONE_RARITY_UNCOMMON);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.SCARCE, MagicTreasuresTags.Items.STONE_RARITY_SCARCE);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.RARE, MagicTreasuresTags.Items.STONE_RARITY_RARE);
        MagicTreasuresApi.registerJewelryStoneRarityTag(MagicTreasuresRarity.EPIC, MagicTreasuresTags.Items.STONE_RARITY_EPIC);

    }

}
