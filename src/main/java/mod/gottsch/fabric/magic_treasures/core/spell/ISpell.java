/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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
package mod.gottsch.fabric.magic_treasures.core.spell;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.spell.cost.ICostEvaluator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Mark Gottschling on 5/3/2023
 */
public interface ISpell {

    // TODO won't be able to use Event.... it'll have to change to something else
    boolean serverUpdate(World level, Random random, ICoords coords, /*Event event,*/ ICastSpellContext context);

//    default public boolean clientUpdate(ItemStack jewelry,SpellUpdateS2C message) {
//        if (jewelry.contains(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)) {
//            JewelryVitalsComponent handler = jewelry.get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT);
//
//            jewelry.set(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT,
//                    new JewelryVitalsComponent(handler.maxLevel(),
//                            handler.maxUses(),
//                            handler.uses(),
//                            handler.maxRepairs(),
//                            handler.repairs(),
//                            handler.maxMana(),
//                            message.getMana(),
//                            handler.maxRecharges(),
//                            handler.recharges()));
//            return true;
//        }
//        return false;
//    }

    @SuppressWarnings("deprecation")
    void addInformation(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType flagIn); //SpellEntity entity);

    Text getSpellDesc();

    Text getSpellDesc(ItemStack jewelry);

    Formatting getSpellLabelColor();

    Formatting getSpellDescColor();

    // final properties
    public Identifier getName();
    public String getType();
    public int getLevel();
    public IRarity getRarity();

    // mutator properties
    double getSpellCost();
    void setSpellCost(double spellCost);

    int getDuration();
    void setDuration(int duration);

    long getFrequency();
    void setFrequency(long frequency);

    double getRange();
    void setRange(double range);

    long getCooldown();
    void setCooldown(long cooldown);

    double getEffectAmount();
    void setEffectAmount(double amount);

    ICostEvaluator getCostEvaluator();
    void setCostEvaluator(ICostEvaluator costEvaluator);

    boolean isExclusive();
    void setExclusive(boolean exclusive);

    public Class<?> getRegisteredEvent();

    boolean isEffectStackable();

    void setEffectStackable(boolean effectStackable);

    int getPriority();

    void setPriority(int priority);
}
