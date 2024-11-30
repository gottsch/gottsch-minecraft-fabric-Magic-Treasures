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
package mod.gottsch.fabric.magic_treasures.core.item.component;

import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/29/2024
 */
public class CastleManaComponentBuilder extends ManaComponent.Builder {

    public CastleManaComponentBuilder(JewelryMaterial material) {
        super(material);
    }

    public CastleManaComponentBuilder(JewelryMaterial material, IJewelrySizeTier sizeTier) {
        super(material, sizeTier);
    }

    public CastleManaComponentBuilder(JewelryMaterial material, IJewelrySizeTier sizeTier, Identifier gemstone) {
        super(material, sizeTier, gemstone);
    }

    public ManaComponent build() {
        // get the stone and stone tier
        Item stone = StoneRegistry.get(this.stone).orElse(Items.AIR);
        // determine the tier
        Optional<JewelryStoneTier> stoneTier = StoneRegistry.getStoneTier(stone);

        int additionalMana = stoneTier.map(tier -> (int)(tier.getMana() * sizeTier.getManaMultiplier())).orElseGet(() -> 0);
        this.maxMana = Math.round(this.maxMana + additionalMana);

        return new ManaComponent(maxMana);
    }
}
