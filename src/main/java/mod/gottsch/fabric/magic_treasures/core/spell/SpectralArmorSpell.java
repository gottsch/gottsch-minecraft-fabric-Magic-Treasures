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
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

/**
 *
 * @author Mark Gottschling on May 12, 2024
 *
 */
public class SpectralArmorSpell extends CooldownSpell {
	public static String SPECTRAL_ARMOR_TYPE = "spectral_armor";

	private static final EventType REGISTERED_EVENT = EventType.PLAYER_DAMAGE_PRE;

	/**
	 *
	 * @param builder
	 */
	SpectralArmorSpell(Builder builder) {
		super(builder);
	}

	/**
	 * Required so sub-classes can call super with a compatible Builder
	 * @param builder
	 */
	protected SpectralArmorSpell(Spell.Builder builder) {
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
		PlayerEntity player = context.getPlayer();
		ManaComponent manaComponent = Jewelry.mana(jewelry).orElseThrow(IllegalStateException::new);
		SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(jewelry).orElseThrow(IllegalStateException::new);

		if (manaComponent.mana() > 0 && player.isAlive()) {
			// TODO only execute if damage is coming from a Mob ie not Fire, Fall etc
			// get the source and amount
			double amount = context.getAmount();
			if (amount > 0D) {
				// NOTE each effectAmount integer reduces by 4% just like vanilla armor, ie Leather Chest = 3 points or 12% reduction
				// calculate the new amount
				double newAmount = amount - (amount * modifyEffectAmount(jewelry) * 0.04);
				// cost eval
				double cost = applyCost(world, random, coords, context, modifySpellCost(jewelry));
				MagicTreasures.LOGGER.debug("cost (mana) incurred to jewelry -> {}", cost);

				// update the newAmount with what comes back from cost eval
				result = new SpellResult(true, newAmount);
			}
		}

		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.spectral_armor.rate"),
				LangUtil.asPercentString(getEffectAmount() * 4.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.spectral_armor.rate"),
				LangUtil.asPercentString(modifyEffectAmount(jewelry) * 4.0));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.BLUE;
	}

	/*
	 *
	 */
	public static class Builder extends Spell.Builder {

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, SPECTRAL_ARMOR_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new SpectralArmorSpell(this);
		}
	}
}
