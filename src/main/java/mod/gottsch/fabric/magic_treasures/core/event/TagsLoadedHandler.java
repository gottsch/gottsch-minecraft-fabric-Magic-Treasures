package mod.gottsch.fabric.magic_treasures.core.event;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.TagRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import java.util.Iterator;
import java.util.List;

/**
 * created by Mark Gottschling on Nov 14, 2024
 */
public class TagsLoadedHandler implements CommonLifecycleEvents.TagsLoaded {

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
                    JewelryRegistry.register(jewelry);
                    MagicTreasures.LOGGER.debug("registering jewelry -> {} ", ModUtil.getName(jewelry));
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
