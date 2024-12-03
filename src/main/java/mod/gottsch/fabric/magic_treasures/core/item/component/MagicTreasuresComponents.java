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
package mod.gottsch.fabric.magic_treasures.core.item.component;

import com.mojang.serialization.Codec;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on Nov 15, 2024
 */
public class MagicTreasuresComponents {

    public static final ComponentType<JewelryAttribsComponent> JEWELRY_ATTRIBS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "jewelry_attribs"),
            ComponentType.<JewelryAttribsComponent>builder().codec(JewelryAttribsComponent.CODEC).build()
    );

    /*
     * component to track uses, repairs, mana, etc.
     */
//    @Deprecated
//    public static final ComponentType<JewelryVitalsComponent> JEWELRY_VITALS_COMPONENT = Registry.register(
//            Registries.DATA_COMPONENT_TYPE,
//            Identifier.of(MagicTreasures.MOD_ID, JEWELRY_VITALS),
//            ComponentType.<JewelryVitalsComponent>builder().codec(JewelryVitalsComponent.CODEC).build()
//    );

    /*
     * component to track the spells (by Identifier) on a piece of jewelry.
     */
    public static final ComponentType<SpellsComponent> SPELLS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "spells"),
            ComponentType.<SpellsComponent>builder().codec(SpellsComponent.CODEC).build()
    );

    public static final ComponentType<SpellFactorsComponent> SPELL_FACTORS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "spell_factors"),
            ComponentType.<SpellFactorsComponent>builder().codec(SpellFactorsComponent.CODEC).build()
    );

    public static final ComponentType<ManaComponent> MANA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "mana"),
            ComponentType.<ManaComponent>builder().codec(ManaComponent.CODEC).build()
    );

    public static final ComponentType<MaxLevelComponent> MAX_LEVEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "max_level"),
            ComponentType.<MaxLevelComponent>builder().codec(MaxLevelComponent.CODEC).build()
    );

    public static final ComponentType<UsesComponent> USES = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "uses"),
            ComponentType.<UsesComponent>builder().codec(UsesComponent.CODEC).build()
    );

    public static final ComponentType<RepairsComponent> REPAIRS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "repairs"),
            ComponentType.<RepairsComponent>builder().codec(RepairsComponent.CODEC).build()
    );

    public static final ComponentType<RechargesComponent> RECHARGES = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "recharges"),
            ComponentType.<RechargesComponent>builder().codec(RechargesComponent.CODEC).build()
    );

    public static final ComponentType<String> RARITY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "rarity"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    // spell-specific components
    public static final ComponentType<Long> COOLDOWN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, "cooldown"),
            ComponentType.<Long>builder().codec(Codec.LONG).build()
    );
}
