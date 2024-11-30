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
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.spell.cost.CostEvaluator;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Mark Gottschling on May 16, 2024
 *
 */
public class PaladinStrikeSpell extends CooldownSpell {
	private static final float LIFE_AMOUNT = 2.0F;
	private static final float MIN_HEALTH_TO_CAST = 3.0F;

	public static String TYPE = "paladin_strike";
	private static final EventType REGISTERED_EVENT = EventType.MOB_DAMAGE_PRE;

	// the amount of health it costs in addition to mana
	private double lifeCost;

	/**
	 *
	 * @param builder
	 */
	PaladinStrikeSpell(Builder builder) {
		super(builder);
		this.lifeCost = builder.lifeCost;
	}

	@Override
	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	// TODO this currently doesn't work if this is a PVP situation.
	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public SpellResult execute(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		PlayerEntity player = context.getPlayer();
		ManaComponent manaComponent = ComponentHelper.mana(context.getJewelry()).orElseThrow(IllegalStateException::new); //context.getJewelry().get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)
		SpellFactorsComponent spellFactorsComponent =  ComponentHelper.spellFactors(context.getJewelry()).orElseThrow(IllegalStateException::new);

		if (manaComponent.mana() > 0 && player.isAlive()) {
			DamageSource source = context.getSource();
			if (source.getAttacker() instanceof PlayerEntity) {

				if (player.getHealth() > MIN_HEALTH_TO_CAST) {
					// get the source and amount
					double sourceAmount = context.getAmount();
					// calculate lifeCost
					double lifeCost = Math.min(getLifeCost(), player.getHealth() - (player.getMaxHealth() / 2));
					// calculate the damage amount based on lifeCost and modifiers
					double damageAmount = sourceAmount + (lifeCost * modifyEffectAmount(jewelry));
					applyCost(world, random, coords, context, modifySpellCost(jewelry));
					result = new SpellResult(true, damageAmount);

					MagicTreasures.LOGGER.debug("life strike damage {} onto mob -> {} ", damageAmount, source.getAttacker().getName().getString());
				}
			}
		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		double amount = lifeCost * getEffectAmount();
		return Text.translatable(LangUtil.tooltip("spell.paladin_strike.rate"),
				MathUtil.r1d(amount),
				MathUtil.r1d(getCooldown()/20.0),
				MathUtil.r1d(getSpellCost()),
				MathUtil.r1d(getLifeCost()));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		double amount = lifeCost * modifyEffectAmount(jewelry);
		return Text.translatable(LangUtil.tooltip("spell.paladin_strike.rate"),
				MathUtil.r1d(amount),
				MathUtil.r1d(modifyCooldown(jewelry)/20.0),
				MathUtil.r1d((modifySpellCost(jewelry))),
				MathUtil.r1d(getLifeCost()));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.YELLOW;
	}

	
	public static class Builder extends Spell.Builder {

		public double lifeCost = LIFE_AMOUNT;

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, TYPE, level, rarity);
			this.costEvaluator = new PaladinStrikeCostEvaluator();
		}

		public Builder withLifeCost(double lifeCost)  {
			this.lifeCost = lifeCost;
			return this;
		}

//		@Override
//		public PaladinStrikeSpell.Builder with(Consumer<PaladinStrikeSpell.Builder> builder)  {
//			builder.accept(this);
//			return this;
//		}
		
		@Override
		public Spell build() {
			return new PaladinStrikeSpell(this);
		}
	}
	
	public static class PaladinStrikeCostEvaluator extends CostEvaluator {
		/**
		 * @param amount the cost amount requested
		 * @return the actual cost incurred
		 */
		@Override
		public double apply(World world, Random random, ICoords coords, ICastSpellContext context, double amount) {
			PlayerEntity player = context.getPlayer();
			ISpell entity = context.getSpell();

			// calculate cost and reduce mana as normal
			double cost = super.apply(world, random, coords, context, amount);

			// reduce player's health by life cost
			if (entity instanceof PaladinStrikeSpell spell) {
				// calculate lifeCost
				double lifeCost = Math.min(spell.getLifeCost(), player.getHealth() - (player.getMaxHealth() / 2));
				// update player health
				player.setHealth((float) MathHelper.clamp(player.getHealth() - lifeCost, 0.0F, player.getMaxHealth()));
			}
			return cost;
		}
	}

	public double getLifeCost() {
		return lifeCost;
	}

	public void setLifeCost(double lifeCost) {
		this.lifeCost = lifeCost;
	}

	@Override
	public String toString() {
		return "PaladinStrikeSpell{" +
				"lifeCost=" + lifeCost +
				"} " + super.toString();
	}
}
