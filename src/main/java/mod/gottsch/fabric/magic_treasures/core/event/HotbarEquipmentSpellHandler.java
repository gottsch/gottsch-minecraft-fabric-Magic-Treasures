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
package mod.gottsch.fabric.magic_treasures.core.event;

import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.spell.EventType;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellContext;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class HotbarEquipmentSpellHandler implements IEquipmentSpellHandler {
	private static final int MAX_HOTBAR_JEWELRY = 4;

	@Override
	public List<SpellContext> handleEquipmentSpells(EventType event, ServerPlayerEntity player) {
		final List<SpellContext> contexts = new ArrayList<>(5);
		AtomicInteger jewelryCount = new AtomicInteger(0);
		AtomicReference<String> hotbarSlotStr = new AtomicReference<>("");
		// check hotbar - get the context at each slot
		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			hotbarSlotStr.set(String.valueOf(hotbarSlot));
			ItemStack inventoryStack = player.getInventory().getStack(hotbarSlot);
			if (inventoryStack != player.getMainHandStack()) {
				ComponentHelper.spells(inventoryStack).ifPresent(spells -> {
//				inventoryStack.getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).ifPresent(cap -> {

					AtomicInteger index = new AtomicInteger();
					// requires indexed for-loop
					for (int i = 0; i < spells.spellNames().size(); i++) {
						Identifier spellName = spells.spellNames().get(i);
						Optional<ISpell> spell = SpellRegistry.get(spellName);
						if (spell.isEmpty()) {
							continue;
						}
						if (spell.get().getRegisteredEvent() != event) {
							continue;
						}
						index.set(i);
						SpellContext context  = new SpellContext.Builder().with($ -> {
							$.slotProviderId = "minecraft";
							$.slot = hotbarSlotStr.get();
							$.itemStack = inventoryStack;
//							$.capability = cap;
							$.index = index.get();
//							$.entity = entity;
							$.spell = spell.get();
						}).build();
						contexts.add(context);
					}

				});
				jewelryCount.getAndIncrement();
				if (jewelryCount.get() >= MAX_HOTBAR_JEWELRY) {
					break;
				}

			}
		}
		return contexts;
	}
}