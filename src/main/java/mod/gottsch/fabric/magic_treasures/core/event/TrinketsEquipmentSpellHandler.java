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

import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.spell.EventType;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellContext;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mark Gottschling on 12/7/2024
 */
public class TrinketsEquipmentSpellHandler implements IEquipmentSpellHandler {
    private static final String TRINKETS_ID = "trinkets";

    @Override
    public List<SpellContext> handleEquipmentSpells(EventType event, ServerPlayerEntity player) {
        List<SpellContext> contexts = new ArrayList<>();

        // check curio slots
        Map<String, SlotGroup> handler = TrinketsApi.getPlayerSlots(player);

        TrinketsApi.getTrinketComponent(player).ifPresent(c -> {
            List<Pair<SlotReference, ItemStack>> equipped = c.getAllEquipped();
            equipped.forEach(pair -> {
                ItemStack trinketStack = pair.getRight();
                ComponentHelper.spells(trinketStack).ifPresent(spells -> {
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
                        SpellContext curiosContext = new SpellContext.Builder().with($ -> {
                            $.slotProviderId = TRINKETS_ID;
                            // MOOT as sync is done by data components
//                            $.slot = slot;
                            $.itemStack = trinketStack;
                            $.index = index.get();
                            $.spell = spell.get();
                        }).build();
                        contexts.add(curiosContext);
                    }
                });
            });
        });

        return contexts;
    }
}
