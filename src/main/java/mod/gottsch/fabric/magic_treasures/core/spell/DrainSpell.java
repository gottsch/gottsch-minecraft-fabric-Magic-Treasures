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

import com.google.common.util.concurrent.AtomicDouble;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * 
 * @author Mark Gottschling on Dec 28, 2020
 *
 */
public class DrainSpell extends Spell {
	public static final String DRAIN_TYPE = "drain";
	private static final EventType REGISTERED_EVENT = EventType.PLAYER_LIVING_TICK;

	/**
	 *
	 * @param builder
	 */
	DrainSpell(Builder builder) {
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
	public SpellResult cast(World level, Random random, ICoords coords, ICastSpellContext context) {
		SpellResult result = new SpellResult();
		ItemStack jewelry = context.getJewelry();
		PlayerEntity player = context.getPlayer();
		ManaComponent manaComponent = ComponentHelper.mana(jewelry).orElseThrow(IllegalStateException::new);
		SpellFactorsComponent spellFactorsComponent =  ComponentHelper.spellFactors(jewelry).orElseThrow(IllegalStateException::new);

		if (level.getTime() % spellFactorsComponent.modifyFrequency(getFrequency()) == 0) {
			if (manaComponent.mana() > 0 && player.getHealth() < player.getMaxHealth() && player.isAlive()) {
				// get player position
				double px = player.getX();
				double py = player.getY();
				double pz = player.getZ();

				// calculate the new amount
				double range = getRange();
				AtomicDouble drainedHealth = new AtomicDouble(0);
				List<MobEntity> mobs = level.getEntitiesByType(
						TypeFilter.equals(MobEntity.class),
						new Box(px - range, py - range, pz - range, px + range, py + range, pz + range),
						EntityPredicates.EXCEPT_SPECTATOR);
				if (mobs.isEmpty()) {
					return result;
				}
				double effectAmount = spellFactorsComponent.modifyEffectAmount(getEffectAmount());
				mobs.forEach(mob -> {
//					boolean flag = mob.attackEntityFrom(DamageSource.GENERIC, (float)getAmount());
					boolean flag = mob.damage(level.getDamageSources().magic(), (float)effectAmount);
					MagicTreasures.LOGGER.debug("health drained from mob -> {} was successful -> {}", mob.getName(), flag);
					if (flag) {
						drainedHealth.addAndGet(effectAmount);
					}
				});

				if (drainedHealth.get() > 0.0) {
					player.setHealth(MathHelper.clamp(player.getHealth() + (float)drainedHealth.get(), 0.0F, player.getMaxHealth()));
					//					entity.setMana(MathHelper.clamp(entity.getMana() - 1D,  0D, entity.getMana()));
					applyCost(level, random, coords, context, spellFactorsComponent.modifySpellCost(getSpellCost()));
					result = new SpellResult(true, 0);
				}                
			}

		}
		return result;
	}

	@Override
	public Text getSpellDesc() {
		return Text.translatable(LangUtil.tooltip("spell.drain.rate"),
				MathUtil.r1d(getEffectAmount()),
				MathUtil.r1d(getRange()),
				MathUtil.r1d(getFrequency()/20.0));
	}

	@Override
	public Text getSpellDesc(ItemStack jewelry) {
		return Text.translatable(LangUtil.tooltip("spell.drain.rate"),
				MathUtil.r1d(modifyEffectAmount(jewelry)),
				MathUtil.r1d(modifyRange(jewelry)),
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
			super(name, DRAIN_TYPE, level, rarity);
		}

		@Override
		public ISpell build() {
			return  new DrainSpell(this);
		}
	}
}
