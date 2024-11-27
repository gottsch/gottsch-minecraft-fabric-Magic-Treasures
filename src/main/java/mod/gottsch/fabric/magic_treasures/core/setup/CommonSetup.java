package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.integration.MagicTreasuresIntegrations;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTiers;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
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

        // register jewelry stone tags
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.TOPAZ), MagicTreasuresTags.Items.TOPAZ);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.ONYX), MagicTreasuresTags.Items.ONYX);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.JADEITE), MagicTreasuresTags.Items.JADEITE);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(Items.DIAMOND), MagicTreasuresTags.Items.DIAMOND);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.RUBY), MagicTreasuresTags.Items.RUBY);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.SAPPHIRE), MagicTreasuresTags.Items.SAPPHIRE);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.WHITE_PEARL), MagicTreasuresTags.Items.WHITE_PEARL);
        MagicTreasuresApi.registerJewerlyStoneTag(ModUtil.getName(MagicTreasuresItems.BLACK_PEARL), MagicTreasuresTags.Items.BLACK_PEARL);

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

}
