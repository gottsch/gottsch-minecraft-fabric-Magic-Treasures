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
package mod.gottsch.fabric.magic_treasures.datagen;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Mark Gottschling on May 4, 2024
 *
 */
public class ModRecipes extends FabricRecipeProvider {
	private static String CRITERIA = "criteria";

	public ModRecipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipe) {
		MagicTreasures.LOGGER.debug("build recipes is running...");

		// recipe scrolls
		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("iron_ring")).findFirst().get())
				.input(MagicTreasuresItems.RING_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.IRON_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.RING_RECIPE), conditionsFromItem(MagicTreasuresItems.RING_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("copper_ring")).findFirst().get())
				.input(MagicTreasuresItems.RING_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.COPPER_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.RING_RECIPE), conditionsFromItem(MagicTreasuresItems.RING_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("silver_ring")).findFirst().get())
				.input(MagicTreasuresItems.RING_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.INGOTS_SILVER), 4)
				.criterion(hasItem(MagicTreasuresItems.RING_RECIPE), conditionsFromItem(MagicTreasuresItems.RING_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("gold_ring")).findFirst().get())
				.input(MagicTreasuresItems.RING_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.GOLD_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.RING_RECIPE), conditionsFromItem(MagicTreasuresItems.RING_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("bone_ring")).findFirst().get())
				.input(MagicTreasuresItems.RING_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.BONES), 4)
				.criterion(hasItem(MagicTreasuresItems.RING_RECIPE), conditionsFromItem(MagicTreasuresItems.RING_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("iron_necklace")).findFirst().get())
				.input(MagicTreasuresItems.NECKLACE_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.IRON_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.NECKLACE_RECIPE), conditionsFromItem(MagicTreasuresItems.NECKLACE_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("copper_necklace")).findFirst().get())
				.input(MagicTreasuresItems.NECKLACE_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.COPPER_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.NECKLACE_RECIPE), conditionsFromItem(MagicTreasuresItems.NECKLACE_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("silver_necklace")).findFirst().get())
				.input(MagicTreasuresItems.NECKLACE_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.INGOTS_SILVER), 4)
				.criterion(hasItem(MagicTreasuresItems.NECKLACE_RECIPE), conditionsFromItem(MagicTreasuresItems.NECKLACE_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("gold_necklace")).findFirst().get())
				.input(MagicTreasuresItems.NECKLACE_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.GOLD_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.NECKLACE_RECIPE), conditionsFromItem(MagicTreasuresItems.NECKLACE_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("bone_necklace")).findFirst().get())
				.input(MagicTreasuresItems.NECKLACE_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.BONES), 4)
				.criterion(hasItem(MagicTreasuresItems.NECKLACE_RECIPE), conditionsFromItem(MagicTreasuresItems.NECKLACE_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("iron_bracelet")).findFirst().get())
				.input(MagicTreasuresItems.BRACELET_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.IRON_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.BRACELET_RECIPE), conditionsFromItem(MagicTreasuresItems.BRACELET_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("copper_bracelet")).findFirst().get())
				.input(MagicTreasuresItems.BRACELET_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.COPPER_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.BRACELET_RECIPE), conditionsFromItem(MagicTreasuresItems.BRACELET_RECIPE))				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("silver_bracelet")).findFirst().get())
				.input(MagicTreasuresItems.BRACELET_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.INGOTS_SILVER), 4)
				.criterion(hasItem(MagicTreasuresItems.BRACELET_RECIPE), conditionsFromItem(MagicTreasuresItems.BRACELET_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("gold_bracelet")).findFirst().get())
				.input(MagicTreasuresItems.BRACELET_RECIPE)
				.input(Ingredient.fromTag(ConventionalItemTags.GOLD_INGOTS), 4)
				.criterion(hasItem(MagicTreasuresItems.BRACELET_RECIPE), conditionsFromItem(MagicTreasuresItems.BRACELET_RECIPE))
				.offerTo(recipe);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MagicTreasuresItems.STANDARD_JEWELRY.stream().filter(o -> ModUtil.getName(o).getPath().equalsIgnoreCase("bone_bracelet")).findFirst().get())
				.input(MagicTreasuresItems.BRACELET_RECIPE)
				.input(Ingredient.fromTag(MagicTreasuresTags.Items.BONES), 4)
				.criterion(hasItem(MagicTreasuresItems.BRACELET_RECIPE), conditionsFromItem(MagicTreasuresItems.BRACELET_RECIPE))
				.offerTo(recipe);

		List<ItemConvertible> SILVER_SMELTABLES = List.of(MagicTreasuresItems.RAW_SILVER);
		offerSmelting(recipe, SILVER_SMELTABLES, RecipeCategory.MISC, MagicTreasuresItems.SILVER_INGOT, 0.25f, 200, "silver_ingot");
		offerBlasting(recipe, SILVER_SMELTABLES, RecipeCategory.MISC, MagicTreasuresItems.SILVER_INGOT, 0.25f, 100, "silver_ingot");

		// TODO Treasure2 doesn't use Tags for key recipes (ruby, sapphire)
		// so need to add the recipes individually

	}
}
