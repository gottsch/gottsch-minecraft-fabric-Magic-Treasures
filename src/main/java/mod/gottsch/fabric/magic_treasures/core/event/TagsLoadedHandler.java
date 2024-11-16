package mod.gottsch.fabric.magic_treasures.core.event;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.TagRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

/**
 * created by Mark Gottschling on Nov 14, 2024
 */
public class TagsLoadedHandler implements CommonLifecycleEvents.TagsLoaded {

    @Override
    public void onTagsLoaded(DynamicRegistryManager dynamicRegistryManager, boolean b) {
        MagicTreasures.LOGGER.debug("in onTagLoaded event");

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
