package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.integration.MagicTreasuresIntegrations;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
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

        // register jewelry material tiers
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.WOOD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.IRON);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.COPPER);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.SILVER);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.GOLD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.BLOOD);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.BONE);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.SHADOW);
        MagicTreasuresApi.registerJewelryMaterial(JewelryMaterials.ATIUM);

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

}
