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
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 
 * @author Mark Gottschling on Sep 25, 2022
 *
 */
public class MagicResistanceSpell extends Spell {
	public static final String MAGIC_RESISTENCE_TYPE = "magic_resistance";
	private static final EventType REGISTERED_EVENT = EventType.PLAYER_DAMAGE_POST;

	/**
	 *
	 * @param builder
	 */
	MagicResistanceSpell(Builder builder) {
		super(builder);
	}

	@Override
	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public SpellResult cast(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		ManaComponent manaComponent = ComponentHelper.mana(jewelry).orElseThrow(IllegalStateException::new);

		// exit if not magic damage
		if (context.getSource().isOf(DamageTypes.MAGIC)
				&& context.getPlayer().hasStatusEffect(StatusEffects.POISON)) {

			if (manaComponent.mana() > 0 && context.getPlayer().isAlive()) {
				// get the source and amount
				double amount = context.getAmount();
				// calculate the new amount
				double newAmount = 0;
				double amountToSpell = amount * Math.min(1.0, modifyEffectAmount(context.getJewelry()));
				double amountToPlayer = amount - amountToSpell;
				// Treasure.logger.debug("amount to charm -> {}); amount to player -> {}", amountToCharm, amountToPlayer);
				double cost = applyCost(world, random, coords, context, amountToSpell);
				newAmount = amountToPlayer;
				result = new SpellResult(true, newAmount);
			}    
		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.magic_resistance.rate"),
				LangUtil.asPercentString(Math.min(100, getEffectAmount() * 100)));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.magic_resistance.rate"),
				LangUtil.asPercentString(Math.min(100, modifyEffectAmount(jewelry) * 100)));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.DARK_GREEN;
	}

	/**
	 *
	 */
	public static class Builder extends Spell.Builder {

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, MAGIC_RESISTENCE_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new MagicResistanceSpell(this);
		}
	}
}
