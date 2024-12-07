/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package mod.gottsch.fabric.magic_treasures.core.loot;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.loot.function.ImbueRandomly;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomGemstone;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomJewelry;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomSpellLoot;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on 12/4/2024
 */
public class ModLootFunctions {
    public static final LootFunctionType<RandomGemstone> RANDOM_GEMSTONE_LOOT_FUNCTION_TYPE = Registry.register(
            Registries.LOOT_FUNCTION_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "random_gemstone"),
            new LootFunctionType<>(RandomGemstone.CODEC)
    );

    public static final LootFunctionType<RandomJewelry> RANDOM_JEWELRY_LOOT_FUNCTION_TYPE = Registry.register(
            Registries.LOOT_FUNCTION_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "random_jewelry"),
            new LootFunctionType<>(RandomJewelry.CODEC)
    );

    public static final LootFunctionType<RandomSpellLoot> RANDOM_SPELL_LOOT_FUNCTION_TYPE = Registry.register(
            Registries.LOOT_FUNCTION_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "random_spell"),
            new LootFunctionType<>(RandomSpellLoot.CODEC)
    );

    public static final LootFunctionType<ImbueRandomly> IMBUE_RANDOMLY_LOOT_FUNCTION_TYPE = Registry.register(
            Registries.LOOT_FUNCTION_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "imbue_randomly"),
            new LootFunctionType<>(ImbueRandomly.CODEC)
    );

    // register items to built-in item group
    // force load of static properties
    public static void register() {}
}
