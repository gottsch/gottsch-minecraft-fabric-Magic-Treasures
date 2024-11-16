package mod.gottsch.fabric.magic_treasures.core.block;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on Nov 14, 2024 (fabric version)
 */
public class MagicTreasuresBlocks {
    public static final Block TOPAZ_ORE = registerBlock("topaz_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    public static final Block ONYX_ORE = registerBlock("onyx_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    public static final Block JADEITE_ORE = registerBlock("jadeite_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    public static final Block RUBY_ORE = registerBlock("ruby_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    public static final Block SAPPHIRE_ORE = registerBlock("sapphire_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    public static final Block SILVER_ORE = registerBlock("silver_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));

    public static final Block DEEPSLATE_TOPAZ_ORE = registerBlock("deepslate_topaz_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_ONYX_ORE = registerBlock("deepslate_onyx_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_JADEITE_ORE = registerBlock("deepslate_jadeite_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).requiresTool()));

    /**
     *
     */
    public static void register() {
        // register item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(TOPAZ_ORE);
            content.add(ONYX_ORE);
            // TODO add all blocks
        });
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MagicTreasures.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MagicTreasures.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }
}
