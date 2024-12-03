package mod.gottsch.fabric.magic_treasures.core.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.*;

/**
 * 
 * @author Mark Gottschling Nov 14, 20204 (fabric version)
 *
 */
public class GemstoneRegistry {
    private static final Map<Identifier, Item> REGISTRY = new HashMap<>();
    // NOTE initialize during the Tags event
    private static final Map<Item, JewelryStoneTier> STONE_TIER_MAP = Maps.newHashMap();
    private static final Multimap<IRarity, Item> RARITY_MAP = ArrayListMultimap.create();

    public static Optional<Item> register(Item gemstone) {
        if (!REGISTRY.containsKey(ModUtil.getName(gemstone))) {
            REGISTRY.put(ModUtil.getName(gemstone), gemstone);
            return Optional.of(gemstone);
        }
        return Optional.empty();
    }

    public static boolean has(Identifier name) {
        return REGISTRY.containsKey(name);
    }

    public static List<Item> getStones() {
        return Lists.newArrayList(REGISTRY.values());
    }

    /**
     *
     * @param name
     * @return
     */
    public static Optional<Item> get(Identifier name) {

        if (name != null && REGISTRY.containsKey(name)) {
            return Optional.of(REGISTRY.get(name));
        }
        return Optional.empty();
    }

    public static JewelryStoneTier register(Item jewelry, JewelryStoneTier tier) {
        return STONE_TIER_MAP.put(jewelry, tier);
    }

    public static Optional<JewelryStoneTier> getStoneTier(Item jewelry) {
        if (STONE_TIER_MAP.containsKey(jewelry)) {
            return Optional.of(STONE_TIER_MAP.get(jewelry));
        }
        return Optional.empty();
    }

//    public static Optional<JewelryStoneHandler> register(Item jewelry, JewelryStoneHandler handler) {
//        return Optional.ofNullable(HANDLER_MAP.put(jewelry, handler));
//    }
//
//    public static Optional<JewelryStoneHandler> getStoneHandler(Item jewelry) {
//        if (HANDLER_MAP.containsKey(jewelry)) {
//            return Optional.of(HANDLER_MAP.get(jewelry));
//        }
//        return Optional.empty();
//    }

    /**
     * register rarity-stone mapping
     * @param rarity
     * @param stone
     * @return
     */
    public static boolean register(IRarity rarity, Item stone) {
        return RARITY_MAP.put(rarity, stone);
    }

    public static List<Item> get(IRarity rarity) {
        List<Item> list = new ArrayList<>();
        if (RARITY_MAP.containsKey(rarity)) {
           list.addAll(RARITY_MAP.get(rarity));
        }
        return list;
    }

    public static Optional<IRarity> getRarity(Item stone) {
        return RARITY_MAP.entries().stream().filter(e -> e.getValue() == stone).findFirst().map(Map.Entry::getKey);
    }
}
