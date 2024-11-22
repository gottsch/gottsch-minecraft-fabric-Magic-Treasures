package mod.gottsch.fabric.magic_treasures.core.item;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class ModItemGroups {
    public static final ItemGroup MAGIC_TREASURES_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MagicTreasures.MOD_ID, "magic_treasures_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(MagicTreasuresItems.MAGIC_TREASURES_TAB))
                    .displayName(Text.translatable("itemgroup.magictreasures.magic_treasures_tab"))
                    .entries((displayContext, entries) -> {
                        entries.add(MagicTreasuresItems.TOPAZ);
                        entries.add(MagicTreasuresItems.ONYX);
                        entries.add(MagicTreasuresItems.JADEITE);
                        entries.add(MagicTreasuresItems.RUBY);
                        entries.add(MagicTreasuresItems.SAPPHIRE);
                        entries.add(MagicTreasuresItems.WHITE_PEARL);
                        entries.add(MagicTreasuresItems.BLACK_PEARL);
                        entries.add(MagicTreasuresItems.RAW_SILVER);

                        entries.add(MagicTreasuresBlocks.TOPAZ_ORE);
                        entries.add(MagicTreasuresBlocks.ONYX_ORE);
                        entries.add(MagicTreasuresBlocks.JADEITE_ORE);
                        entries.add(MagicTreasuresBlocks.RUBY_ORE);
                        entries.add(MagicTreasuresBlocks.SAPPHIRE_ORE);
                        entries.add(MagicTreasuresBlocks.SILVER_ORE);

                        entries.add(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE);
                        entries.add(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE);
                        entries.add(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE);
                        entries.add(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE);
                        entries.add(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE);
                        entries.add(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE);

                        MagicTreasuresItems.ALL_JEWELRY.forEach(entries::add);

                        // add all scrolls
                        MagicTreasuresItems.ALL_SPELL_SCROLLS.forEach(entries::add);

                    }).build());


    public static void registerItemGroups() {
        MagicTreasures.LOGGER.info("Registering Item Groups for " + MagicTreasures.MOD_ID);
    }
}
