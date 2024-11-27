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
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.JewelryBuilder;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 11/26/2024
 */
public class JewelryComponentBuilder {
    public IJewelryType type;
    public IJewelrySizeTier size;
    public JewelryMaterial material;
    public Identifier stone;
    public boolean useItemName = false;
    public String baseName;
    public String loreKey;
    public int maxUses = -1;
    public int maxMana = -1;
    public int maxRepairs = -1;
    public int maxLevel = 0;
    public Predicate<ItemStack> acceptsAffixer = p -> true;

    @Deprecated
    protected String modid;

    public JewelryComponentBuilder(String modid) {
        this.modid = modid;
    }

    public JewelryComponentBuilder clear() {
        type = null;
        size = null;
        material = null;
        stone = null;
        return this;
    }

    public JewelryComponentBuilder with(Consumer<JewelryComponentBuilder> builder) {
        builder.accept(this);
        return this;
    }

    public List<Pair<ComponentType<?>, Object>>  build() {
        List<Pair<ComponentType<?>, Object>> components = new ArrayList<>();
        if (type == null) return components;
        if (size == null) size = JewelrySizeTier.REGULAR;
        if (material == null) material = JewelryMaterials.SILVER;
        if (stone == null) {
            stone = ModUtil.getName(Items.AIR);
        }

        /*
         *build the components
         */
        // create the attribs component
        components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                new JewelryAttribsComponent(type.getName(), size.getName(), material.getId(), stone)));
        // max level
        components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.MAX_LEVEL,
                new MaxLevelComponent.Builder(material, size).build()));
        // uses
        components.add(new Pair<>(MagicTreasuresComponents.USES,
                new UsesComponent.Builder(material, size).build()));
        // repairs
        components.add(new Pair<>(MagicTreasuresComponents.REPAIRS,
                new RepairsComponent.Builder(material, size).build()));
        // mana
        components.add(new Pair<>(MagicTreasuresComponents.MANA,
                new ManaComponent.Builder(material, size, stone).build()));
        // recharges
        components.add(new Pair<>(MagicTreasuresComponents.RECHARGES,
                new RechargesComponent.Builder(material, stone).build()));
        // spell factors
        components.add(new Pair<>(MagicTreasuresComponents.SPELL_FACTORS,
                new SpellFactorsComponent.Builder(material, stone).build()));
        // spells
        components.add(new Pair<>(MagicTreasuresComponents.SPELLS,
                new SpellsComponent((List<Identifier>)null)));
        // naming and affixers

        return components;
    }

}
