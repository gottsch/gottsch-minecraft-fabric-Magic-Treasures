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


        addDrop(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE, oreDrops(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE, MagicTreasuresItems.TOPAZ));
    }

//    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
//        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
//        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
//                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
//                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
//    }
}
