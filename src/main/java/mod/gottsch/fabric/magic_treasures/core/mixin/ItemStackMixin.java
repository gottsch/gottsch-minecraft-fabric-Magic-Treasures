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

import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Mark Gottschling on 11/23/2024
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    private static final Logger log = LoggerFactory.getLogger(ItemStackMixin.class);

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;)V", at = @At("RETURN"))
    public void onConstruction(ItemConvertible item, CallbackInfo ci) {

        JewelryRegistry.getComponentsBuilder(item.asItem()).ifPresentOrElse(
                builder -> {
                    JewelryComponents components = builder.build();
                    updateComponents(((ItemStack)(Object)this), components);
                },
                // TODO this will be going away
                () -> {
                    JewelryRegistry.getAttribs(item.asItem()).ifPresent(jewelryAttribsPair -> {
                        JewelryComponents components = new JewelryComponents.Builder(jewelryAttribsPair.getRight().getType(), jewelryAttribsPair.getRight().getMaterial(), jewelryAttribsPair.getRight().getSizeTier())
                                .with($ -> {
                                    $.gemstone = jewelryAttribsPair.getRight().getStone();
                                }).build();
                         updateComponents(((ItemStack)(Object)this), components);

                    });
                }
        );
//    });
    }

    public void updateComponents(ItemStack stack, JewelryComponents components) {
        stack.set(MagicTreasuresComponents.JEWELRY_ATTRIBS, components.getAttribs());
        stack.set(MagicTreasuresComponents.MAX_LEVEL, components.getMaxLevel());
        stack.set(MagicTreasuresComponents.USES, components.getUses());
        stack.set(MagicTreasuresComponents.REPAIRS, components.getRepairs());
        stack.set(MagicTreasuresComponents.MANA, components.getMana());
        stack.set(MagicTreasuresComponents.RECHARGES, components.getRecharges());
        stack.set(MagicTreasuresComponents.SPELL_FACTORS, components.getSpellFactors());
        stack.set(MagicTreasuresComponents.SPELLS, components.getSpells());
        stack.set(MagicTreasuresComponents.RARITY, components.getRarity());
    }
}
