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

import mod.gottsch.fabric.gottschcore.spatial.Coords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.UsesComponent;
import mod.gottsch.fabric.magic_treasures.core.spell.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

/**
 * Created by Mark Gottschling on 11/18/2024
 */
public class SpellEventHandler {
    private static IEquipmentSpellHandler equipmentSpellHandler;

    /**
     * this class deviates a lot from the Forge version. here it is a Singleton where the
     * equipment handler is registered.
     * all the 'event' tie-ins are handled via mixins since most don't exist as events
     * out-of-the-box in Fabric.
     */
    private SpellEventHandler() {}

    public static void register(IEquipmentSpellHandler handler) {
        if (SpellEventHandler.equipmentSpellHandler == null) {
            SpellEventHandler.equipmentSpellHandler = handler;
        }
    }

    public static IEquipmentSpellHandler equipmentSpellHandler() {
        return SpellEventHandler.equipmentSpellHandler;
    }


    /**
     *
     * @param event
     * @param player
     */
    public static SpellResult processSpells(EventType event, ServerPlayerEntity player) {
        return processSpells(event, player, 0, null);
    }

    /**
     *
     * @param event
     * @param player
     * @param amount
     * @param source
     */
    public static SpellResult processSpells(EventType event, ServerPlayerEntity player, double amount, DamageSource source) {
        /*
         * a list of spell contexts to execute
         */
        List<SpellContext> spellsToExecute;

        // gather all spells
        spellsToExecute = gatherSpells(event, player);

        // sort spells
        Collections.sort(spellsToExecute, SpellContext.priorityComparator);

        // execute spells
        return executeSpells(event, player, spellsToExecute, amount, source);

    }

    /**
     * Examine and collect all Spells that the player has in valid slots.
     * @param event
     * @param player
     * @return
     */
    private static List<SpellContext> gatherSpells(EventType event, ServerPlayerEntity player) {
        final List<SpellContext> contexts = new ArrayList<>(5);

        // check each hand
        for (Hand hand : Hand.values()) {
            ItemStack heldStack = player.getStackInHand(hand);
            if (heldStack.contains(MagicTreasuresComponents.SPELLS)) {
                contexts.addAll(getSpellsFromStack(event, hand, "", heldStack));
            }
        }

        // check equipment slots
        if (SpellEventHandler.equipmentSpellHandler() != null) {
            List<SpellContext> equipmentContexts = SpellEventHandler.equipmentSpellHandler().handleEquipmentSpells(event, player);
            contexts.addAll(equipmentContexts);
        }

        return contexts;
    }

    /**
     *
     * @param event
     * @param hand
     * @param slot
     * @param itemStack
     * @return
     */
    private static List<SpellContext> getSpellsFromStack(EventType event, Hand hand, String slot, ItemStack itemStack) {
        final List<SpellContext> contexts = new ArrayList<>(5);

        Optional<SpellsComponent> spellsComponent = Jewelry.spells(itemStack);
        if (spellsComponent.isPresent()) {
            int index = 0;
            for (int i = 0; i < spellsComponent.get().spellNames().size(); i++) {
                Identifier spellName = spellsComponent.get().spellNames().get(i);
                Optional<ISpell> spell = SpellRegistry.get(spellName);
                if (spell.isEmpty() || spell.get().getRegisteredEvent() != event) {
                    continue;
                }
                index = i;
                SpellContext context = new SpellContext.Builder()
                        .withIndex(index)
                        .with($ -> {
                            $.hand = hand;
                            $.slot = slot;
                            $.slotProviderId = "minecraft";
                            $.itemStack = itemStack;
                            $.spell = spell.get();
                        }).build();
                contexts.add(context);
            }
        }
        return contexts;
    }

    /**
     *
     * @param event
     * @param player
     * @param contexts
     */
    private static SpellResult executeSpells(EventType event, ServerPlayerEntity player, List<SpellContext> contexts, double amount, DamageSource source) {
        /*
         * a list of spell types that are non-stackable that should not be executed more than once.
         */
        final List<String> executeOnceSpellTypes = new ArrayList<>(5);

        SpellResult result = new SpellResult();
        double newAmount = amount;

//        contexts.forEach(context -> {
        for (SpellContext context : contexts) {
            ISpell spell = (ISpell)context.getSpell();
//			MagicTreasures.LOGGER.debug("processing spell -> {}", spell.getName().toString());
            if (!spell.isEffectStackable()) {
                // TODO this probably needs to change to spell.getName comparison
                // check if this spell type is already in the monitored list
                if (executeOnceSpellTypes.contains(spell.getType())) {
                    continue;
                }
                else {
                    // add the spell type to the monitored list
                    executeOnceSpellTypes.add(spell.getType());
                }
            }

            // create a cast context
            ICastSpellContext castContext = new CastSpellContext(
                    context.getItemStack(), null, context.getSpell(), player, newAmount, source);

            // if spell is executable and executes successfully
            SpellResult spellResult = context.getSpell().cast(player.getServerWorld(), new Random(), Coords.of(player.getBlockPos()), castContext);
            if (spellResult.success()) {
//				MagicTreasures.LOGGER.debug("spell {} successfully updated.", spell.getName().toString());
                processUsage(player.getServerWorld(), player, event, context);
                newAmount = spellResult.amount();
            }

            // update the final result
            result = new SpellResult(result.success() || spellResult.success(), newAmount);
        }

        // return the final result
        return result;
    }

    /**
     *
     * @param world
     * @param player
     * @param event
     * @param context
     */
    private static void processUsage(World world, PlayerEntity player, EventType event, SpellContext context) {
        MagicTreasures.LOGGER.debug("processing usage for spell -> {}", context.getSpell().getName().toString());
        ItemStack stack = context.getItemStack();
        // get uses component
        Jewelry.uses(stack).ifPresent(uses -> {
            if (uses.isInfinite()) {
                return;
            } else {
                // remove/destroy item stack if damage is greater than durability
                stack.set(MagicTreasuresComponents.USES, new UsesComponent(uses.maxUses(), uses.uses() - 1));
                if (uses.uses() <= 0) {
                    stack.decrement(1);
                }
            }
        });
    }
}
