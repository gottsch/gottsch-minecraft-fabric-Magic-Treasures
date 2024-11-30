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
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 11/26/2024
 */
@Deprecated
public class JewelryComponentBuilder {
    public IJewelryType type;
    public IJewelrySizeTier sizeTier;
    public JewelryMaterial material;
    public Identifier gemstone;
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

    @Deprecated
    public JewelryComponentBuilder(String modid) {
        this.modid = modid;
    }

    public JewelryComponentBuilder(IJewelryType type, JewelryMaterial material) {
        this.type = type;
        this.material = material;
        this.sizeTier = JewelrySizeTier.REGULAR;
        this.gemstone = null;
    }

    public JewelryComponentBuilder(IJewelryType type, JewelryMaterial material, IJewelrySizeTier sizeTier) {
        this.type = type;
        this.material = material;
        this.sizeTier = sizeTier;
        this.gemstone = null;
    }

    public JewelryComponentBuilder clear() {
        type = null;
        sizeTier = null;
        material = null;
        gemstone = null;
        return this;
    }

    public JewelryComponentBuilder with(Consumer<JewelryComponentBuilder> builder) {
        builder.accept(this);
        return this;
    }

    public List<Pair<ComponentType<?>, Object>>  build() {
        List<Pair<ComponentType<?>, Object>> components = new ArrayList<>();
        if (type == null) return components;
        if (sizeTier == null) sizeTier = JewelrySizeTier.REGULAR;
        if (material == null) material = JewelryMaterials.SILVER;
        if (gemstone == null) {
            gemstone = ModUtil.getName(Items.AIR);
        }

        /*
         *build the components
         */
        // create the attribs component
        components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                new JewelryAttribsComponent(type.getName(), sizeTier.getName(), material.getId(), gemstone)));
        // max level
        components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.MAX_LEVEL,
                new MaxLevelComponent.Builder(material, sizeTier).build()));
        // uses
        components.add(new Pair<>(MagicTreasuresComponents.USES,
                new UsesComponent.Builder(material, sizeTier).build()));
        // repairs
        components.add(new Pair<>(MagicTreasuresComponents.REPAIRS,
                new RepairsComponent.Builder(material, sizeTier).build()));
        // mana
        components.add(new Pair<>(MagicTreasuresComponents.MANA,
                new ManaComponent.Builder(material, sizeTier, gemstone).build()));
        // recharges
        components.add(new Pair<>(MagicTreasuresComponents.RECHARGES,
                new RechargesComponent.Builder(material, gemstone).build()));
        // spell factors
        components.add(new Pair<>(MagicTreasuresComponents.SPELL_FACTORS,
                new SpellFactorsComponent.Builder(material, gemstone).build()));
        // spells
        components.add(new Pair<>(MagicTreasuresComponents.SPELLS,
                new SpellsComponent((List<Identifier>)null)));
        // naming and affixers

        return components;
    }

}
