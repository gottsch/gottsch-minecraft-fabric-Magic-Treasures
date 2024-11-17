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
package mod.gottsch.fabric.magic_treasures.core.spell.cost;


import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.core.spell.ICastSpellContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Calculates and applies the cost to execute the jewelry.
 * @author Mark Gottschling on Jan 12, 2022
 *
 */
public interface ICostEvaluator {
	public double apply(World level, Random random, ICoords coords, ICastSpellContext context, double amount);

	@Deprecated
	default public NbtCompound save(NbtCompound tag) {
		return tag;
	}

	@Deprecated
	default public void load(NbtCompound tag) {}
}
