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

import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryAttribsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.ManaComponent;
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
        ItemStack rightStack = this.input.getStack(1);

        // add a spell to item
        if (!jewelryStack.isEmpty()
                && jewelryStack.isIn(MagicTreasuresTags.Items.JEWELRY)) {

            if (jewelryStack.contains(MagicTreasuresComponents.SPELLS)
                    && !rightStack.isEmpty() && rightStack.isIn(MagicTreasuresTags.Items.SPELL_SCROLLS)) {

                Optional<ItemStack> resultStack = generator.addSpells(jewelryStack, rightStack);
                resultStack.ifPresent(stack -> {
                    // TODO this is moot
                    resultOutStack.set(stack);
                    this.output.setStack(0, stack);
                    this.sendContentUpdates();
                });
                ci.cancel();
                // add a stone to jewelry
            } else if (jewelryStack.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)
                    && rightStack.isIn(MagicTreasuresTags.Items.STONES)) {
                JewelryAttribsComponent attribs = Jewelry.attribs(jewelryStack).orElseThrow(IllegalStateException::new);
                if (!attribs.hasGemstone()) {
                    /*
					 TODO need a better way of constructing a new jewelry item than using its registry name.
					 When using items from different mods, following the naming structure may not work.
					 ie. this ONLY works for items that use Magic Treasures naming convention AND don't
					 overlap in the tiers. What if 2 items have the same make-up ex copper, topaz, ring
					 */

                    // TODO somehow interrogate leftStack to determine if it is a standard naming or not. - might end up being a capability property OR a tag??
                    // TODO addStone should return an Optional
                    resultOutStack.set(generator.addStone(jewelryStack, rightStack));
                }
            }
        }
    }
}
