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

import mod.gottsch.fabric.gottschcore.spatial.Coords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.event.SpellEventHandler;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.UsesComponent;
import mod.gottsch.fabric.magic_treasures.core.spell.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerMixins extends LivingEntity {

    protected PlayerMixins(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * a simple mixin at executes at the beginning of the Player's tick event.
     * the mixin processes any jewelry/charms the player may be using.
     * @param ci
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (getWorld().isClient) {
            // don't process anything on the client/render side
            // NOTE don't cancel the tick() call.
            return;
        }
        SpellEventHandler.processSpells(EventType.PLAYER_LIVING_TICK, ((ServerPlayerEntity)(Object)this));
    }

}
