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
package mod.gottsch.fabric.magic_treasures;

import mod.gottsch.fabric.magic_treasures.core.config.MyConfig;
import mod.gottsch.fabric.magic_treasures.core.setup.CommonSetup;
import mod.gottsch.fabric.magic_treasures.core.setup.Registration;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class MagicTreasures implements ModInitializer {
	public static final String MOD_ID = "magictreasures";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	// logger
//	public static Logger LOGGER = LogManager.getLogger(MagicTreasures.MOD_ID);

	public static final MyConfig CONFIG = MyConfig.createAndLoad();

	public MagicTreasures() {
		// force load of static blocks
		MagicTreasuresSpells.init();
	}

	@Override
	public void onInitialize() {
		CommonSetup.init();
		Registration.register();
		// NOTE comes after Item registration
		CommonSetup.registerJewelryCustomComponentsBuilders();
	}
}