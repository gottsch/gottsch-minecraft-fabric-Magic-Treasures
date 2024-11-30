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
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
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
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;
        public Identifier gemstone;

        public JewelryAttribsComponent attribsComponent;
        public MaxLevelComponent maxLevelComponent;
        public UsesComponent usesComponent;
        public RepairsComponent repairsComponent;
        public ManaComponent manaComponent;
        public ManaComponent.Builder manaComponentBuilder;
        public RechargesComponent rechargesComponent;
        public SpellFactorsComponent spellFactorsComponent;
        public SpellsComponent spellsComponent;

        public boolean infinite;

        public Builder(IJewelryType type, JewelryMaterial material) {
            this.type = type;
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
            this.gemstone = null;
        }

        public Builder(IJewelryType type, JewelryMaterial material, IJewelrySizeTier sizeTier) {
            this.type = type;
            this.material = material;
            this.sizeTier = sizeTier;
            this.gemstone = null;
        }

        public Builder clear() {
            type = null;
            sizeTier = null;
            material = null;
            gemstone = null;
            return this;
        }

        public Builder setInfinite() {
            this.infinite = true;
            return this;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public JewelryComponents build() {
            JewelryComponents components = new JewelryComponents();

            if (type == null) return components;
            if (sizeTier == null) sizeTier = JewelrySizeTier.REGULAR;
            if (material == null) material = JewelryMaterials.SILVER;
            if (gemstone == null) {
                gemstone = ModUtil.getName(Items.AIR);
            }

            /*
             *build the components
             */
            // attribs
            if (attribsComponent == null)
                components.setAttribs(new JewelryAttribsComponent(type.getName(), sizeTier.getName(), material.getId(), gemstone));
            else
                components.setAttribs(attribsComponent);
            // max levels
            if (maxLevelComponent == null)
                components.setMaxLevel(new MaxLevelComponent.Builder(material, sizeTier).build());
            else
                components.setMaxLevel(maxLevelComponent);
            // uses
            if (this.infinite)
                components.setUses(new UsesComponent(Integer.MAX_VALUE));
            else if (usesComponent == null)
                components.setUses(new UsesComponent.Builder(material, sizeTier).build());
            else
                components.setUses(usesComponent);
            // repairs
            if (repairsComponent == null)
                components.setRepairs(new RepairsComponent.Builder(material, sizeTier).build());
            else
                components.setRepairs(repairsComponent);
            // mana
            if (manaComponentBuilder != null)
                components.setMana(manaComponentBuilder.build());
            else if (manaComponent == null)
                components.setMana(new ManaComponent.Builder(material, sizeTier, gemstone).build());
            else
                components.setMana(manaComponent);
            // recharges
            if (rechargesComponent == null)
                components.setRecharges(new RechargesComponent.Builder(material, gemstone).build());
            else
                components.setRecharges(rechargesComponent);
            // spell factors
            if (spellFactorsComponent == null)
                components.setSpellFactors(new SpellFactorsComponent.Builder(material, gemstone).build());
            else
                components.setSpellFactors(spellFactorsComponent);
            // spells
            if (spellsComponent == null)
                components.setSpells(new SpellsComponent((List<Identifier>)null));
            else
                components.setSpells(spellsComponent);

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
