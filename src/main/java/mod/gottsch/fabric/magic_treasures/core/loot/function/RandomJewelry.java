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
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.MaxLevelComponent;
import mod.gottsch.fabric.magic_treasures.core.loot.ModLootFunctions;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryMaterialRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
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
public class RandomJewelry extends ConditionalLootFunction {

    public static final MapCodec<RandomJewelry> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addConditionsField(instance)
                    .<String, List<String>, List<Identifier>, List<Identifier>, LootNumberProvider>and(
                            instance.group(
                                    Codec.STRING.optionalFieldOf("rarity", "").forGetter(function -> function.rarity),
                                    Codec.STRING.listOf().optionalFieldOf("rarities", new ArrayList<>()).forGetter(function -> function.rarities),
                                    Identifier.CODEC.listOf().optionalFieldOf("gemstones", new ArrayList<>()).forGetter(function -> function.gemstones),
                                    Identifier.CODEC.listOf().optionalFieldOf("materials", new ArrayList<>()).forGetter(function -> function.materials),
                                    LootNumberProviderTypes.CODEC.optionalFieldOf("level", UniformLootNumberProvider.create(1.0f, 1.0f)).forGetter(function -> function.levels)
                                    )
                    ).apply(instance, RandomJewelry::new)
            );

    private final String rarity;
    private final List<String> rarities;
    private final List<Identifier> gemstones;
    private final List<Identifier> materials;
    private final LootNumberProvider levels;

    private RandomJewelry(List<LootCondition> conditions,
                          String rarity, List<String> rarities,
                          List<Identifier> gemstones, List<Identifier> materials,
                          LootNumberProvider levels) {
        super(conditions);
        this.rarity = rarity == null ? "" : rarity;
        this.rarities = rarities == null ? new ArrayList<>() : rarities;
        this.gemstones = gemstones == null ? new ArrayList<>() : gemstones;
        this.materials = materials == null ? new ArrayList<>() : materials;
        this.levels = levels == null ? UniformLootNumberProvider.create(1.0f, 1.0f) : levels;
    }

    @Override
    public LootFunctionType<RandomJewelry> getType() {
        return ModLootFunctions.RANDOM_JEWELRY_LOOT_FUNCTION_TYPE;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        Random random = new Random();
        Optional<Item> gemstone = Optional.empty();

        MagicTreasures.LOGGER.debug("incoming stack -> {}", stack.getName().getString());
        MagicTreasures.LOGGER.debug("rarity -> {}", rarity);

        IRarity rarityEnum = rarity.isEmpty() ? MagicTreasuresRarity.NONE
                : MagicTreasuresApi.getRarity(rarity).orElse(MagicTreasuresRarity.NONE);
        List<Item> tempGemstones = new ArrayList<>();
        List<Item> jewelry = new ArrayList<>();
        if (!materials.isEmpty()) {
            List<Item> finalJewelry = jewelry;
            for(Identifier material : materials) {
                JewelryMaterialRegistry.get(material)
                        .map(JewelryRegistry::get).ifPresent(jewelry::addAll);
            }
            // filter by rarity/rarities
            if (rarityEnum != MagicTreasuresRarity.NONE) {
                jewelry = filterByRarity(jewelry, rarityEnum);
                jewelry = filterByGemstones(jewelry, gemstones);
                // NOTE do not filter by level as rarity supersedes it

            } else if (!rarities.isEmpty()) {
                List<Item> filteredJewelry = new ArrayList<>();
                for(String s : rarities) {
                    IRarity r = MagicTreasuresApi.getRarity(s).orElse(MagicTreasuresRarity.NONE);
                    filteredJewelry.addAll(filterByRarity(jewelry, r));
                }
                jewelry = filterByGemstones(filteredJewelry, gemstones);

            } else if (levels != null) {
                jewelry = filterByLevel(jewelry, levels.nextInt(context));
                jewelry = filterByGemstones(jewelry, gemstones);
            }
        } else if (rarityEnum != MagicTreasuresRarity.NONE) {
            // get by rarity
            jewelry.addAll(JewelryRegistry.get(rarityEnum));
            jewelry = filterByGemstones(jewelry, gemstones);
        } else if (rarities != null && !rarities.isEmpty()) {
            MagicTreasures.LOGGER.debug("adding jewelry by rarities");
            for (String r : rarities) {
                jewelry.addAll(JewelryRegistry.get(MagicTreasuresApi.getRarity(r).orElse(MagicTreasuresRarity.NONE)));
                MagicTreasures.LOGGER.debug("jewelry list ->{}", jewelry);
            }
            jewelry = filterByGemstones(jewelry, gemstones);
            MagicTreasures.LOGGER.debug("filtered jewelry list ->{}", jewelry);
        } else if (levels != null) {
            jewelry = filterByLevel(jewelry, levels.nextInt(context));
            jewelry = filterByGemstones(jewelry, gemstones);
        }

        return jewelry.isEmpty() ? stack : new ItemStack(jewelry.get(random.nextInt(jewelry.size())));
    }

    public List<Item> filterByRarity(List<Item> jewelry, IRarity rarity) {
        return jewelry.stream()
                .filter(j -> {
                    return JewelryRegistry.get(rarity).contains(j);
                }).toList();
    }

    public List<Item> filterByLevel(List<Item> jewelry, int level) {
        return jewelry.stream()
                .filter(j -> {
                    ItemStack jewelryStack = new ItemStack(j);
                    return ComponentHelper.maxLevel(jewelryStack).map(MaxLevelComponent::maxLevel).orElse(0) >= level;
                }).toList();
    }

    public List<Item> filterByGemstones(List<Item> jewelry, List<Identifier> gemstones) {
        if (!gemstones.isEmpty()) {
            // filter by stones
            return jewelry.stream()
                    .filter(j -> {
                        ItemStack jewelryStack = new ItemStack(j);
                        return ComponentHelper.gemstone(jewelryStack).map(gemstones::contains).orElse(false);
                    }).toList();
        }
        return jewelry;
    }

    private Optional<Item> selectGemstone(List<Item> gemstones) {
        if (!gemstones.isEmpty()) {
            return Optional.ofNullable(gemstones.get(new Random().nextInt(gemstones.size())));
        }
        return Optional.empty();
    }

    public static Builder<?> builder(String rarity, List<String> rarities, List<Identifier> gemstones,
                                     List<Identifier> materials, LootNumberProvider levels) {
        // TODO either remove here or in the constructor
        return builder(list -> new RandomJewelry(list,
                rarity == null ? "" : rarity,
                rarities == null ? new ArrayList<>() : rarities,
                gemstones == null ? new ArrayList<>() : gemstones,
                materials == null ? new ArrayList<>() : materials,
                levels));
    }
}

