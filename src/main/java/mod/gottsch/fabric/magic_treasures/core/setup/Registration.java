package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.event.TagsLoadedHandler;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.ModItemGroups;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

/**
 * Created by Mark Gottschling on 5/3/2023
 */
public class Registration {


    public static void register() {
        ModItemGroups.registerItemGroups();
        MagicTreasuresBlocks.register();
        MagicTreasuresItems.register();

        CommonLifecycleEvents.TAGS_LOADED.register(new TagsLoadedHandler());
    }
}
