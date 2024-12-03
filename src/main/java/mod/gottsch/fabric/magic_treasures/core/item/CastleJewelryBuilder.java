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
package mod.gottsch.fabric.magic_treasures.core.item;

import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on 11/21/2024
 */
public class CastleJewelryBuilder extends JewelryBuilder {

    public CastleJewelryBuilder(String modid) {
        super(modid);
    }

    @Override
    public JewelryComponents.Builder createComponentsBuilder(JewelryType type, JewelryMaterial material, JewelrySizeTier size, Identifier gemstone) {
        JewelryComponents.Builder builder = new JewelryComponents.Builder(type, material, size)
                .with($ -> {
                    $.gemstone = gemstone;
                    $.maxLevelComponent = this.maxLevel <= 0 ? new MaxLevelComponent(material.getMaxLevel() + 1) : new MaxLevelComponent(this.maxLevel);
                    $.usesComponent = this.maxUses <= 0 ? null : new UsesComponent(this.maxUses);
                    $.repairsComponent = this.maxRepairs < 0 ? null : new RepairsComponent(this.maxRepairs);
                    $.infinite = this.infinite;
                    $.manaComponentBuilder = this.maxMana <= 0 ? new CastleManaComponentBuilder(material, size, gemstone)
                            .with($$ -> {
                                $$.maxMana = material.getMana() * 2.5;
                            })
                            : new CastleManaComponentBuilder(material, size, gemstone)
                            .with($$ -> {
                                $$.maxMana = this.maxMana;
                            });
                    $.rechargesComponent = this.maxRecharges < 0 ? null : new RechargesComponent(this.maxRecharges);
                    $.spellsComponent = null;
                });
        return builder;
    }
}

