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

import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

/**
 * @author Mark Gottschling on May 16, 2024
 *
 */
public abstract class CooldownSpell extends Spell {

    public CooldownSpell(Builder builder) {
        super(builder);
    }

    @Override
    public SpellResult cast(World world, Random random, ICoords coords, ICastSpellContext context) {
        SpellResult result = new SpellResult();
//        Jewelry item = (Jewelry)context.getJewelry().getItem();
        Long cooldownExpireTime = getCooldownExpireTime(context.getJewelry()).orElse(0L);

        long cooldown = modifyCooldown(context.getJewelry());
        // check if supports cooldown or if world time has exceeded the entity cooldown end time
        if (context.getJewelry().getOrDefault(MagicTreasuresComponents.COOLDOWN, 0L) <= 0.0 || (world.getTime() > cooldownExpireTime)) {
            result = execute(world, random, coords, context);
            if(result.success()) {
                // update cooldown expire time
                updateExpireTime(world, random, context.getJewelry(), context.getSpell(), cooldown);
//                context.getPlayer().getCooldowns().addCooldown(context.getJewelry().getItem(), (int)cooldown);
            }
        }
        return result;
    }

    abstract public SpellResult execute(World world, Random random, ICoords coords, ICastSpellContext context);

    public void updateExpireTime(World level, Random random, ItemStack jewelry, ISpell entity, long cooldown) {
        // set cooldown expire time if cooldown is activated
        if (cooldown > 0.0) {
            jewelry.set(MagicTreasuresComponents.COOLDOWN, (level.getTime() + cooldown));
        }
    }

    public Optional<Long> getCooldownExpireTime(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.COOLDOWN));
    }
}
