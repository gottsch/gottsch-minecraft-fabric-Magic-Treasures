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

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on Nov 15, 2024
 */
public class MagicTreasuresComponents {
    public static final String JEWELRY_VITALS = "jewelry_vitals";
    public static final String SPELLS = "spells";
    public static final String SPELL_FACTORS = "spell_factors";

    /*
     * component to track uses, repairs, mana, etc.
     */
    public static final ComponentType<JewelryVitalsComponent> JEWELRY_VITALS_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, JEWELRY_VITALS),
            ComponentType.<JewelryVitalsComponent>builder().codec(JewelryVitalsComponent.CODEC).build()
    );

    /*
     * component to track the spells (by Identifier) on a piece of jewelry.
     */
    public static final ComponentType<SpellsComponent> SPELLS_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, SPELLS),
            ComponentType.<SpellsComponent>builder().codec(SpellsComponent.CODEC).build()
    );

    public static final ComponentType<SpellFactorsComponent> SPELL_FACTORS_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MagicTreasures.MOD_ID, SPELL_FACTORS),
            ComponentType.<SpellFactorsComponent>builder().codec(SpellFactorsComponent.CODEC).build()
    );
}
