package mod.gottsch.fabric.magic_treasures.core.item;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellFactorsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.SpellsComponent;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class MagicTreasuresItems {
    // tab items
    public static final Item MAGIC_TREASURES_TAB = registerItem("magic_treasures_tab", new Item(new Item.Settings()));

    // gems
    public static final Item TOPAZ = registerItem("topaz", new Gemstone(new Item.Settings()));
    public static final Item ONYX = registerItem("onyx", new Gemstone(new Item.Settings()));
    public static final Item JADEITE = registerItem("jadeite", new Gemstone(new Item.Settings()));
    public static final Item RUBY = registerItem("ruby", new Gemstone(new Item.Settings()));
    public static final Item SAPPHIRE = registerItem("sapphire", new Gemstone(new Item.Settings()));
    public static final Item WHITE_PEARL = registerItem("white_pearl", new Gemstone(new Item.Settings()));
    public static final Item BLACK_PEARL = registerItem("black_pearl", new Gemstone(new Item.Settings()));

    public static final Item RAW_SILVER = registerItem("raw_silver", new Gemstone(new Item.Settings()));

    /*
     * a list of all mod generated items.
     */
    public static final List<Item> ALL_JEWELRY = Lists.newArrayList();
    public static final List<Item> STANDARD_JEWELRY = Lists.newArrayList();

    // jewelry
    public static final Item GOLD_RING = registerItem("gold_ring",
            new Jewelry(JewelryType.RING, JewelrySizeTier.REGULAR, JewelryMaterials.GOLD,
                    new Item.Settings().component(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT,
                            new JewelryVitalsComponent.Builder(JewelryType.RING, JewelryMaterials.GOLD).build())
                            .component(MagicTreasuresComponents.SPELLS_COMPONENT,
                                    new SpellsComponent(null))
                            .component(MagicTreasuresComponents.SPELL_FACTORS_COMPONENT,
                                    new SpellFactorsComponent.Builder(JewelryMaterials.GOLD).build())
            ));


    // register items to built-in item group
    public static void register() {
        ALL_JEWELRY.add(GOLD_RING);
        STANDARD_JEWELRY.add(GOLD_RING);
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
//            entries.add(TOPAZ);
//            entries.add(ONYX);
//            entries.add(JADEITE);
//            entries.add(RUBY);
//            entries.add(SAPPHIRE);
//            entries.add(RAW_SILVER);
//        });
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MagicTreasures.MOD_ID, name), item);
    }
}
