package mod.gottsch.fabric.magic_treasures.datagen;

import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.setup.Registration;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Mark Gottschling on Nov 13, 2024
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(MagicTreasuresBlocks.TOPAZ_ORE)
                .add(MagicTreasuresBlocks.ONYX_ORE)
                .add(MagicTreasuresBlocks.JADEITE_ORE)
                .add(MagicTreasuresBlocks.RUBY_ORE)
                .add(MagicTreasuresBlocks.SAPPHIRE_ORE)
                .add(MagicTreasuresBlocks.SILVER_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE);

        // NOTE only Topaz and Onyx can be mined with < iron tools
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(MagicTreasuresBlocks.JADEITE_ORE)
                .add(MagicTreasuresBlocks.RUBY_ORE)
                .add(MagicTreasuresBlocks.SAPPHIRE_ORE)
                .add(MagicTreasuresBlocks.SILVER_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE)
                .add(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE);
    }
}
