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
package mod.gottsch.fabric.magic_treasures.core.spell;


import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

/**
 *
 * @author Mark Gottschling on Apr 30, 2020
 *
 */
public class ManaShieldSpell extends CooldownSpell {
	public static String SHIELDING_TYPE = "mana_shield";

	// executed AFTER armor etc modifiers BEFORE inflicting the damage
	private static final EventType REGISTERED_EVENT = EventType.PLAYER_DAMAGE_POST;

	/**
	 *
	 * @param builder
	 */
	ManaShieldSpell(Builder builder) {
		super(builder);
	}

	/**
	 * Required so sub-classes can call super with a compatible Builder
	 * @param builder
	 */
	protected ManaShieldSpell(Spell.Builder builder) {
		super(builder);
	}

	@Override
	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	@Override
	public SpellResult execute(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		ManaComponent manaComponent = ComponentHelper.mana(jewelry).orElseThrow(IllegalStateException::new);

		if (manaComponent.mana() > 0 && context.getPlayer().isAlive()) {
			// get the source and amount from the context
			double amount = context.getAmount();
			if (amount > 0D) {
				// calculate the new amount
				double amountToSpell = amount * modifyEffectAmount(jewelry);
				double amountToPlayer = amount - amountToSpell;
				double newAmount = amountToPlayer;
				MagicTreasures.LOGGER.debug("amount to jewelry -> {} amount to player -> {}", amountToSpell, amountToPlayer);
				// cost eval
				double cost = applyCost(world, random, coords, context, amountToSpell);
				MagicTreasures.LOGGER.debug("cost (mana) incurred to jewelry -> {}", cost);
//				if (cost < amountToSpell) {
//					newAmount =+ (amountToSpell - cost);
//				}
				MagicTreasures.LOGGER.debug("new amount to player -> {}", newAmount);
				result = new SpellResult(true, amountToPlayer);
			}
		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.mana_shield.rate"),
				LangUtil.asPercentString(getEffectAmount() * 100),
				MathUtil.r1d(getFrequency()/20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.mana_shield.rate"),
				LangUtil.asPercentString(modifyEffectAmount(jewelry) * 100),
				MathUtil.r1d(modifyFrequency(jewelry)/20.0));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.DARK_BLUE;
	}


	/*
	 *
	 */
	public static class Builder extends Spell.Builder {

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, SHIELDING_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new ManaShieldSpell(this);
		}
	}
}
