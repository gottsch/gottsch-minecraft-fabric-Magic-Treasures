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
package mod.gottsch.fabric.magic_treasures.core.loot.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.loot.ModLootFunctions;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Mark Gottschling on 12/4/2024
 */
public class RandomSpellLoot extends ConditionalLootFunction {

    public static final MapCodec<RandomSpellLoot> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addConditionsField(instance)
                    .<LootNumberProvider, String, List<String>>and(
                            instance.group(
                                    LootNumberProviderTypes.CODEC.optionalFieldOf("levels", UniformLootNumberProvider.create(1.0f, 1.0f)).forGetter(function -> function.levels),
                                    Codec.STRING.optionalFieldOf("rarity", "").forGetter(function -> function.rarity),
                                    Codec.STRING.listOf().optionalFieldOf("rarities", new ArrayList<>()).forGetter(function -> function.rarities)
                            )
                    ).apply(instance, RandomSpellLoot::new)
            );

    private final LootNumberProvider levels;
    private final String rarity;
    private final List<String> rarities;

    private RandomSpellLoot(List<LootCondition> conditions, LootNumberProvider levels,
                            String rarity, List<String> rarities) {
        super(conditions);
        this.levels = levels == null ? UniformLootNumberProvider.create(1.0f, 1.0f) : levels;
        this.rarity = rarity == null ? "" : rarity;
        this.rarities = rarities == null ? new ArrayList<>() : rarities;
    }

    @Override
    public LootFunctionType<RandomSpellLoot> getType() {
        return ModLootFunctions.RANDOM_SPELL_LOOT_FUNCTION_TYPE;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        Random rand = new Random();
        MagicTreasures.LOGGER.debug("incoming stack -> {}", stack.getName().getString());
        List<ISpell> spells = new ArrayList<>();
        ISpell spell;

        MagicTreasures.LOGGER.debug("rarity -> {}", rarity);
        spell = MagicTreasuresSpells.DEFAULT_HEALING;

        if (!rarity.isEmpty()) {
            IRarity r = MagicTreasuresApi.getRarity(rarity).orElse(MagicTreasuresRarity.NONE);
            // get all the spells by rarity
            spells.addAll(SpellRegistry.get(r));
        }
        else if (rarities != null && !rarities.isEmpty()) {
            rarities.forEach(r -> {
                IRarity rarity = MagicTreasuresApi.getRarity(r).orElse(MagicTreasuresRarity.NONE);
                spells.addAll(SpellRegistry.get(rarity));
            });
        } else {
            // select random level
            int level = this.levels == null ? 1 : this.levels.nextInt(context);
            MagicTreasures.LOGGER.debug("selected level -> {}", level);

            // select a spell by level
            spells.addAll(SpellRegistry.get(level));
        }

        if (!spells.isEmpty()) {
            spell = spells.get(new Random().nextInt(spells.size()));
        } else {
            return stack;
        }
        MagicTreasures.LOGGER.debug("selected spell -> {}", spell.getName());

        Identifier scrollName = ModUtil.asLocation(spell.getName() + "_scroll");
        Optional<Item> spellScroll = MagicTreasuresItems.ALL_SPELL_SCROLLS.stream()
                .filter(scroll -> scroll.getRegistryEntry().registryKey().getValue().equals(scrollName))
                .findFirst();

        return spellScroll.map(ItemStack::new).orElse(stack);
    }

    private Optional<Item> selectGemstone(List<Item> gemstones) {
        if (!gemstones.isEmpty()) {
            return Optional.ofNullable(gemstones.get(new Random().nextInt(gemstones.size())));
        }
        return Optional.empty();
    }

    public static Builder<?> builder(LootNumberProvider levels,
                                                             String rarity, List<String> rarities) {
        return builder(list -> new RandomSpellLoot(list, levels, rarity == null ? "" : rarity, rarities));
    }
}

