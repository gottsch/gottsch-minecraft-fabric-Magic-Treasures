package mod.gottsch.fabric.magic_treasures.datagen;

import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(MagicTreasuresBlocks.TOPAZ_ORE, oreDrops(MagicTreasuresBlocks.TOPAZ_ORE, MagicTreasuresItems.TOPAZ));
        addDrop(MagicTreasuresBlocks.ONYX_ORE, oreDrops(MagicTreasuresBlocks.ONYX_ORE, MagicTreasuresItems.ONYX));
        addDrop(MagicTreasuresBlocks.JADEITE_ORE, oreDrops(MagicTreasuresBlocks.JADEITE_ORE, MagicTreasuresItems.JADEITE));
        addDrop(MagicTreasuresBlocks.RUBY_ORE, oreDrops(MagicTreasuresBlocks.RUBY_ORE, MagicTreasuresItems.RUBY));
        addDrop(MagicTreasuresBlocks.SAPPHIRE_ORE, oreDrops(MagicTreasuresBlocks.SAPPHIRE_ORE, MagicTreasuresItems.SAPPHIRE));
        addDrop(MagicTreasuresBlocks.SILVER_ORE, oreDrops(MagicTreasuresBlocks.SILVER_ORE, MagicTreasuresItems.RAW_SILVER));


        addDrop(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE, MagicTreasuresItems.TOPAZ));
        addDrop(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE, MagicTreasuresItems.ONYX));
        addDrop(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE, MagicTreasuresItems.JADEITE));
        addDrop(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE, MagicTreasuresItems.RUBY));
        addDrop(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE, MagicTreasuresItems.SAPPHIRE));
        addDrop(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE, MagicTreasuresItems.RAW_SILVER));

    }

//    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
//        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
//        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
//                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
//                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
//    }
}
