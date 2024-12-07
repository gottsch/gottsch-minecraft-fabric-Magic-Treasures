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
package mod.gottsch.fabric.magic_treasures.core.loot;

import com.google.common.collect.Maps;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.loot.function.ImbueRandomly;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomGemstone;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomJewelry;
import mod.gottsch.fabric.magic_treasures.core.loot.function.RandomSpellLoot;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Mark Gottschling on 12/3/2024
 */
public class ModLootTableModifiers {
    private static final String ZOMBIE = "zombie";
    private static final String SKELETON = "skeleton";
    private static final String WITHER_SKELETON = "wither_skeleton";
    private static final String SCARCE = "scarce";
    private static final String RARE = "rare";
    private static final String EPIC = "epic";
    private static final String GENERAL = "general";

    private static final Map<Identifier, String> MAP = Maps.newHashMap();

    // loot table keys
    static RegistryKey<LootTable> zombieLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/zombie"));

    static RegistryKey<LootTable> skeletonLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/skeleton"));

    static RegistryKey<LootTable> witherSkeletonLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/wither_skeleton"));

    static RegistryKey<LootTable> generalEntityLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/general"));

    static RegistryKey<LootTable> scarceEntityLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/scarce"));

    static RegistryKey<LootTable> rareEntityLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/rare"));

    static RegistryKey<LootTable> epicEntityLootTableKey =
            RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MagicTreasures.MOD_ID, "entities/epic"));

    // TODO would be nice if you could register into this list or use tags - entity_type tags instead of item tags
    // TODO look into it
    // TODO if I could get loot table references working, then you could register the loot table as well instead of a String category
    static {
        // NOTE could've map these Lists instead as modifyLootTables() is executed only once for registry purposes.
        MAP.put(Identifier.of("minecraft", "entities/zombie"), ZOMBIE);
        MAP.put(Identifier.of("minecraft", "entities/zombie_villager"), ZOMBIE);
        MAP.put(Identifier.of("minecraft", "entities/drowned"), ZOMBIE);
        MAP.put(Identifier.of("minecraft", "entities/husk"), ZOMBIE);

        MAP.put(Identifier.of("minecraft", "entities/skeleton"), SKELETON);
        MAP.put(Identifier.of("minecraft", "entities/stray"), SKELETON);

        MAP.put(Identifier.of("minecraft", "entities/wither_skeleton"), WITHER_SKELETON);

        // scarce
        MAP.put(Identifier.of("minecraft", "entities/enderman"), SCARCE);
        MAP.put(Identifier.of("minecraft", "entities/vex"), SCARCE);

        // rare
        MAP.put(Identifier.of("minecraft", "entities/blaze"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/ghast"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/guardian"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/piglin_brute"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/shulker"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/vindicator"), RARE);
        MAP.put(Identifier.of("minecraft", "entities/zoglin"), RARE);

        // epic
        MAP.put(Identifier.of("minecraft", "entities/elder_guardian"), EPIC);
        MAP.put(Identifier.of("minecraft", "entities/ender_dragon"), EPIC);
        MAP.put(Identifier.of("minecraft", "entities/ravager"), EPIC);
        MAP.put(Identifier.of("minecraft", "entities/warden"), EPIC);
        MAP.put(Identifier.of("minecraft", "entities/elder_guardian"), EPIC);
        MAP.put(Identifier.of("minecraft", "entities/wither"), EPIC);

        MAP.put(Identifier.of("minecraft", "entities/cave_spider"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/creeper"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/evoker"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/illusioner"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/magma_cube"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/phantom"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/piglin"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/pillager"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/slime"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/spider"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/cave_spider"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/witch"), GENERAL);
        MAP.put(Identifier.of("minecraft", "entities/zombified_piglin"), GENERAL);

    }

    // THIS SUCKS
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            // test for matching loot table
            String group = MAP.getOrDefault(registryKey.getValue(), "none");

            switch(group) {
                case ZOMBIE -> {
                    // determine if specific loot modifier is enabled
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableZombieEntityLootModifier()) {
                        zombieLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case SKELETON -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableSkeletonEntityLootModifier()) {
                        skeletonLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case WITHER_SKELETON -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableWitherSkeletonEntityLootModifier()) {
                        witherSkeletonLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case SCARCE -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableScarceEntityLootModifier()) {
                        scarceEntityLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case RARE -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableRareEntityLootModifier()) {
                        rareEntityLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case EPIC -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableEpicEntityLootModifier()) {
                        epicEntityLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                case GENERAL -> {
                    if (MagicTreasures.CONFIG.loot.enableVanillaLootModifiers()
                            && MagicTreasures.CONFIG.loot.enableGeneralEntityLootModifier()) {
                        generalEntityLootTable(registryKey, builder, lootTableSource, wrapperLookup);
                    }
                }
                default -> {
                    // nothing for non-listed
                }
            }
        });
    }

    private static void zombieLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstoneLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(MagicTreasuresItems.TOPAZ, MagicTreasuresItems.ONYX)))
//                        .build())
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:wood_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelryLootFunction.builder(null, Arrays.asList("common"), null, null, null))
//                        .build())
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:harm_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f), null, null))
//                        .build());

                // TODO with this line, should be able to pass in a custom loot_table
                 // then only need to wrap this builder with the config check and chance check
                .with(LootTableEntry.builder(zombieLootTableKey).build());

//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.025f)) // 1F for 100%, 0.025F
//                .build());
        builder.pool(poolBuilder).build();
    }

    private static void skeletonLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(MagicTreasuresItems.TOPAZ, MagicTreasuresItems.ONYX)))
//                        .build())
//
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:bone_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("common", "uncommon"), null, null, null))
//                        .build())
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:harm_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(1.0f, 4.0f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.05f)) // 1F for 100%, 0.05F
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(skeletonLootTableKey).build());

        builder.pool(poolBuilder).build();
    }

    private static void witherSkeletonLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null, null))
//                        .build())
//
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:bone_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("scarce", "rare"), null, null, null))
//                        .build())
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:drain_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(3.0f, 7.0f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.4f)) // 1F for 100%, 0.05F
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(witherSkeletonLootTableKey).build());

        builder.pool(poolBuilder).build();
    }

    private static void generalEntityLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(MagicTreasuresItems.TOPAZ, MagicTreasuresItems.ONYX, Items.DIAMOND, MagicTreasuresItems.JADEITE)))
//                        .build())
//
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:copper_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("common", "uncommon", "scarce"), null, null, null))
//                        .build())
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:drain_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(2.0f, 5.0f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.1f)) // 1F for 100%, 0.05F
//                .build());
//
//        // recipe pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RING_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.NECKLACE_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.BRACELET_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.05f)) // 1F for 100%
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(generalEntityLootTableKey).build());

        builder.pool(poolBuilder).build();
    }

    private static void scarceEntityLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:silver_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("common",
//                                "uncommon",
//                                "scarce"), null, null, null))
//                        .apply(ImbueRandomly.builder(UniformLootNumberProvider.create(2f, 6f), null, null))                                .build());
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.1f)) // 1F for 100%, 0.05F
//                .build());
//
//        // new pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:copper_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("common",
//                                "uncommon",
//                                "scarce"), null, null, null))
//                        .build())
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(Items.DIAMOND, MagicTreasuresItems.JADEITE, MagicTreasuresItems.RUBY)))
//                        .build())
//
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:drain_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(2.0f, 5.0f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.15f)) // 1F for 100%
//                .build());
//
//        // recipe pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RING_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.NECKLACE_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.BRACELET_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.05f)) // 1F for 100%
//                .build());
//
//        // recharge pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RECHARGE_SCROLL)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.2f)) // 1F for 100%
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(scarceEntityLootTableKey).build());

        builder.pool(poolBuilder).build();
    }

    private static void rareEntityLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:gold_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("scarce", "rare"), null, null, null))
//                        .apply(ImbueRandomly.builder(UniformLootNumberProvider.create(3f, 7f), null, null))
//                        .build());
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.2f))
//                .build());
//
//        // new pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:silver_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList(
//                                "uncommon",
//                                "scarce",
//                                "rare"), null, null, null))
//                        .build())
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(Items.DIAMOND, MagicTreasuresItems.JADEITE, MagicTreasuresItems.RUBY, MagicTreasuresItems.WHITE_PEARL)))
//                        .build())
//
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:drain_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(5.0f, 8f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.2f))
//                .build());
//
//        // recipe pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RING_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.NECKLACE_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.BRACELET_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.12f)) // 1F for 100%
//                .build());
//
//        // recharge pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RECHARGE_SCROLL)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.5f)) // 1F for 100%
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(rareEntityLootTableKey).build());

        builder.pool(poolBuilder).build();
    }

    // TODO update
    private static void epicEntityLootTable(RegistryKey<LootTable> registryKey, LootTable.Builder builder, LootTableSource lootTableSource, RegistryWrapper.WrapperLookup wrapperLookup) {
//        LootPool.Builder poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:gold_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList("epic", "legendary", "mythical"), null, null, null))
//                        .apply(ImbueRandomly.builder(UniformLootNumberProvider.create(7f, 10f), null, null))
//                        .build());
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.8f))
//                  .build());
//
//        // new pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                // random jewelry
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:gold_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList(
//                                "rare",
//                                "epic"), null, null, null))
//                        .apply(ImbueRandomly.builder(UniformLootNumberProvider.create(6f, 8f), null, null))
//                        .build())
//                // random jewelry 2
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:gold_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList(
//                                "legendary",
//                                "mythical"), null, null, null))
//                         .build())
//                // random jewelry 3
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:gold_ring")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomJewelry.builder(null, Arrays.asList(
//                                "rare",
//                                "epic"), null, null, null))
//                        .build())
//                // random gemstone
//                .with(ItemEntry.builder(MagicTreasuresItems.TOPAZ)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomGemstone.builder(UniformLootNumberProvider.create(1.0f, 1.0f), null, null,
//                                ModUtil.getNames(MagicTreasuresItems.RUBY, MagicTreasuresItems.WHITE_PEARL,
//                                        MagicTreasuresItems.SAPPHIRE, MagicTreasuresItems.BLACK_PEARL)))
//                        .build())
//
//                // random spell
//                .with(ItemEntry.builder(Registries.ITEM.get(Identifier.of("magictreasures:drain_scroll")))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .apply(RandomSpellLoot.builder(UniformLootNumberProvider.create(6.0f, 10f), null, null))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.6f))
//                .build());
//
//        // recipe pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RING_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.NECKLACE_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build())
//                .with(ItemEntry.builder(MagicTreasuresItems.BRACELET_RECIPE)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.75f)) // 1F for 100%
//                .build());
//
//        // recharge pool
//        poolBuilder = LootPool.builder()
//                .rolls(ConstantLootNumberProvider.create(1))
//                .with(ItemEntry.builder(MagicTreasuresItems.RECHARGE_SCROLL)
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
//                        .build());
//
//        builder.pool(poolBuilder.conditionally(RandomChanceLootCondition.builder(0.75f)) // 1F for 100%
//                .build());
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(LootTableEntry.builder(epicEntityLootTableKey).build());

        builder.pool(poolBuilder).build();
    }
}
