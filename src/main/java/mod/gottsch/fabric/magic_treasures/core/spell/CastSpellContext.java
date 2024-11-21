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
package mod.gottsch.fabric.magic_treasures.core.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Gottschling on 5/17/2024
 */
public class CastSpellContext implements ICastSpellContext {
    private ItemStack jewelry;
    private ISpell spell;
    private List<ItemStack> manaWells;
    private PlayerEntity player;
    private double amount;

    public CastSpellContext(ItemStack jewelry, List<ItemStack> manaWells, ISpell spell, PlayerEntity player) {
        this.jewelry = jewelry;
        this.manaWells = manaWells;
        this.spell = spell;
        this.player = player;
    }

    @Override
    public ItemStack getJewelry() {
        return jewelry;
    }

    public void setJewelry(ItemStack jewelry) {
        this.jewelry = jewelry;
    }

    @Override
    public ISpell getSpell() {
        return spell;
    }

    public void setSpell(ISpell spell) {
        this.spell = spell;
    }

    @Override
    public List<ItemStack> getManaWells() {
        if (manaWells == null) {
            this.manaWells = new ArrayList<>();
        }
        return manaWells;
    }

    public void setManaWells(List<ItemStack> manaWells) {
        this.manaWells = manaWells;
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
