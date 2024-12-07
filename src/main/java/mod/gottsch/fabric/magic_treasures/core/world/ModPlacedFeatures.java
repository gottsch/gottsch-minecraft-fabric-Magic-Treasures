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
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

/**
 * Created by Mark Gottschling on 12/2/2024
 */
public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TOPAZ_ORE_PLACED_KEY = registerKey("topaz_ore_placed");
    public static final RegistryKey<PlacedFeature> ONYX_ORE_PLACED_KEY = registerKey("onyx_ore_placed");
    public static final RegistryKey<PlacedFeature> JADEITE_ORE_PLACED_KEY = registerKey("jadeite_ore_placed");
    public static final RegistryKey<PlacedFeature> RUBY_ORE_PLACED_KEY = registerKey("ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> SAPPHIRE_ORE_PLACED_KEY = registerKey("sapphire_ore_placed");
    public static final RegistryKey<PlacedFeature> SILVER_ORE_PLACED_KEY = registerKey("silver_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, TOPAZ_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TOPAZ_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(34), YOffset.aboveBottom(94))));

        register(context, ONYX_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ONYX_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(34), YOffset.aboveBottom(94))));

        register(context, JADEITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.JADEITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(24), YOffset.aboveBottom(84))));

        register(context, RUBY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(14), YOffset.aboveBottom(70))));

        register(context, SAPPHIRE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SAPPHIRE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(14), YOffset.aboveBottom(70))));

        register(context, SILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(34), YOffset.aboveBottom(90))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MagicTreasures.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
