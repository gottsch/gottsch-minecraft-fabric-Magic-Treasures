package mod.gottsch.fabric.magic_treasures.core.api;

import mod.gottsch.fabric.gottschcore.enums.IEnum;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.EnumRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryStoneTierRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Gottschling on Nov 13, 2024
 */
public class MagicTreasuresApi {
    public static final String RARITY = "rarity";

    public static final String JEWELRY_TYPE = "jewelryType";
    public static final String JEWELRY_SIZE = "jewelrySize";

    /**
     *
     * @param rarity
     */
    public static void registerRarity(IRarity rarity) {
        EnumRegistry.register(RARITY, rarity);
    }

    /**
     *
     * @param key
     * @return
     */
    public static Optional<IRarity> getRarity(String key) {
        IEnum ienum = EnumRegistry.get(RARITY, key);
        if (ienum == null) {
            return Optional.empty();
        }
        else {
            return Optional.of((IRarity) ienum);
        }
    }

    public static List<IRarity> getRarities() {
        List<IEnum> enums = EnumRegistry.getValues(RARITY);
        ArrayList<IRarity> rarities = new ArrayList<>();
        if (!enums.isEmpty()) {
            rarities.addAll(enums.stream().map(e -> (IRarity)e).toList());
        }
        return rarities;
    }

    public static void registerJewelryType(IJewelryType jewelryType) {
        EnumRegistry.register(JEWELRY_TYPE, jewelryType);
    }

    public static List<IJewelryType> getJewelryTypes() {
        List<IEnum> enums = EnumRegistry.getValues(JEWELRY_TYPE);
        ArrayList<IJewelryType> types = new ArrayList<>();
        if (!enums.isEmpty()) {
            types.addAll(enums.stream().map(e -> (IJewelryType)e).toList());
        }
        return types;
    }

    public static Optional<IJewelryType> getJewelryType(String key) {
        IEnum ienum = EnumRegistry.get(JEWELRY_TYPE, key);
        if (ienum == null) {
            return Optional.empty();
        }
        else {
            return Optional.of((IJewelryType) ienum);
        }
    }

    public static void registerJewelrySize(IJewelrySizeTier size) {
        EnumRegistry.register(JEWELRY_SIZE, size);
    }

    public static Optional<IJewelrySizeTier> getJewelrySize(String key) {
        IEnum ienum = EnumRegistry.get(JEWELRY_SIZE, key);
        if (ienum == null) {
            return Optional.empty();
        }
        else {
            return Optional.of((IJewelrySizeTier) ienum);
        }
    }

    public static void registerJewelryStoneTier(JewelryStoneTier tier) {
        JewelryStoneTierRegistry.register(tier);
    }

    public static List<JewelryStoneTier> getJewelryStoneTiers() {
        return JewelryStoneTierRegistry.getStoneTiers();
    }

    public static void registerJewerlyStoneTierTag(JewelryStoneTier tier, TagKey<Item> tierTagKey) {
        TagRegistry.registerJewelryStoneTier(tier, tierTagKey);
    }
    public static void registerJewelryStoneRarityTag(IRarity rarity, TagKey<Item> tagKey) {
        TagRegistry.registerJewelryStoneRarity(rarity, tagKey);
    }
}
