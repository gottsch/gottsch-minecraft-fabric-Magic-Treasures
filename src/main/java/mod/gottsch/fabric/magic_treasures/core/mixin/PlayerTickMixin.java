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
package mod.gottsch.fabric.magic_treasures.core.mixin;

import com.mojang.authlib.GameProfile;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.event.SpellEventHandler;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.spell.EventType;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellContext;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixin extends LivingEntity {

    protected PlayerTickMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (getWorld().isClient) {
            // don't process anything on the client/render side
            // NOTE don't cancel the tick() call.
            return;
        }
        MagicTreasures.LOGGER.info("in player tick!");

        // TODO call the SpellHandler to gather jewelry + spells and execute
        checkSpellsInteraction(EventType.LIVING_TICK, ((ServerPlayerEntity)(Object)this));
    }

    public void checkSpellsInteraction(EventType type, ServerPlayerEntity player) {

        // do something to player every update tick:
//        if (event.getEntity() instanceof Player) {
            // get the player
//            ServerPlayer player = (ServerPlayer) event.getEntity();
            processSpells(type, player);
//        }
    }

    private void processSpells(EventType event, ServerPlayerEntity player) {
        /*
         * a list of spell contexts to execute
         */
        List<SpellContext> spellsToExecute;

        // gather all spells
        spellsToExecute = gatherSpells(event, player);

        // sort spells
        Collections.sort(spellsToExecute, SpellContext.priorityComparator);

        // execute spells
//        executeSpells(event, player, spellsToExecute);
    }

    /**
     * Examine and collect all Spells (not SpellEntity) that the player has in valid slots.
     * @param event
     * @param player
     * @return
     */
    @Unique
    private List<SpellContext> gatherSpells(EventType event, ServerPlayerEntity player) {
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

    @Unique
    private List<SpellContext> getSpellsFromStack(EventType event, Hand hand, String slot, ItemStack itemStack) {
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
}
