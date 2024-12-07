package mod.gottsch.fabric.magic_treasures.datagen;

import com.google.gson.JsonElement;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.setup.Registration;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Created by Mark Gottschling on Nov 13, 2024
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.TOPAZ_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.ONYX_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.JADEITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.SAPPHIRE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.SILVER_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Model JEWELRY_PARENT = withParent(modLoc("item/jewelry"), TextureKey.LAYER0);

        // tabs
        singleTexture(MagicTreasuresItems.MAGIC_TREASURES_TAB,
                Models.GENERATED, modLoc("item/" + name(MagicTreasuresItems.MAGIC_TREASURES_TAB)), itemModelGenerator.writer);

        // metals
        singleTexture(MagicTreasuresItems.SILVER_INGOT, Models.GENERATED, modLoc("item/" + name(MagicTreasuresItems.SILVER_INGOT)), itemModelGenerator.writer);
        singleTexture(MagicTreasuresItems.RAW_SILVER, Models.GENERATED, modLoc("item/" + name(MagicTreasuresItems.RAW_SILVER)), itemModelGenerator.writer);

        // gemstones
        gemstone(MagicTreasuresItems.TOPAZ, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.ONYX, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.JADEITE, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.RUBY, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.SAPPHIRE, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.WHITE_PEARL, itemModelGenerator.writer);
        gemstone(MagicTreasuresItems.BLACK_PEARL, itemModelGenerator.writer);

        // jewelry
        MagicTreasuresItems.STANDARD_JEWELRY.forEach(item -> {
            singleTexture(item, JEWELRY_PARENT,
                    modLoc("item/jewelry/" + name(item)), itemModelGenerator.writer);
        });
        MagicTreasuresItems.CUSTOM_JEWELRY.forEach(item -> {
            if (name(item).contains("castle") || name(item).contains("hawk")) {
                singleTexture(item, JEWELRY_PARENT,
                        modLoc("item/jewelry/" + name(item)), itemModelGenerator.writer);
            }
        });

        // special / non-standard jewelry
        singleTexture(MagicTreasuresItems.SILBROS_RING_OF_VITALITY, JEWELRY_PARENT,
                modLoc("item/jewelry/silbros_ring_of_vitality"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.STRONGMANS_BRACERS, JEWELRY_PARENT,
                modLoc("item/jewelry/wood_bracelet"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.PEASANTS_FORTUNE, JEWELRY_PARENT,
                                modLoc("item/jewelry/great_iron_ring"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.AMULET_OF_DEFENCE, JEWELRY_PARENT,
                modLoc("item/jewelry/copper_topaz_necklace"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET, JEWELRY_PARENT,
                modLoc("item/jewelry/bone_onyx_necklace"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.AQUA_RING, JEWELRY_PARENT,
                modLoc("item/jewelry/silver_topaz_ring"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.JOURNEYMANS_BANDS, JEWELRY_PARENT,
                modLoc("item/jewelry/great_gold_jadeite_bracelet"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.MEDICS_TOKEN, JEWELRY_PARENT,
                modLoc("item/jewelry/medics_token"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.ADEPHAGIAS_BOUNTY, JEWELRY_PARENT,
                modLoc("item/jewelry/adephagias_bounty"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.SALANDAARS_WARD, JEWELRY_PARENT,
                modLoc("item/jewelry/salandaars_ward"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.ANGELS_RING, JEWELRY_PARENT,
                modLoc("item/jewelry/angels_ring"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.RING_OF_FORTITUDE, JEWELRY_PARENT,
                modLoc("item/jewelry/ring_of_fortitude"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.RING_LIFE_DEATH, JEWELRY_PARENT,
                modLoc("item/jewelry/ring_of_life_death"), itemModelGenerator.writer);

        singleTexture(MagicTreasuresItems.EYE_OF_THE_PHOENIX, JEWELRY_PARENT,
                modLoc("item/jewelry/eye_of_the_phoenix"), itemModelGenerator.writer);

        // scrolls
        SpellRegistry.values().forEach(spell -> {
            singleTexture(modLoc(spell.getName().getPath() + "_scroll")
                            .withPrefixedPath("item/"),
                    Models.GENERATED, modLoc(
                            switch(spell.getLevel()) {
                                case 1,2 -> "item/yellow_spell_scroll";
                                case 3,4 -> "item/green_spell_scroll";
                                case 5,6 -> "item/blue_spell_scroll";
                                case 7,8 -> "item/red_spell_scroll";
                                default -> "item/black_spell_scroll";
                            }), itemModelGenerator.writer);
        });

        singleTexture(MagicTreasuresItems.RECHARGE_SCROLL, Models.GENERATED,
                modLoc("item/recharge_scroll"), itemModelGenerator.writer);

        // recipes
        singleTexture(MagicTreasuresItems.RING_RECIPE, Models.GENERATED, modLoc("item/ring_recipe_scroll"), itemModelGenerator.writer);
        singleTexture(MagicTreasuresItems.NECKLACE_RECIPE, Models.GENERATED, modLoc("item/necklace_recipe_scroll"), itemModelGenerator.writer);
        singleTexture(MagicTreasuresItems.BRACELET_RECIPE, Models.GENERATED, modLoc("item/bracelet_recipe_scroll"), itemModelGenerator.writer);

    }

    public void gemstone(Item item, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        try {
            singleTexture(item, Models.GENERATED, modLoc("item/gem/" + name(item)), writer);
        } catch(Exception e) {
            MagicTreasures.LOGGER.error("error", e);
        }
    }

    public void singleTexture(Item item, Model model, Identifier path, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        model.upload(ModelIds.getItemModelId(item), TextureMap.layer0(path), writer);
    }

    public void singleTexture(Identifier itemIdentifier, Model model, Identifier path, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        model.upload(itemIdentifier, TextureMap.layer0(path), writer);
    }

    private static Model withParent(Identifier parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(parent), Optional.empty(), requiredTextureKeys);
    }

    public final Identifier modLoc(String path) {
        return Identifier.of(MagicTreasures.MOD_ID, path);
    }
    public final Identifier mcLoc(String path) {
        return Identifier.ofVanilla(path);
    }

    public final String name(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }
}
