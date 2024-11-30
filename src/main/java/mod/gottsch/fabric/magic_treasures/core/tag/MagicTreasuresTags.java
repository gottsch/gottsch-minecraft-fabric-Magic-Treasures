package mod.gottsch.fabric.magic_treasures.core.tag;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class MagicTreasuresTags {
    private static final String CURIOS_MODID = "curios";
    private static final String FORGE_MODID = "forge";

    public static class Items {

        // gemstones
        public static final TagKey<Item> STONES = createTag(MagicTreasures.MOD_ID, "jewelry/stones/all_stones");
        public static final TagKey<Item> RECHARGERS = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rechargers");

        // gemstone tiers
        public static final TagKey<Item> STONE_TIER1 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier1");
        public static final TagKey<Item> STONE_TIER2 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier2");
        public static final TagKey<Item> STONE_TIER3 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier3");
        public static final TagKey<Item> STONE_TIER4 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier4");
        public static final TagKey<Item> STONE_TIER5 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier5");
        public static final TagKey<Item> STONE_TIER6 = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/tier6");
//      public static final TagKey<Item> STONE_TIER_SKELETONS_HEART = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/skeletons_heart");

        // NOTE gemstone rarity is loosely equivalent to tier.
        public static final TagKey<Item> STONE_RARITY_COMMON = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rarity/common");
        public static final TagKey<Item> STONE_RARITY_UNCOMMON = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rarity/uncommon");
        public static final TagKey<Item> STONE_RARITY_SCARCE = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rarity/scarce");
        public static final TagKey<Item> STONE_RARITY_RARE = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rarity/rare");
        public static final TagKey<Item> STONE_RARITY_EPIC = createTag(MagicTreasures.MOD_ID, "jewelry/stones/rarity/epic");

        // categorization by tier
        public static final TagKey<Item> WOOD = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/wood");
        public static final TagKey<Item> IRON = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/iron");
        public static final TagKey<Item> COPPER = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/copper");
        public static final TagKey<Item> SILVER = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/silver");
        public static final TagKey<Item> GOLD = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/gold");
        public static final TagKey<Item> BLOOD = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/blood");
        public static final TagKey<Item> BONE = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/bone");
        public static final TagKey<Item> SHADOW = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/shadow");
        public static final TagKey<Item> ATIUM = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/materials/atium");

        // categorization by size tier
        public static final TagKey<Item> REGULAR = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/sizes/regular");
        public static final TagKey<Item> GREAT = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/sizes/great");
        public static final TagKey<Item> LORDS = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/sizes/lords");

        // categorization by stone
        public static final TagKey<Item> TOPAZ = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/topaz");
        public static final TagKey<Item> ONYX = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/onyx");
        public static final TagKey<Item> JADEITE = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/jadeite");
        public static final TagKey<Item> DIAMOND = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/diamond");
        public static final TagKey<Item> RUBY = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/ruby");
        public static final TagKey<Item> SAPPHIRE = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/sapphire");
        public static final TagKey<Item> WHITE_PEARL = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/white_pearl");
        public static final TagKey<Item> BLACK_PEARL = createTag(MagicTreasures.MOD_ID, "jewelry/tiers/stones/black_pearl");

        // categorization by rarity
        public static final TagKey<Item> JEWELRY_COMMON = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/common");
        public static final TagKey<Item> JEWELRY_UNCOMMON = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/uncommon");
        public static final TagKey<Item> JEWELRY_SCARCE = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/scarce");
        public static final TagKey<Item> JEWELRY_RARE = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/rare");
        public static final TagKey<Item> JEWELRY_EPIC = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/epic");
        public static final TagKey<Item> JEWELRY_LEGENDARY = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/legendary");
        public static final TagKey<Item> JEWELRY_MYTHICAL = createTag(MagicTreasures.MOD_ID, "jewelry/rarity/mythical");
        
        // jewelry categorization by type
        public static final TagKey<Item> RINGS = createTag(MagicTreasures.MOD_ID, "jewelry/rings");
        public static final TagKey<Item> BRACELETS = createTag(MagicTreasures.MOD_ID, "jewelry/bracelets");
        public static final TagKey<Item> NECKLACES = createTag(MagicTreasures.MOD_ID, "jewelry/necklaces");
        //        public static final TagKey<Item> CHARMS = createTag(MagicTreasures.MOD_ID, "jewelry/charms");
        public static final TagKey<Item> POCKETS = createTag(MagicTreasures.MOD_ID, "jewelry/pockets");

        // all jewelry (optional tags of the other types)
        public static final TagKey<Item> JEWELRY = createTag(MagicTreasures.MOD_ID, "jewelry/all_jewelry");
        // special/custom jewelry - a subset of all jewelry
        public static final TagKey<Item> CUSTOM_JEWELRY = createTag(MagicTreasures.MOD_ID, "jewelry/custom_jewelry");
        // castle rings
        public static final TagKey<Item> CASTLE_RING_RUBY = createTag(MagicTreasures.MOD_ID, "jewelry/castle_ring/ruby");
        public static final TagKey<Item> CASTLE_RING_SAPPHIRE = createTag(MagicTreasures.MOD_ID, "jewelry/castle_ring/sapphire");

        // other
        public static final TagKey<Item> STONE_REMOVAL_TOOLS = createTag(MagicTreasures.MOD_ID, "tools/stone_removal");

        // spell scrolls
        public static final TagKey<Item> SPELL_SCROLLS = createTag(MagicTreasures.MOD_ID, "scrolls/spells");

        /**
         *
         * @param domain
         * @param path
         * @return
         */
        public static TagKey<Item> createTag(String domain, String path) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(domain, path));
        }
    }
}
