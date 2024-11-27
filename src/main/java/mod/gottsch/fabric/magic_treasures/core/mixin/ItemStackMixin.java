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
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryComponentBuilder;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Created by Mark Gottschling on 11/23/2024
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {

//    @Shadow private ComponentMapImpl components;

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;)V", at = @At("RETURN"))
    public void onConstruction(ItemConvertible item, CallbackInfo ci) {
        // TODO lookup to the jewelry registry to get attribs to build components
//        if (item.asItem() instanceof Jewelry) {
//            MagicTreasures.LOGGER.debug("captured constructor of {}", item.asItem().getName().getString());
//        }
//        if (((ItemStack)(Object)this).contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)) {
//            MagicTreasures.LOGGER.debug("has attribs component");
//        }
        JewelryRegistry.getAttribs(item.asItem()).ifPresent(j -> {
            List<Pair<ComponentType<?>, Object>> jewelryComponents = new JewelryComponentBuilder(MagicTreasures.MOD_ID)
                    .with($ -> {
                        $.type = j.getRight().getType();
                        $.material = j.getRight().getMaterial();
                        $.size = j.getRight().getSizeTier();
                        $.stone = j.getRight().getStone();
                    }).build();
            jewelryComponents.forEach(jc -> {
                (((ItemStack)(Object)this).set(jc.getLeft(), jc.getRight());
            });
        });
    });
}
