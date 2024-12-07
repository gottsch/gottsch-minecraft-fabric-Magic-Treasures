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
package mod.gottsch.fabric.magic_treasures.core.world;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

/**
 * Created by Mark Gottschling on 12/2/2024
 */
public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> TOPAZ_ORE_KEY = registerKey("topaz_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ONYX_ORE_KEY = registerKey("onyx_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> JADEITE_ORE_KEY = registerKey("jadeite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RUBY_ORE_KEY = registerKey("ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAPPHIRE_ORE_KEY = registerKey("sapphire_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_ORE_KEY = registerKey("silver_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldTopazOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.TOPAZ_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldOnyxOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.ONYX_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldJadeiteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.JADEITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldRubyOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.RUBY_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldSapphireOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.SAPPHIRE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldSilverOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, MagicTreasuresBlocks.SILVER_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE.getDefaultState()));


        register(context, TOPAZ_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldTopazOres, 3));
        register(context, ONYX_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldOnyxOres, 3));
        register(context, JADEITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldJadeiteOres, 3));
        register(context, RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldRubyOres, 3));
        register(context, SAPPHIRE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSapphireOres, 3));
        register(context, SILVER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverOres, 4));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MagicTreasures.MOD_ID, name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                  RegistryKey<ConfiguredFeature<?, ?>> key,
                                                                                  F feature,
                                                                                  FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
