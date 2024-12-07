/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
 *
 * Magic Treasures is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magic Treasures is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Magic Treasures.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.magic_treasures.core.setup;

import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.event.TagsLoadedHandler;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.ModItemGroups;
import mod.gottsch.fabric.magic_treasures.core.loot.ModLootFunctions;
import mod.gottsch.fabric.magic_treasures.core.loot.ModLootTableModifiers;
import mod.gottsch.fabric.magic_treasures.core.world.gen.ModWorldGeneration;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;

/**
 * Created by Mark Gottschling on 5/3/2023
 */
public class Registration {


    public static void register() {
        ModItemGroups.registerItemGroups();
        MagicTreasuresBlocks.register();
        MagicTreasuresItems.register();
        ModLootFunctions.register();
        ModWorldGeneration.generateModWorldGen();
        ModLootTableModifiers.modifyLootTables();

        CommonLifecycleEvents.TAGS_LOADED.register(new TagsLoadedHandler());
    }
}
