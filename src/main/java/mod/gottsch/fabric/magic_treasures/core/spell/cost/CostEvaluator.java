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
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTiers;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.ICastSpellContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

/*
 * Generic cost evaluator
 * @param amount cost requested
 * @return actual cost incurred
 */
public class CostEvaluator implements ICostEvaluator {
	@Override
	public double apply(World level, Random random, ICoords coords, ICastSpellContext context, double amount) {
		Jewelry item = (Jewelry)context.getJewelry().getItem();
		JewelryVitalsComponent vitals = Jewelry.vitals(context.getJewelry()).orElseThrow(IllegalStateException::new); //context.getJewelry().get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)
		Optional<Item> stone = StoneRegistry.get(item.getGemstone());
		JewelryStoneTier stoneTier = StoneRegistry.getStoneTier(stone.orElseGet(() -> Items.AIR)).orElse(JewelryStoneTiers.NONE);

		// calculate the new amount for cost
		double newAmount = amount * item.getMaterial().getSpellCostFactor()
				* stoneTier.getSpellCostFactor();

		double cost = 0;
		if (vitals.mana() >= newAmount) {
			cost = newAmount;
			item.setMana(context.getJewelry(), MathHelper.clamp(vitals.mana() - newAmount, 0D, vitals.mana()));
			}
		else {
			cost = vitals.mana();
			item.setMana(context.getJewelry(), 0);
		}
		return cost;
	}
}
