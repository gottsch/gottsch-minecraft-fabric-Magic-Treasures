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
 * @author Mark Gottschling on May 21, 2024
 *
 */
public class CheatDeathSpell extends CooldownSpell {

	public static String TYPE = "cheat_death";

	private static final EventType REGISTERED_EVENT = EventType.PLAYER_DAMAGE_POST;

	/**
	 *
	 * @param builder
	 */
	CheatDeathSpell(Builder builder) {
		super(builder);
	}

	/**
	 * Required so sub-classes can call super with a compatible Builder
	 * @param builder
	 */
	protected CheatDeathSpell(Spell.Builder builder) {
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
		ManaComponent manaComponent = Jewelry.mana(jewelry).orElseThrow(IllegalStateException::new);
		SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(context.getJewelry()).orElseThrow(IllegalStateException::new);

		PlayerEntity player = context.getPlayer();

		if (manaComponent.mana() > 0 && player.isAlive()) {
				// get the source and amount
				double damage = context.getAmount();
				if (damage > 0D && damage > player.getHealth()) {

					// set player's health to amount
					player.setHealth((float) spellFactorsComponent.modifyEffectAmount(getEffectAmount()));

					// cost eval
					double cost = applyCost(world, random, coords, context, modifySpellCost(jewelry));

					// reduce damage to 0
					result = new SpellResult(true, 0);
				}
		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.cheat_death.rate"),
				MathUtil.r1d(getCooldown()/20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.cheat_death.rate"),
				MathUtil.r1d(modifyCooldown(jewelry)/20.0));
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
			super(name, TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new CheatDeathSpell(this);
		}
	}
}
