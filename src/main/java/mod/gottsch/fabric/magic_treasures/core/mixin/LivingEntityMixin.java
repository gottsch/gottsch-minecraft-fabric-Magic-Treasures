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

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.event.SpellEventHandler;
import mod.gottsch.fabric.magic_treasures.core.spell.EventType;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellResult;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by Mark Gottschling on 11/20/2024
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // TODO research - might have to have different version for PlayerEntity.onApplyDamage
    // since it overrides this one, but this class targets LivingEntity
    /**
     * this is fired when an Entity is about to have damage applied, but BEFORE
     * armor, potion and absorption modifiers have been applied to damage,
     * BUT after freezing, helmet modifiers have been applied.
     * @param ci
     */
//    @Inject(method = "applyDamage", at = @At(value = "INVOKE"))
//    private void onApplyDamage(DamageSource source, float amount, CallbackInfo ci) {
//        if (getWorld().isClient) {
//            return;
//        }
//
//        if ((Object)this instanceof PlayerEntity) {
//            // TODO need the net result from the process Spells so that the amount can be updated.
//            SpellEventHandler.processSpells(EventType.PLAYER_DAMAGE_PRE, ((ServerPlayerEntity)(Object)this), amount, source);
//        } else if ((Object)this instanceof MobEntity && source.getAttacker() instanceof PlayerEntity) {
//            SpellEventHandler.processSpells(EventType.MOB_DAMAGE_PRE, ((ServerPlayerEntity)source.getAttacker()), amount, source);
//        }
//    }

    /**
     * this is fire JUST BEFORE an Entity is going to have damage applied, AFTER
     * all modifiers have been applied. this is the final amount value.
     * @param source
     * @param amount
     * @param cir
     */
    @Inject(method = "modifyAppliedDamage", at = @At(value = "TAIL") )
    private void onModifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (getWorld().isClient) {
            return;
        }

        SpellResult spellResult = new SpellResult();
        if (((Object)this instanceof PlayerEntity) && (source.getAttacker() instanceof MobEntity)) {
            MagicTreasures.LOGGER.debug("modifyAppliedDamage");
            spellResult = SpellEventHandler.processSpells(EventType.PLAYER_DAMAGE_POST, ((ServerPlayerEntity)(Object)this), amount, source);
        } else if ((Object)this instanceof MobEntity && source.getAttacker() instanceof PlayerEntity) {
            spellResult = SpellEventHandler.processSpells(EventType.MOB_DAMAGE_POST, ((ServerPlayerEntity)source.getAttacker()), amount, source);
        }

        if (spellResult.success()) {
            amount = (float)spellResult.amount();
            cir.setReturnValue((float)spellResult.amount());
        }
    }

    /**
     * this is fired when an Entity is set to be damaged, BEFORE ANY
     * processing or modifiers have been execute/applied.
     */
    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (getWorld().isClient) {
            return;
        }

        SpellResult spellResult = new SpellResult();
        // TODO right now this is only working for non-PVP
        if (((Object)this instanceof PlayerEntity) && (source.getAttacker() instanceof MobEntity)) {
            MagicTreasures.LOGGER.debug("damage");
            spellResult = SpellEventHandler.processSpells(EventType.PLAYER_DAMAGE_PRE, ((ServerPlayerEntity)(Object)this), amount, source);
        } else if ((Object)this instanceof MobEntity && source.getAttacker() instanceof PlayerEntity) {
            spellResult = SpellEventHandler.processSpells(EventType.MOB_DAMAGE_PRE, ((ServerPlayerEntity)source.getAttacker()), amount, source);
        }

        if (spellResult.success()) {
            amount = (float)spellResult.amount();
        }
    }
}
