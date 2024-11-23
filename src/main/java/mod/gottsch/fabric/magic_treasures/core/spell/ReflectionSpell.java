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
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import mod.gottsch.fabric.magic_treasures.core.util.MathUtil;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Fired on LivingHurtEvent, so the original amount of damage INTENDED (ie not actual Damage) to be
 * inflicted on Player is reflected back on mob.
 * reflection: value = # of uses, duration = range, percent = % of damage reflected
 * @author Mark Gottschling on Apr 30, 2020
 *
 */
public class ReflectionSpell extends CooldownSpell {
	public static String REFLECTION_TYPE = "reflection";

	private static final EventType REGISTERED_EVENT = EventType.PLAYER_DAMAGE_PRE;

	/**
	 *
	 * @param builder
	 */
	ReflectionSpell(Builder builder) {
		super(builder);
	}

	public EventType getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	@Override
	public SpellResult execute(World world, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		ManaComponent manaComponent = Jewelry.mana(jewelry).orElseThrow(IllegalStateException::new);
		PlayerEntity player = context.getPlayer();

		if (manaComponent.mana() > 0 && player.isAlive()) {
						// get player position
				double px = player.getX();
				double py = player.getY();
				double pz = player.getZ();

				// get the source and amount
				double amount = context.getAmount();
				// calculate the new amount
				double reflectedAmount = amount * modifyEffectAmount(jewelry);
				double range = modifyRange(jewelry);
				List<MobEntity> mobs = world.getEntitiesByType(
						TypeFilter.equals(MobEntity.class),
						new Box(px - range, py - range, pz - range, px + range, py + range, pz + range),
						EntityPredicates.EXCEPT_SPECTATOR);
				// hurt the mob with reflected amount
				mobs.forEach(mob -> {
					boolean flag = mob.damage(world.getDamageSources().magic(), (float) reflectedAmount);
					MagicTreasures.LOGGER.debug("reflected damage {} onto mob -> {} was successful -> {}", reflectedAmount, mob.getName(), flag);
				});

				applyCost(world, random, coords, context, Math.min(modifySpellCost(jewelry), reflectedAmount));
				result = new SpellResult(true, context.getAmount());
			}

		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.reflection.rate"),
				LangUtil.asPercentString(getEffectAmount() * 100),
				MathUtil.r1d(getCooldown()/20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.reflection.rate"),
				LangUtil.asPercentString(modifyEffectAmount(jewelry) * 100),
				MathUtil.r1d(modifyCooldown(jewelry)/20.0));
	}

	@Override
	public Formatting getSpellLabelColor() {
		return Formatting.YELLOW;
	}

	/**
	 *
	 */
	public static class Builder extends Spell.Builder {

		public Builder(Identifier name, int level, IRarity rarity) {
			super(name, REFLECTION_TYPE, level, rarity);
		}

		@Override
		public Spell build() {
			return  new ReflectionSpell(this);
		}
	}
}