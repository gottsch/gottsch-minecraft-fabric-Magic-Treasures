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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on 11/27/2024
 */
public class JewelryComponents {
    private JewelryAttribsComponent attribs;
    private MaxLevelComponent maxLevel;
    private UsesComponent uses;
    private RepairsComponent repairs;
    private ManaComponent mana;
    private RechargesComponent recharges;
    private SpellFactorsComponent spellFactors;
    private SpellsComponent spells;

    public static class Builder {
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

//        public JewelryComponentBuilder(String modid) {
//            this.modid = modid;
//        }

        public Builder clear() {
            type = null;
            size = null;
            material = null;
            stone = null;
            return this;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public JewelryComponents build() {
//            List<Pair<ComponentType<?>, Object>> components = new ArrayList<>();
            JewelryComponents components = new JewelryComponents();

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
//            components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.JEWELRY_ATTRIBS,
//                    new JewelryAttribsComponent(type.getName(), size.getName(), material.getId(), stone)));
//            // max level
//            components.add(new Pair<ComponentType<?>, Object>(MagicTreasuresComponents.MAX_LEVEL,
//                    new MaxLevelComponent.Builder(material, size).build()));
//            // uses
//            components.add(new Pair<>(MagicTreasuresComponents.USES,
//                    new UsesComponent.Builder(material, size).build()));
//            // repairs
//            components.add(new Pair<>(MagicTreasuresComponents.REPAIRS,
//                    new RepairsComponent.Builder(material, size).build()));
//            // mana
//            components.add(new Pair<>(MagicTreasuresComponents.MANA,
//                    new ManaComponent.Builder(material, size, stone).build()));
//            // recharges
//            components.add(new Pair<>(MagicTreasuresComponents.RECHARGES,
//                    new RechargesComponent.Builder(material, stone).build()));
//            // spell factors
//            components.add(new Pair<>(MagicTreasuresComponents.SPELL_FACTORS,
//                    new SpellFactorsComponent.Builder(material, stone).build()));
//            // spells
//            components.add(new Pair<>(MagicTreasuresComponents.SPELLS,
//                    new SpellsComponent((List<Identifier>)null)));

            components.setAttribs(new JewelryAttribsComponent(type.getName(), size.getName(), material.getId(), stone));
            components.setMaxLevel(new MaxLevelComponent.Builder(material, size).build());
            components.setUses(new UsesComponent.Builder(material, size).build());
            components.setRepairs(new RepairsComponent.Builder(material, size).build());
            components.setMana(new ManaComponent.Builder(material, size, stone).build());
            components.setRecharges(new RechargesComponent.Builder(material, stone).build());
            components.setSpellFactors(new SpellFactorsComponent.Builder(material, stone).build());
            components.setSpells(new SpellsComponent((List<Identifier>)null));

            // naming and affixers

            return components;
        }

    }

    public JewelryAttribsComponent getAttribs() {
        return attribs;
    }

    public void setAttribs(JewelryAttribsComponent attribs) {
        this.attribs = attribs;
    }

    public MaxLevelComponent getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(MaxLevelComponent maxLevel) {
        this.maxLevel = maxLevel;
    }

    public UsesComponent getUses() {
        return uses;
    }

    public void setUses(UsesComponent uses) {
        this.uses = uses;
    }

    public RepairsComponent getRepairs() {
        return repairs;
    }

    public void setRepairs(RepairsComponent repairs) {
        this.repairs = repairs;
    }

    public ManaComponent getMana() {
        return mana;
    }

    public void setMana(ManaComponent mana) {
        this.mana = mana;
    }

    public RechargesComponent getRecharges() {
        return recharges;
    }

    public void setRecharges(RechargesComponent recharges) {
        this.recharges = recharges;
    }

    public SpellFactorsComponent getSpellFactors() {
        return spellFactors;
    }

    public void setSpellFactors(SpellFactorsComponent spellFactors) {
        this.spellFactors = spellFactors;
    }

    public SpellsComponent getSpells() {
        return spells;
    }

    public void setSpells(SpellsComponent spells) {
        this.spells = spells;
    }
}
