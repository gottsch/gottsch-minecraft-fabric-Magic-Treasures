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
package mod.gottsch.fabric.magic_treasures.core.integration;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.event.HotbarEquipmentSpellHandler;
import mod.gottsch.fabric.magic_treasures.core.event.IEquipmentSpellHandler;
import mod.gottsch.fabric.magic_treasures.core.event.SpellEventHandler;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Created by Mark Gottschling on 11/20/2024
 */
public class MagicTreasuresIntegrations {
    private static final String className = "mod.gottsch.fabric.magic_treasures.core.event.TrinketsEquipmentSpellHandler";

    public static void registerTrinketsIntegration() {
        IEquipmentSpellHandler equipmentSpellHandler = null;

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            MagicTreasures.LOGGER.debug("curios IS loaded");
            try {
                equipmentSpellHandler =
                        (IEquipmentSpellHandler) Class.forName(className).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                MagicTreasures.LOGGER.warn("Unable to load Trinkets compatibility class -> {}", className);
            }
        }
        if (equipmentSpellHandler == null) {
            MagicTreasures.LOGGER.debug("equipmentHandler is null, using default HotbarEquipmentSpellHandler");
            equipmentSpellHandler = new HotbarEquipmentSpellHandler();
        }
        // pass the equipmentSpellHandler to the SpellEventHandler
        SpellEventHandler.register(equipmentSpellHandler);
    }
}
