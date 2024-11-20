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

import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.generator.JewelryGenerator;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilEventMixin extends ForgingScreenHandler {
    private static JewelryGenerator generator = new JewelryGenerator();

    @Shadow @Final private Property levelCost;

    public AnvilEventMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At("INVOKE"), cancellable = true)
    private void onUpdate(CallbackInfo ci) {
        AtomicReference<ItemStack> resultOutStack = new AtomicReference<>(ItemStack.EMPTY);
        ItemStack outStack = ItemStack.EMPTY;
        this.levelCost.set(1);

        ItemStack jewelryStack = this.input.getStack(0);
        // add a spell to item
        if (!jewelryStack.isEmpty()
                && jewelryStack.isIn(MagicTreasuresTags.Items.JEWELRY)
                && jewelryStack.contains(MagicTreasuresComponents.SPELLS)) {
            ItemStack scrollStack = this.input.getStack(1);
            if (!scrollStack.isEmpty() && scrollStack.isIn(MagicTreasuresTags.Items.SPELL_SCROLLS)) {
                Optional<ItemStack> resultStack = generator.addSpells(jewelryStack, scrollStack);
                resultStack.ifPresent(stack -> {
                    // TODO this is moot
                    resultOutStack.set(stack);
                    this.output.setStack(0, stack);
                    this.sendContentUpdates();
                });
                ci.cancel();
            }
        }
    }
}
