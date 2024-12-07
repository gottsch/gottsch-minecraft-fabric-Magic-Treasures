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
import mod.gottsch.fabric.magic_treasures.core.loot.ModLootFunctions;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.registry.GemstoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
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
public class RandomGemstone extends ConditionalLootFunction {

    public static final MapCodec<RandomGemstone> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addConditionsField(instance)
                    .<LootNumberProvider, String, List<String>, List<Identifier>>and(
                            instance.group(
                                    LootNumberProviderTypes.CODEC.optionalFieldOf("count", UniformLootNumberProvider.create(1.0f, 1.0f)).forGetter(function -> function.count),
                                    Codec.STRING.optionalFieldOf("rarity", "").forGetter(function -> function.rarity),
//                                    Codec.STRING.<IRarity>comapFlatMap(RandomGemstoneLootFunction::parseRarity, IRarity::getName),
                                    Codec.STRING.listOf().optionalFieldOf("rarities", new ArrayList<>()).forGetter(function -> function.rarities),
                                    Identifier.CODEC.listOf().optionalFieldOf("gemstones", new ArrayList<>()).forGetter(function -> function.gemstones)
                            )
                    ).apply(instance, RandomGemstone::new)
            );

    private final LootNumberProvider count;
    private final String rarity;
    private final List<String> rarities;
    private final List<Identifier> gemstones;

    private RandomGemstone(List<LootCondition> conditions, LootNumberProvider count,
                           String rarity, List<String> rarities, List<Identifier> gemstones) {
        super(conditions);
        this.count = count == null ? UniformLootNumberProvider.create(1.0f, 1.0f) : count;
        this.rarity = rarity == null ? "" : rarity;
        this.rarities = rarities == null ? new ArrayList<>() : rarities;
        this.gemstones = gemstones == null ? new ArrayList<>() : gemstones;
    }


//    public static DataResult<IRarity> parseRarity(String rarity) {
//        try {
//            return DataResult.success(MagicTreasuresApi.getRarity(rarity).orElse(MagicTreasuresRarity.NONE));
//        } catch (Exception e) {
//            return DataResult.success(MagicTreasuresRarity.NONE);
//        }
//    }

    @Override
    public LootFunctionType<RandomGemstone> getType() {
        return ModLootFunctions.RANDOM_GEMSTONE_LOOT_FUNCTION_TYPE;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        Random random = new Random();
        Optional<Item> gemstone = Optional.empty();

        MagicTreasures.LOGGER.debug("random gemstone incoming stack -> {}", stack.getName().getString());
        MagicTreasures.LOGGER.debug("rarity -> {}", rarity);

        List<Item> tempGemstones = new ArrayList<>();

        // TODO probably want to validate all properties first and then
        // attempt to process them. ie if rarity is some invalid value
        // we don't want to process at all (and not use NONE) and move to the
        // next properties ex rarities.

        if (!rarity.isEmpty()) {
            IRarity r = MagicTreasuresApi.getRarity(rarity).orElse(MagicTreasuresRarity.NONE);

            // get all the gemstones by rarity
            // TODO filter by tag
            tempGemstones.addAll(GemstoneRegistry.get(r));
            gemstone = selectGemstone(tempGemstones);
        } else if (rarities != null && !rarities.isEmpty()) {
            rarities.forEach(r -> {
                IRarity rarity = MagicTreasuresApi.getRarity(r).orElse(MagicTreasuresRarity.NONE);
                // get the IRarity from the string
                // TODO filter by Tag
                tempGemstones.addAll(GemstoneRegistry.get(rarity));
            });
            // select one gemstone from the list
            gemstone = selectGemstone(tempGemstones);
        } else if (gemstones != null && !gemstones.isEmpty()) {
            // TODO first filter list against Tag, then select
            // grab one of the listed gemstones and see if it is in a GEMSTONES tag
            Identifier gemstoneId = gemstones.get(random.nextInt(gemstones.size()));
            Optional<Item> optionalGemstone = GemstoneRegistry.get(gemstoneId);
            if (optionalGemstone.isPresent() && optionalGemstone.get().getRegistryEntry().isIn(MagicTreasuresTags.Items.STONES)) {
                gemstone = optionalGemstone;
            }
        } else {
            // select a random gemstone from all stones
            // filtering out any gemstones that aren't in the Tag
            gemstone = selectGemstone(GemstoneRegistry.getStones()
                    .stream()
                    .filter(g -> g.getRegistryEntry().isIn(MagicTreasuresTags.Items.STONES))
                    .toList());
        }

        // select random count
        int count = this.count == null ? 1 : this.count.nextInt(context);
        MagicTreasures.LOGGER.debug("selected count -> {}", count);
        gemstone.ifPresent(item -> MagicTreasures.LOGGER.debug("selected gemstone -> {}", item.getName().getString()));
        return gemstone.map(s -> new ItemStack(s, count)).orElse(stack);
    }

    private Optional<Item> selectGemstone(List<Item> gemstones) {
        if (!gemstones.isEmpty()) {
            return Optional.ofNullable(gemstones.get(new Random().nextInt(gemstones.size())));
        }
        return Optional.empty();
    }

    public static ConditionalLootFunction.Builder<?> builder(LootNumberProvider count,
                                                             String rarity, List<String> rarities, List<Identifier> gemstones) {
        return builder(list -> new RandomGemstone(list, count, rarity == null ? "" : rarity, rarities, gemstones));
    }
}

