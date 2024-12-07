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
    private static final String TRINKETS_MOD_ID = "trinkets";
    private static final String FORGE_MODID = "forge";

    public static class Items {

        // ingots
        public static final TagKey<Item> INGOTS_SILVER = createTag("c", "ingots/silver");
        // raw materials
        public static final TagKey<Item> RAW_SILVER = createTag("c", "raw_materials/silver");
        public static final TagKey<Item> BONES = createTag("c", "raw_materials/bones");

        // gemstones
        public static final TagKey<Item> STONES = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/all_gemstones");
        public static final TagKey<Item> RECHARGERS = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rechargers");

        // gemstone tiers
        public static final TagKey<Item> STONE_TIER1 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier1");
        public static final TagKey<Item> STONE_TIER2 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier2");
        public static final TagKey<Item> STONE_TIER3 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier3");
        public static final TagKey<Item> STONE_TIER4 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier4");
        public static final TagKey<Item> STONE_TIER5 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier5");
        public static final TagKey<Item> STONE_TIER6 = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/tier/tier6");
//      public static final TagKey<Item> STONE_TIER_SKELETONS_HEART = createTag(MagicTreasures.MOD_ID, "jewelry/stones/tier/skeletons_heart");

        // NOTE gemstone rarity is loosely equivalent to tier.
        public static final TagKey<Item> STONE_RARITY_COMMON = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rarity/common");
        public static final TagKey<Item> STONE_RARITY_UNCOMMON = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rarity/uncommon");
        public static final TagKey<Item> STONE_RARITY_SCARCE = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rarity/scarce");
        public static final TagKey<Item> STONE_RARITY_RARE = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rarity/rare");
        public static final TagKey<Item> STONE_RARITY_EPIC = createTag(MagicTreasures.MOD_ID, "jewelry/gemstone/rarity/epic");

        // categorization by material
        public static final TagKey<Item> WOOD = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/wood");
        public static final TagKey<Item> IRON = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/iron");
        public static final TagKey<Item> COPPER = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/copper");
        public static final TagKey<Item> SILVER = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/silver");
        public static final TagKey<Item> GOLD = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/gold");
        public static final TagKey<Item> BLOOD = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/blood");
        public static final TagKey<Item> BONE = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/bone");
        public static final TagKey<Item> SHADOW = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/shadow");
        public static final TagKey<Item> ATIUM = createTag(MagicTreasures.MOD_ID, "jewelry/category/material/atium");

        // categorization by size tier
        public static final TagKey<Item> REGULAR = createTag(MagicTreasures.MOD_ID, "jewelry/category/size/regular");
        public static final TagKey<Item> GREAT = createTag(MagicTreasures.MOD_ID, "jewelry/category/size/great");
        public static final TagKey<Item> LORDS = createTag(MagicTreasures.MOD_ID, "jewelry/category/size/lords");

        // categorization by stone
        public static final TagKey<Item> TOPAZ = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/topaz");
        public static final TagKey<Item> ONYX = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/onyx");
        public static final TagKey<Item> JADEITE = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/jadeite");
        public static final TagKey<Item> DIAMOND = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/diamond");
        public static final TagKey<Item> RUBY = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/ruby");
        public static final TagKey<Item> SAPPHIRE = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/sapphire");
        public static final TagKey<Item> WHITE_PEARL = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/white_pearl");
        public static final TagKey<Item> BLACK_PEARL = createTag(MagicTreasures.MOD_ID, "jewelry/category/gemstone/black_pearl");

        // categorization by rarity
        public static final TagKey<Item> JEWELRY_COMMON = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/common");
        public static final TagKey<Item> JEWELRY_UNCOMMON = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/uncommon");
        public static final TagKey<Item> JEWELRY_SCARCE = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/scarce");
        public static final TagKey<Item> JEWELRY_RARE = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/rare");
        public static final TagKey<Item> JEWELRY_EPIC = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/epic");
        public static final TagKey<Item> JEWELRY_LEGENDARY = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/legendary");
        public static final TagKey<Item> JEWELRY_MYTHICAL = createTag(MagicTreasures.MOD_ID, "jewelry/category/rarity/mythical");
        
        // jewelry categorization by type
        public static final TagKey<Item> RINGS = createTag(MagicTreasures.MOD_ID, "jewelry/category/type/rings");
        public static final TagKey<Item> BRACELETS = createTag(MagicTreasures.MOD_ID, "jewelry/category/type/bracelets");
        public static final TagKey<Item> NECKLACES = createTag(MagicTreasures.MOD_ID, "jewelry/category/type/necklaces");
        //        public static final TagKey<Item> CHARMS = createTag(MagicTreasures.MOD_ID, "jewelry/category/type/charms");
        public static final TagKey<Item> POCKETS = createTag(MagicTreasures.MOD_ID, "jewelry/category/type/pockets");

        // all jewelry (optional tags of the other types)
        public static final TagKey<Item> JEWELRY = createTag(MagicTreasures.MOD_ID, "jewelry/category/class/all_jewelry");
        public static final TagKey<Item> STANDARD_JEWELRY = createTag(MagicTreasures.MOD_ID, "jewelry/category/class/standard_jewelry");
        // special/custom jewelry - a subset of all jewelry
        public static final TagKey<Item> CUSTOM_JEWELRY = createTag(MagicTreasures.MOD_ID, "jewelry/category/class/custom_jewelry");
        // castle rings
        public static final TagKey<Item> CASTLE_RING_RUBY = createTag(MagicTreasures.MOD_ID, "jewelry/special/castle_ring/gemstone/ruby");
        public static final TagKey<Item> CASTLE_RING_SAPPHIRE = createTag(MagicTreasures.MOD_ID, "jewelry/special/castle_ring/gemstone/sapphire");

        // other
        public static final TagKey<Item> STONE_REMOVAL_TOOLS = createTag(MagicTreasures.MOD_ID, "tools/stone_removal");

        // spell scrolls
        public static final TagKey<Item> SPELL_SCROLLS = createTag(MagicTreasures.MOD_ID, "scrolls/spells");

        // trinkets integration
        public static final TagKey<Item> TRINKETS_NECKLACE = createTag(TRINKETS_MOD_ID, "chest/necklace");
        public static final TagKey<Item> TRINKETS_RING = createTag(TRINKETS_MOD_ID, "hand/ring");
        public static final TagKey<Item> TRINKETS_BRACELET = createTag(TRINKETS_MOD_ID, "hand/bracelet");
        public static final TagKey<Item> TRINKETS_BELT = createTag(TRINKETS_MOD_ID, "legs/belt");

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
