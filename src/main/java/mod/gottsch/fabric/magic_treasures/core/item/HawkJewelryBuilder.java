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
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.List;

import static mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems.createSettings;

/**
 * Created by Mark Gottschling on 11/21/2024
 */
public class HawkJewelryBuilder extends JewelryBuilder {

    public HawkJewelryBuilder(String modid) {
        super(modid);
    }

    @Override
    public Jewelry createJewelry(JewelryType type, JewelryMaterial material, JewelrySizeTier sizeTier, Identifier gemstone) {

        /*
          * NOTE can't use createSettings method but instead have to explicitly create the
          * setting with each component where we change the values.
         */
        Item.Settings settings = new Item.Settings()
                .component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), sizeTier.getName(),
                                material.getId(), gemstone))
                .component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent((List<Identifier>)null))
                .component(MagicTreasuresComponents.SPELL_FACTORS,
                        new SpellFactorsComponent.Builder(material, gemstone).build())
                .component(MagicTreasuresComponents.MAX_LEVEL,
                        new MaxLevelComponent.Builder(material, sizeTier)
                                .with($ -> { $.maxLevel = material.getMaxLevel() + 1;})
                                .build())
                .component(MagicTreasuresComponents.USES,
                        new UsesComponent.Builder(material, sizeTier)
                                .with($ -> {$.maxUses = HawkJewelryBuilder.this.maxUses;})
                                .build())
                .component(MagicTreasuresComponents.REPAIRS,
                        new RepairsComponent.Builder(material, sizeTier)
                                .with($ -> {
                                    $.maxRepairs = HawkJewelryBuilder.this.maxRepairs;
                                }).build())
                .component(MagicTreasuresComponents.MANA,
                        new ManaComponent.Builder(material, sizeTier, gemstone)
                                .with($ -> {
                                    $.maxMana = material.getUses() * Math.max(1, material.getRepairs() + sizeTier.getRepairs()) * Math.max(1, sizeTier.getUsesMultiplier());
                                }).build())
                .component(MagicTreasuresComponents.RECHARGES,
                        new RechargesComponent.Builder(material, gemstone).build());

        // generate the item using the generated settings
        Jewelry j = new Jewelry(settings);

        return (Jewelry) j.setBaseName(HawkJewelryBuilder.this.getBaseName())
                .setAffixer(HawkJewelryBuilder.this.acceptsAffixer)
                .setLoreKey("jewelry.hawk_ring.lore");
    }
}

