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

import com.mojang.datafixers.functions.Functions;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Mark Gottschling on 11/23/2024
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    private static final Logger log = LoggerFactory.getLogger(ItemStackMixin.class);

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
//            List<Pair<ComponentType<?>, Object>> jewelryComponents = new JewelryComponentBuilder(MagicTreasures.MOD_ID)
            JewelryComponents components = new JewelryComponents.Builder()
                    .with($ -> {
                        $.type = j.getRight().getType();
                        $.material = j.getRight().getMaterial();
                        $.size = j.getRight().getSizeTier();
                        $.stone = j.getRight().getStone();
                    }).build();
//            jewelryComponents.forEach(jc -> {
//                (((ItemStack)(Object)this).set(jc.getLeft().cl, jc.getRight());
//                Item.Settings ukjhjh
//            });

            // TODO this is an explicit way of doing this..... research having a map of component<T>
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.JEWELRY_ATTRIBS, components.getAttribs());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.MAX_LEVEL, components.getMaxLevel());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.USES, components.getUses());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.REPAIRS, components.getRepairs());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.MANA, components.getMana());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.RECHARGES, components.getRecharges());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.SPELL_FACTORS, components.getSpellFactors());
            ((ItemStack)(Object)this).set(MagicTreasuresComponents.SPELLS, components.getSpells());
        });
//    });
    }
}
