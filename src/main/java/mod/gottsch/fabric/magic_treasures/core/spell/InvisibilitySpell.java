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
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Mark Gottschling on June 17, 2024
 *
 */
public class InvisibilitySpell extends CooldownSpell {
	public static final String TYPE = "invisibility";
	private static final EventType REGISTERED_EVENT = EventType.PLAYER_LIVING_TICK;

	// amount to amplify strength effect by
	private int amplifier = 0;

	/**
	 *
	 * @param builder
	 */
	InvisibilitySpell(Builder builder) {
		super(builder);
		this.amplifier = builder.amplifier;
	}

	@Override
	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public SpellResult execute(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ManaComponent manaComponent = ComponentHelper.mana(context.getJewelry()).orElseThrow(IllegalStateException::new); //context.getJewelry().get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)
		SpellFactorsComponent spellFactorsComponent = Jewelry.spellFactors(context.getJewelry()).orElseThrow(IllegalStateException::new);

		if (manaComponent.mana() > 0 && context.getPlayer().isAlive()) {
			if (!context.getPlayer().hasStatusEffect(StatusEffects.INVISIBILITY)) {
				context.getPlayer().addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, spellFactorsComponent.modifyDuration(getDuration()), getAmplifier()));
			}
			applyCost(world, random, coords, context, spellFactorsComponent.modifySpellCost(getSpellCost()));
			result = new SpellResult(true, 0);
		}    		
		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.invisibility.rate"),
				MathUtil.r1d(getDuration() / 20.0),
				MathUtil.r1d(getCooldown() / 20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.invisibility.rate"),
				MathUtil.r1d(modifyDuration(jewelry) / 20.0),
				MathUtil.r1d(modifyCooldown(jewelry) / 20.0));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.AQUA;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public static class Builder extends Spell.Builder {
		public int amplifier;

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, TYPE, level, rarity);
		}

		public Builder withAmplifier(int amplifier)  {
			this.amplifier = amplifier;
			return this;
		}

		@Override
		public Spell build() {
			return  new InvisibilitySpell(this);
		}
	}
}
