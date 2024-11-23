/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.magic_treasures.core.spell;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

/**
 *
 * @author Mark Gottschling on May 2, 2020
 *
 */
public class SatietySpell extends Spell {
	public static final int MAX_FOOD_LEVEL = 20;
	public static final String SATIETY_TYPE = "satiety";

	private static final EventType REGISTERED_EVENT = EventType.PLAYER_LIVING_TICK;

	/**
	 *
	 * @param builder
	 */
	SatietySpell(Builder builder) {
		super(builder);
	}

	@Override
	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	@Override
	public SpellResult cast(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		PlayerEntity player = context.getPlayer();
		ManaComponent manaComponent = Jewelry.mana(jewelry).orElseThrow(IllegalStateException::new);
		SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(jewelry).orElseThrow(IllegalStateException::new);

		if (world.getTime() % modifyFrequency(jewelry) == 0) {
			if (player.isAlive() && manaComponent.mana() > 0 && player.getHungerManager().getFoodLevel() < MAX_FOOD_LEVEL) {
				double amount = modifyEffectAmount(jewelry);
				player.getHungerManager().add((int)amount, 0.1F);
				applyCost(world, random, coords, context, modifySpellCost(jewelry));
				result = new SpellResult(true, 0);
			}
		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		// "Restores 0.5 hunger every %s seconds."
		return Text.translatable(LangUtil.tooltip("spell.satiety.rate"),
				MathUtil.r1d(getFrequency()/20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		// "Restores 0.5 hunger every %s seconds."
		return Text.translatable(LangUtil.tooltip("spell.satiety.rate"),
				MathUtil.r1d(modifyFrequency(jewelry)/20.0));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.GREEN;
	}

	/**
	 *
	 */
	public static class Builder extends Spell.Builder {

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, SATIETY_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new SatietySpell(this);
		}
	}
}
