package mod.gottsch.fabric.magic_treasures.datagen;

import com.google.gson.JsonElement;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.setup.Registration;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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
        // tabs
        singleTexture(MagicTreasuresItems.MAGIC_TREASURES_TAB,
                Models.GENERATED, modLoc("item/" + name(MagicTreasuresItems.MAGIC_TREASURES_TAB)), itemModelGenerator.writer);

        gem(MagicTreasuresItems.TOPAZ, itemModelGenerator.writer);
        gem(MagicTreasuresItems.ONYX, itemModelGenerator.writer);
        gem(MagicTreasuresItems.JADEITE, itemModelGenerator.writer);
        gem(MagicTreasuresItems.RUBY, itemModelGenerator.writer);
        gem(MagicTreasuresItems.SAPPHIRE, itemModelGenerator.writer);
        gem(MagicTreasuresItems.WHITE_PEARL, itemModelGenerator.writer);
        gem(MagicTreasuresItems.BLACK_PEARL, itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.TOPAZ, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.TOPAZ)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.ONYX, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.ONYX)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.JADEITE, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.JADEITE)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.RUBY, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.RUBY)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.SAPPHIRE, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.SAPPHIRE)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.WHITE_PEARL, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.WHITE_PEARL)), itemModelGenerator.writer);
//        singleTexture(MagicTreasuresItems.BLACK_PEARL, Models.GENERATED, modLoc("item/gem/" + name(MagicTreasuresItems.BLACK_PEARL)), itemModelGenerator.writer);
        singleTexture(MagicTreasuresItems.RAW_SILVER, Models.GENERATED, modLoc("item/" + name(MagicTreasuresItems.RAW_SILVER)), itemModelGenerator.writer);

        // jewelry
        singleTexture(MagicTreasuresItems.GOLD_RING, Models.GENERATED, modLoc("item/jewelry/" + name(MagicTreasuresItems.GOLD_RING)), itemModelGenerator.writer);

    }

    // TODO this method cause errors which in turn nothing generates - why?!
    // TODO now it doesn't cause error - bizarre
    public void gem(Item item, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        try {
            singleTexture(item, Models.GENERATED, modLoc("item/gem/" + name(item)), writer);
        } catch(Exception e) {
            MagicTreasures.LOGGER.error("error", e);
        }
    }

    public void singleTexture(Item item, Model model, Identifier path, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        model.upload(ModelIds.getItemModelId(item), TextureMap.layer0(path), writer);
    }

    public final Identifier modLoc(String path) {
        return Identifier.of(MagicTreasures.MOD_ID, path);
    }

    public final String name(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }
}
