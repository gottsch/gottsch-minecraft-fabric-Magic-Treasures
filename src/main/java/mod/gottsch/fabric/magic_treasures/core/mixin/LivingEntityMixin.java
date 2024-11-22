package mod.gottsch.fabric.magic_treasures.core.mixin;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
     * armor, potion and absorption modifiers have been applied to damage.
     * @param ci
     */
    @Inject(method = "applyDamage", at = @At(value = "INVOKE"))
    private void onApplyDamage(DamageSource source, float amount, CallbackInfo ci) {
        if (getWorld().isClient) {
            return;
        }

    }

    /**
     * this is fire JUST BEFORE an Entity is going to have damage applied, AFTER
     * all modifiers have been applied. this is the final amount value.
     * @param source
     * @param amount
     * @param cir
     */
    @Inject(method = "modifyAppliedDamage", at = @At(value = "TAIL") )
    private void onModifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {

    }

    /**
     * this is fired when an Entity is set to be damaged, BEFORE ANY
     * processing or modifiers have been execute/applied.
     */
    @Inject(method = "damage", at = @At(value = "INVOKE"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (getWorld().isClient) {
            return;
        }

    }
}
