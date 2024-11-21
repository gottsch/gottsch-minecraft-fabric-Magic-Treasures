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


import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 
 * @author Mark Gottschling on Apr 30, 2020
 *
 */
public class ManaShieldSpell extends Spell {
	public static String SHIELDING_TYPE = "mana_shield";

	// before armor and potions - TODO change
	private static final EventType REGISTERED_EVENT = EventType.LIVING_TICK;

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

	// TODO need multiple signatures because there aren't Events, but using mixins at different points which
	// may require to return different values ex damage amount. the resultant effects cannot be contained
	// solely with the spell method for every situation.
	// TODO or may have to return a catch-all return value such as SpellResult which contains various properties
	// such as boolean/success, newAmount, etc.
	public SpellResult cast(World world, Random random, ICoords coords, ICastSpellContext context) {
		double newAmount = 0;

		return new SpellResult(true, newAmount);
	}

	@Override
	public boolean serverUpdate(World world, Random random, ICoords coords, ICastSpellContext context) {
		boolean result = false;
		ItemStack jewelry = context.getJewelry();
		ManaComponent manaComponent = Jewelry.mana(jewelry).orElseThrow(IllegalStateException::new);
//		SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(context.getJewelry()).orElseThrow(IllegalStateException::new);

		// TODO extend CooldownSpell class and update
//		if (context.getEntity() instanceof CooldownSpellEntity spellEntity) {
            double cooldown = modifyCooldown(jewelry);

			// check if supports cooldown or if world time has exceeded the entity cooldown end time
		if (context.getJewelry().getOrDefault(MagicTreasuresComponents.COOLDOWN, 0L) <= 0.0 || (world.getTime() > cooldownExpireTime)) {
			if (manaComponent.mana() > 0 && context.getPlayer().isAlive()) {
				// TODO check the correct target - this should be moot if the EventType are granular enough

					if (((LivingDamageEvent)event).getEntity() instanceof Player) {
						// TODO another issue. since we don't have an event, there isn't an amount - if has to be passed in from the Mixin
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
							if (cost < amountToSpell) {
								newAmount =+ (amountToSpell - cost);
							}
							MagicTreasures.LOGGER.debug("new amount to player -> {}", amountToPlayer);
							// update the newAmount with what comes back from cost eval
							((LivingDamageEvent)event).setAmount((float) newAmount);

							// set cooldown expire time if cooldown is activated
							if (cooldown > 0.0) {
								((CooldownSpellEntity) context.getEntity()).setCooldownExpireTime(Long.valueOf(world.getGameTime()).doubleValue() + cooldown);
							}
							result = true;
						}
					}
				}
			}
//		}
		return result;
	}

	@Override
	public Component getSpellDesc() {
		return Component.translatable(LangUtil.tooltip("spell.mana_shield.rate"),
				LangUtil.asPercentString(getEffectAmount() * 100),
				MathUtil.r1d(getFrequency()/20.0));
	}

	@Override
	public Component getSpellDesc(ItemStack jewelry) {
		return Component.translatable(LangUtil.tooltip("spell.mana_shield.rate"),
				LangUtil.asPercentString(modifyEffectAmount(jewelry) * 100),
				MathUtil.r1d(modifyFrequency(jewelry)/20.0));
	}

	@Override
	public ChatFormatting getSpellLabelColor() {
		return ChatFormatting.DARK_BLUE;
	}

	
	/*
	 * 
	 */
	public static class Builder extends Spell.Builder {

		public Builder(ResourceLocation name, int level, IRarity rarity) {
			super(name, SHIELDING_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new ManaShieldSpell(this);
		}
	}
}
