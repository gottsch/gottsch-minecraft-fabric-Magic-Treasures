package mod.gottsch.fabric.magic_treasures.core.item;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class MagicTreasuresItems {
    /*
     * lists of all mod generated items.
     */
    public static final List<Item> ALL_JEWELRY = Lists.newArrayList();
    public static final List<Item> STANDARD_JEWELRY = Lists.newArrayList();
    public static final List<Item> ALL_SPELL_SCROLLS = Lists.newArrayList();

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


    // spell scrolls
    static {
        SpellRegistry.values().forEach(spell -> {
            Item scroll = registerItem(spell.getName().getPath() + "_scroll",
                    new SpellScroll(new Item.Settings(), spell));
            // add scroll registry item to a list
            ALL_SPELL_SCROLLS.add(scroll);
        });
    }

    // jewelry
    public static final Item GOLD_RING = registerItem("gold_ring",
            new Jewelry(createSettings(JewelryType.RING, JewelryMaterials.GOLD)
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

    public static Item.Settings createSettings(IJewelryType type, JewelryMaterial material) {
        return new Item.Settings()
                .component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), JewelrySizeTier.REGULAR.getName(),
                                material.getId(), null))
//                .component(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT,
//                        new JewelryVitalsComponent.Builder(type, material).build())
                .component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent(null))
                .component(MagicTreasuresComponents.SPELL_FACTORS,
                        new SpellFactorsComponent.Builder(material).build())
                .component(MagicTreasuresComponents.MAX_LEVEL,
                        new MaxLevelComponent.Builder(material).build())
                .component(MagicTreasuresComponents.USES,
                        new UsesComponent.Builder(material).build())
                .component(MagicTreasuresComponents.REPAIRS,
                        new RepairsComponent.Builder(material).build())
                .component(MagicTreasuresComponents.MANA,
                        new ManaComponent.Builder(material).build())
                .component(MagicTreasuresComponents.RECHARGES,
                        new RechargesComponent.Builder(material).build());
    }
}
