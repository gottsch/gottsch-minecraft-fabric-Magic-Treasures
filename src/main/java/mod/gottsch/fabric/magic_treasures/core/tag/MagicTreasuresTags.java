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
