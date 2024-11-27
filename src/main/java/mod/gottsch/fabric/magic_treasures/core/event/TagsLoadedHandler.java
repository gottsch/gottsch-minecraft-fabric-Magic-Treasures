package mod.gottsch.fabric.magic_treasures.core.event;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryAttribs;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.TagRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by Mark Gottschling on Nov 14, 2024
 */
public class TagsLoadedHandler implements CommonLifecycleEvents.TagsLoaded {

    public static class Attribs {
        public IJewelryType type;
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;
        public Identifier stone = Items.AIR.getRegistryEntry().registryKey().getValue();
    }

    private static Map<Item, Attribs> attribsMap = new HashMap<>();

    @Override
    public void onTagsLoaded(DynamicRegistryManager dynamicRegistryManager, boolean b) {
        MagicTreasures.LOGGER.debug("in onTagLoaded event");

        // clear any registries
        JewelryRegistry.clear();

        /*
         * register all jewelry items by looking up the jewelry type tags.
         */
        // process all items in the JewelryType tags
        List<IJewelryType> types = MagicTreasuresApi.getJewelryTypes();
        types.forEach(type -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryTypeTag(type);
            if (tagKey != null) {
                // get the tag
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
//                    JewelryRegistry.register(jewelry);
                    Attribs attribs = new Attribs();
                    attribs.type = type;
                    attribsMap.put(jewelry, attribs);
//                    MagicTreasures.LOGGER.debug("registering jewelry -> {} ", ModUtil.getName(jewelry));
                }
            }
        });

        // process all items in the Material tags
        List<JewelryMaterial> materials = MagicTreasuresApi.getJewelryMaterials();
        materials.forEach(material -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryMaterialTag(material);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).material = material;
                    }
                }
            }
        });

        // process all items in the size tier tags
        List<IJewelrySizeTier> sizeTiers = MagicTreasuresApi.getJewelrySizeTiers();
        sizeTiers.forEach(sizeTier -> {
            TagKey<Item> tagKey = TagRegistry.getJewelrySizeTierTag(sizeTier);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).sizeTier = sizeTier;
                    }
                }
            }
        });

        // process all items in the JewelryStoneTier tags
        List<JewelryStoneTier> tiers = MagicTreasuresApi.getJewelryStoneTiers();
        tiers.forEach(tier -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryStoneTierTag(tier);
            if (tagKey != null) {
                // get the tag
                List<Item> tagItems = getItemsFromTag(tagKey);

                // for each item in the tag
                for (Item stone : tagItems) {
                    // register the stone with the StoneRegistry
                    StoneRegistry.register(stone);
                    // register the stone with the tier in the StoneRegistry
                    StoneRegistry.register(stone, tier);
                    MagicTreasures.LOGGER.debug("registering stone to tier -> {} <--> {} ", ModUtil.getName(stone), tier.getName());
                }
            }
        });

        // NOTE this must come after processing Stone Tiers since that is where
        // the stones are first registered.
        // process all the items in the stones tags
        List<Item> stones = MagicTreasuresApi.getJewelryStones();
        stones.forEach(stone -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryStoneTag(stone);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).stone = ModUtil.getName(stone);
                    }
                }
            }
        });

        // now we can finally register the jewelry
        attribsMap.forEach((k,v) -> {
            // build attribs
            JewelryAttribs attribs = new JewelryAttribs(v.type, v.sizeTier, v.material, v.stone);
            JewelryRegistry.register(k, attribs);
        });

        // registry stones by rarity
        List<IRarity> rarities = MagicTreasuresApi.getRarities();
        rarities.forEach(rarity -> {
            TagKey<Item> tagKey = TagRegistry.getStoneRarityTag(rarity);
            if (tagKey != null) {
                // get the tagged list of items
                List<Item> tagItems = getItemsFromTag(tagKey);
                for (Item stone : tagItems) {
                    StoneRegistry.register(rarity, stone);
                    MagicTreasures.LOGGER.debug("registering stone to rarity -> {} <--> {} ", ModUtil.getName(stone), rarity.getName());
                }
            }
            // register jewelry by rarity
            MagicTreasuresApi.getJewelryRarityTag(rarity)
                    .ifPresent(key -> {
                        List<Item> tagItems = getItemsFromTag(key);
                        tagItems.forEach(item -> {
                            JewelryRegistry.register(rarity, item);
                            MagicTreasures.LOGGER.debug("registering jewelry to rarity -> {} <--> {} ", ModUtil.getName(item), rarity.getName());
                        });
                    });
        });
    }

    /**
     * this is an extremely inefficient method to get tags items, other than
     * one-time setup situations.
     * @param tagKey
     * @return
     */
    public static List<Item> getItemsFromTag(TagKey<Item> tagKey) {
        return Registries.ITEM.stream()
                .filter(item -> item.getRegistryEntry().isIn(tagKey))
                .toList();
    }
}
