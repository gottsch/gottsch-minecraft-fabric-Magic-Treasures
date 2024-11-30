package mod.gottsch.fabric.magic_treasures.core.item;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class MagicTreasuresItems {
    /*
     * lists of all mod generated items.
     */
    public static final List<Item> ALL_JEWELRY = Lists.newArrayList();
    public static final List<Item> STANDARD_JEWELRY = Lists.newArrayList();
    public static final List<Item> CUSTOM_JEWELRY = Lists.newArrayList();
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

    // custom jewelry

    // common/uncommon
    public static Item SILBROS_RING_OF_VITALITY = registerItem("silbros_ring_of_vitality",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.WOOD)
//                            .with($ -> {
//                                $.gemstone = ModUtil.getName(Items.BEDROCK);
//                                $.maxLevelComponent = new MaxLevelComponent(2);
//                                $.repairsComponent = new RepairsComponent(1);
//                                $.manaComponent = new ManaComponent(80);
//                                $.rechargesComponent = new RechargesComponent(0);
//                                $.spellsComponent = new SpellsComponent(MagicTreasuresSpells.DEFAULT_HEALING);
//                            }).build()
            ).setAcceptsAffixing(p -> { return false;})
                    .setLoreKey("jewelry.silbros_ring_of_vitality.lore")
    );

    public static Item STRONGMANS_BRACERS = registerItem("strongmans_bracers",
            (Item) new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.BRACELET, JewelryMaterials.WOOD)
//                            .with($ -> {
//                                $.gemstone = ModUtil.getName(Items.BEDROCK);
//                                $.usesComponent = new UsesComponent(100);
//                                $.maxLevelComponent = new MaxLevelComponent(2);
//                                $.manaComponent = new ManaComponent(50);
//                                $.rechargesComponent = new RechargesComponent(1);
//                                $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.QUICK_STRENGTH).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                            }).build()
            ).setAcceptsAffixing(p -> { return false;})
    );

    // common
    public static Item PEASANTS_FORTUNE = registerItem("peasants_fortune",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.IRON, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = ModUtil.getName(Items.BEDROCK);
//                        $.usesComponent = new UsesComponent(125);
//                        $.maxLevelComponent = new MaxLevelComponent(4);
//                        $.manaComponent = new ManaComponent(250);
//                        $.rechargesComponent = new RechargesComponent(1);
//                    }).build()
            )
    );

    // uncommon
    public static Item AMULET_OF_DEFENCE = registerItem("amulet_of_defence",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.NECKLACE, JewelryMaterials.COPPER)
//                    .with($ -> {
//                        $.gemstone = TOPAZ.getRegistryEntry().registryKey().getValue();
//                        $.manaComponent = new ManaComponent(100);
//                        $.infinite = true;
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MAGIC_RESISTANCE).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    public static Item MALDRITCHS_FIRST_AMULET = registerItem("maldritchs_first_amulet",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.NECKLACE, JewelryMaterials.BONE)
//                    .with($ -> {
//                        $.gemstone = ONYX.getRegistryEntry().registryKey().getValue();
//                        $.usesComponent = new UsesComponent(300);
//                        $.repairsComponent = new RepairsComponent(1);
//                        $.manaComponent = new ManaComponent(150);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.HARM).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        $.spellFactorsComponent = new SpellFactorsComponent.Builder(JewelryMaterials.BONE)
//                                .with($$ -> {
//                                    $$.spellCostFactor = .95; // 0.1 points below regular bone
//                                }).build();
//                    }).build()
            ).setLoreKey("jewelry.maldritchs_first_amulet.lore")
    );

    public static Item AQUA_RING = registerItem("aqua_ring",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.SILVER)
//                    .with($ -> {
//                        $.gemstone = TOPAZ.getRegistryEntry().registryKey().getValue();
//                        $.infinite = true;
//                        $.manaComponent = new ManaComponent(150);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.WATER_BREATHING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    // scarce
    public static Item JOURNEYMANS_BANDS = registerItem("journeyman_bands",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = JADEITE.getRegistryEntry().registryKey().getValue();
//                        $.infinite = true;
//                        $.manaComponent = new ManaComponent(100);
//                        $.rechargesComponent = new RechargesComponent(0);
//                        $.spellsComponent = new SpellsComponent(
//                                SpellRegistry.get(MagicTreasuresSpells.SPEED).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
//                                SpellRegistry.get(MagicTreasuresSpells.NIGHT_VISION).orElse(MagicTreasuresSpells.DEFAULT_HEALING)
//                        );
//                    }).build()
            )
    );

    public static Item MEDICS_TOKEN = registerItem("medics_token",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = JADEITE.getRegistryEntry().registryKey().getValue();
//                        $.infinite = true;
//                        $.manaComponent = new ManaComponent(300);
//                        $.rechargesComponent = new RechargesComponent(1);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.GREATER_HEALING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    // scarce
    public static Item ADEPHAGIAS_BOUNTY = registerItem("adephagias_bounty",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = JADEITE.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(6);
//                        $.infinite = true;
//                        $.manaComponent = new ManaComponent(300);
//                        $.rechargesComponent = new RechargesComponent(1);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SATIETY).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    // rare
    public static Item SALANDAARS_WARD = registerItem("salandaars_ward",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = RUBY.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(7);
//                        $.manaComponent = new ManaComponent(350);
//                        $.rechargesComponent = new RechargesComponent(3);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MANA_TOWER_SHIELD).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    // epic
    public static Item ANGELS_RING = registerItem("angels_ring",
            new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = WHITE_PEARL.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(8);
//                        $.manaComponent = new ManaComponent(400);
//                        $.rechargesComponent = new RechargesComponent(3);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.REGENERATION).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            )
    );

    // TODO move to MagicTreasuresAffixes or Jewelry class
    static Predicate<ItemStack> castleRingAffixer = p -> {
        return p.isIn(MagicTreasuresTags.Items.CASTLE_RING_RUBY)
                | p.isIn(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE);
    };

    // rare / epic
    public static Item RING_OF_FORTITUDE = registerItem("ring_of_fortitude",
            (Item) new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = SAPPHIRE.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(7);
//                        $.manaComponent = new ManaComponent(350);
//                        $.rechargesComponent = new RechargesComponent(3);
//                        $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SHADOW_ARMOR).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
             ).setBaseName("castle_ring")
                    .setAcceptsAffixing(castleRingAffixer)
            //            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    // epic / legendary
    public static Item RING_LIFE_DEATH = registerItem("ring_of_life_death",
            (Item) new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.RING, JewelryMaterials.BLOOD, JewelrySizeTier.LORDS)
//                    .with($ -> {
//                        $.gemstone = RUBY.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(9);
//                        $.repairsComponent = new RepairsComponent(0);
//                        $.manaComponent = new ManaComponent(1000);
//                        $.rechargesComponent = new RechargesComponent(0);
//                        $.spellsComponent = new SpellsComponent(
//                                SpellRegistry.get(MagicTreasuresSpells.CHEAT_DEATH).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
//                                SpellRegistry.get(MagicTreasuresSpells.GREATER_DRAIN).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            ).setBaseName("twisted_ring")
                    .setAcceptsAffixing(p -> {return false;})
//            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    // rare / epic
    public static Item EYE_OF_THE_PHOENIX = registerItem("eye_of_the_phoenix",
            (Item) new NamedJewelry(new Item.Settings()
//                    new ItemSettingsDataComponentBuilder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                    .with($ -> {
//                        $.gemstone = BLACK_PEARL.getRegistryEntry().registryKey().getValue();
//                        $.maxLevelComponent = new MaxLevelComponent(7);
////                        $.repairsComponent = new RepairsComponent(3);
//                        $.infinite = true;
//                        $.manaComponent = new ManaComponent(300);
//                        $.rechargesComponent = new RechargesComponent(3);
//                        $.spellsComponent = new SpellsComponent(
//                                SpellRegistry.get(MagicTreasuresSpells.BLESSING_OF_THE_PHOENIX).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                    }).build()
            ).setBaseName("amulet")
                    .setAcceptsAffixing(p -> {return false;})
//            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    // recharge scroll
    public static Item RECHARGE_SCROLL = registerItem("recharge_scroll", new Item(new Item.Settings()));

    // register custom jewelry component builders
    static {
        JewelryRegistry.register(SILBROS_RING_OF_VITALITY,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.WOOD)
                        .with($ -> {
                            $.gemstone = ModUtil.getName(Items.BEDROCK);
                            $.maxLevelComponent = new MaxLevelComponent(2);
                            $.repairsComponent = new RepairsComponent(1);
                            $.manaComponent = new ManaComponent(80);
                            $.rechargesComponent = new RechargesComponent(0);
                            $.spellsComponent = new SpellsComponent(MagicTreasuresSpells.DEFAULT_HEALING);
                        }));
        // strongmans bracers
        JewelryRegistry.register(STRONGMANS_BRACERS,
                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.WOOD)
                        .with($ -> {
                            $.gemstone = ModUtil.getName(Items.BEDROCK);
                            $.usesComponent = new UsesComponent(100);
                            $.maxLevelComponent = new MaxLevelComponent(2);
                            $.manaComponent = new ManaComponent(50);
                            $.rechargesComponent = new RechargesComponent(1);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.QUICK_STRENGTH).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })
        );
        // peasants fortune
        JewelryRegistry.register(MagicTreasuresItems.PEASANTS_FORTUNE,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.IRON, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = ModUtil.getName(Items.BEDROCK);
                            $.usesComponent = new UsesComponent(125);
                            $.maxLevelComponent = new MaxLevelComponent(4);
                            $.manaComponent = new ManaComponent(250);
                            $.rechargesComponent = new RechargesComponent(1);
                        })

        );
        // amulet of defence
        JewelryRegistry.register(MagicTreasuresItems.AMULET_OF_DEFENCE,
                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.COPPER)
                        .with($ -> {
                            $.gemstone = ModUtil.getName(MagicTreasuresItems.TOPAZ);
                            $.manaComponent = new ManaComponent(100);
                            $.infinite = true;
                            $.repairsComponent = new RepairsComponent(0);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MAGIC_RESISTANCE).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })
        );
        // maldritchs first amulet
        JewelryRegistry.register(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET,
                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.BONE)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.ONYX.getRegistryEntry().registryKey().getValue();
                            $.usesComponent = new UsesComponent(300);
                            $.repairsComponent = new RepairsComponent(1);
                            $.manaComponent = new ManaComponent(150);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.HARM).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                            $.spellFactorsComponent = new SpellFactorsComponent.Builder(JewelryMaterials.BONE)
                                    .with($$ -> {
                                        $$.spellCostFactor = .95; // 0.1 points below regular bone
                                    }).build();
                        })
        );
        // aqua ring
        JewelryRegistry.register(MagicTreasuresItems.AQUA_RING,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.SILVER)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.TOPAZ.getRegistryEntry().registryKey().getValue();
                            $.infinite = true;
                            $.manaComponent = new ManaComponent(150);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.WATER_BREATHING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })
        );
        // journeymans bands
        JewelryRegistry.register(MagicTreasuresItems.JOURNEYMANS_BANDS,
                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
                            $.infinite = true;
                            $.manaComponent = new ManaComponent(100);
                            $.rechargesComponent = new RechargesComponent(0);
                            $.spellsComponent = new SpellsComponent(
                                    SpellRegistry.get(MagicTreasuresSpells.SPEED).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
                                    SpellRegistry.get(MagicTreasuresSpells.NIGHT_VISION).orElse(MagicTreasuresSpells.DEFAULT_HEALING)
                            );
                        })

        );
        // medics token
        JewelryRegistry.register(MagicTreasuresItems.MEDICS_TOKEN,
                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
                            $.infinite = true;
                            $.manaComponent = new ManaComponent(300);
                            $.rechargesComponent = new RechargesComponent(1);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.GREATER_HEALING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })
        );
        // adephagias bounty
        JewelryRegistry.register(MagicTreasuresItems.ADEPHAGIAS_BOUNTY,
                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(6);
                            $.infinite = true;
                            $.manaComponent = new ManaComponent(300);
                            $.rechargesComponent = new RechargesComponent(1);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SATIETY).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
        // salandaars ward
        JewelryRegistry.register(MagicTreasuresItems.SALANDAARS_WARD,
                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.RUBY.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(7);
                            $.manaComponent = new ManaComponent(350);
                            $.rechargesComponent = new RechargesComponent(3);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MANA_TOWER_SHIELD).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
        // angles ring
        JewelryRegistry.register(MagicTreasuresItems.ANGELS_RING,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.WHITE_PEARL.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(8);
                            $.manaComponent = new ManaComponent(400);
                            $.rechargesComponent = new RechargesComponent(3);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.REGENERATION).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
        // ring of fortitude
        JewelryRegistry.register(MagicTreasuresItems.RING_OF_FORTITUDE,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.SAPPHIRE.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(7);
                            $.manaComponent = new ManaComponent(350);
                            $.rechargesComponent = new RechargesComponent(3);
                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SHADOW_ARMOR).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
        // ring of life and death
        JewelryRegistry.register(MagicTreasuresItems.RING_LIFE_DEATH,
                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.BLOOD, JewelrySizeTier.LORDS)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.RUBY.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(9);
                            $.repairsComponent = new RepairsComponent(0);
                            $.manaComponent = new ManaComponent(1000);
                            $.rechargesComponent = new RechargesComponent(0);
                            $.spellsComponent = new SpellsComponent(
                                    SpellRegistry.get(MagicTreasuresSpells.CHEAT_DEATH).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
                                    SpellRegistry.get(MagicTreasuresSpells.GREATER_DRAIN).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
        // eye of the phoenix
        JewelryRegistry.register(MagicTreasuresItems.EYE_OF_THE_PHOENIX,
                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
                        .with($ -> {
                            $.gemstone = MagicTreasuresItems.BLACK_PEARL.getRegistryEntry().registryKey().getValue();
                            $.maxLevelComponent = new MaxLevelComponent(7);
//                        $.repairsComponent = new RepairsComponent(3);
                            $.infinite = true;
                            $.manaComponent = new ManaComponent(300);
                            $.rechargesComponent = new RechargesComponent(3);
                            $.spellsComponent = new SpellsComponent(
                                    SpellRegistry.get(MagicTreasuresSpells.BLESSING_OF_THE_PHOENIX).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
                        })

        );
    }

    static {
        CUSTOM_JEWELRY.add(SILBROS_RING_OF_VITALITY);
        CUSTOM_JEWELRY.add(STRONGMANS_BRACERS);
        CUSTOM_JEWELRY.add(PEASANTS_FORTUNE);
        CUSTOM_JEWELRY.add(AMULET_OF_DEFENCE);
        CUSTOM_JEWELRY.add(MALDRITCHS_FIRST_AMULET);
        CUSTOM_JEWELRY.add(AQUA_RING);
        CUSTOM_JEWELRY.add(JOURNEYMANS_BANDS);
        CUSTOM_JEWELRY.add(MEDICS_TOKEN);
        CUSTOM_JEWELRY.add(ADEPHAGIAS_BOUNTY);
        CUSTOM_JEWELRY.add(SALANDAARS_WARD);
        CUSTOM_JEWELRY.add(ANGELS_RING);
        CUSTOM_JEWELRY.add(RING_OF_FORTITUDE);
        CUSTOM_JEWELRY.add(RING_LIFE_DEATH);
        CUSTOM_JEWELRY.add(EYE_OF_THE_PHOENIX);
        // add all custom jewelry to all jewelry
        ALL_JEWELRY.addAll(CUSTOM_JEWELRY);

        // standard jewelry
        List<Triple<String, Jewelry, JewelryAttribs>> standardJewelryList = new ArrayList<>();
        JewelryBuilder builder = new JewelryBuilder(MagicTreasures.MOD_ID);
        standardJewelryList = builder.useBaseDefaults().build();
        standardJewelryList.addAll(builder.useStoneDefaults().build());

        // wood jewelry (only regular size)
        JewelryBuilder woodBuilder = new JewelryBuilder(MagicTreasures.MOD_ID);
        standardJewelryList.addAll(woodBuilder
                .materials(JewelryMaterials.WOOD)
                .sizes(JewelrySizeTier.REGULAR)
                .types(JewelryType.RING, JewelryType.BRACELET, JewelryType.NECKLACE)
                .build()
        );
        standardJewelryList.addAll(woodBuilder.useStoneDefaults().build());

        // lord's rings and necklaces
        JewelryBuilder lordsNecklacesBuilder = new JewelryBuilder(MagicTreasures.MOD_ID);
        standardJewelryList.addAll(lordsNecklacesBuilder
                .materials(
                        JewelryMaterials.IRON,
                        JewelryMaterials.COPPER,
                        JewelryMaterials.SILVER,
                        JewelryMaterials.GOLD,
                        JewelryMaterials.BONE)
                .sizes(JewelrySizeTier.LORDS)
                .types(JewelryType.RING, JewelryType.NECKLACE)
                .build()
        );
        standardJewelryList.addAll(lordsNecklacesBuilder.useStoneDefaults().build());

        // TODO create a custom castle ring builder like the hawks builder
        // castle rings
        List<Triple<String, Jewelry, JewelryComponents.Builder>> customJewelryList = new ArrayList<>();
        JewelryBuilder castleRingBuilder = new CastleJewelryBuilder(MagicTreasures.MOD_ID);
        customJewelryList.addAll(castleRingBuilder
                .types(JewelryType.RING)
                .materials(JewelryMaterials.SILVER)
                .sizes(JewelrySizeTier.REGULAR)
                .with($ -> {
                    $.baseName = "castle_ring";
                    $.loreKey = "jewelry.castle_ring.lore";
                    $.maxUses = 500;
                    $.maxMana = 200;
                    $.maxRepairs = 0;
                    $.acceptsAffixer = p -> {
                        return p.isIn(MagicTreasuresTags.Items.CASTLE_RING_RUBY)
                                | p.isIn(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE);
                    };
                })
                .buildComponentsBuilders());
        customJewelryList.addAll(castleRingBuilder
                .stones(RUBY.getRegistryEntry().registryKey().getValue(),
                        SAPPHIRE.getRegistryEntry().registryKey().getValue())
                .buildComponentsBuilders());

        JewelryBuilder castleRingBuilder2 = new CastleJewelryBuilder(MagicTreasures.MOD_ID);
        customJewelryList.addAll(castleRingBuilder2
                .types(JewelryType.RING)
                .materials(JewelryMaterials.GOLD)
                .sizes(JewelrySizeTier.REGULAR)
                .with($ -> {
                    $.baseName = "castle_ring";
                    $.loreKey = "jewelry.castle_ring.lore";
                    $.maxUses = 500;
                    $.maxMana = 300;
                    $.maxRepairs = 0;
                    $.acceptsAffixer = p -> {
                        return p.isIn(MagicTreasuresTags.Items.CASTLE_RING_RUBY)
                                | p.isIn(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE);
                    };
                })
                .buildComponentsBuilders());
        customJewelryList.addAll(castleRingBuilder2
                .stones(RUBY.getRegistryEntry().registryKey().getValue(),
                        SAPPHIRE.getRegistryEntry().registryKey().getValue())
                .buildComponentsBuilders());

        // hawks rings
        JewelryBuilder hawkRingBuilder = new HawkJewelryBuilder(MagicTreasures.MOD_ID);
        customJewelryList.addAll(hawkRingBuilder
                .materials(
                        JewelryMaterials.WOOD,
                        JewelryMaterials.IRON,
                        JewelryMaterials.COPPER,
                        JewelryMaterials.SILVER,
                        JewelryMaterials.GOLD
                )
                .sizes(JewelrySizeTier.REGULAR)
                .types(JewelryType.RING)
                .with($ -> {
                    $.baseName = "hawk_ring";
                    $.acceptsAffixer = p -> {return false;};
                })
                .buildComponentsBuilders()
        );

        // great and lord's hawk rings
        JewelryBuilder hawkRingBuilder2 = new HawkJewelryBuilder(MagicTreasures.MOD_ID);
        customJewelryList.addAll(hawkRingBuilder2
                .materials(
                        JewelryMaterials.IRON,
                        JewelryMaterials.COPPER,
                        JewelryMaterials.SILVER,
                        JewelryMaterials.GOLD
                )
                .sizes(JewelrySizeTier.GREAT, JewelrySizeTier.LORDS)
                .types(JewelryType.RING)
                .with($ -> {
                    $.baseName = "hawk_ring";
                    $.acceptsAffixer = p -> {return false;};
                })
                .buildComponentsBuilders()
        );

        // register all the standard jewelry (uses attribs)
        standardJewelryList.forEach(pair -> {
            Item item = registerItem(pair.getLeft(), pair.getMiddle());
            JewelryRegistry.register(item, pair.getRight());
            STANDARD_JEWELRY.add(item);
            MagicTreasures.LOGGER.debug("adding item -> {} to standard registry", item.getRegistryEntry().registryKey().getValue());
            ALL_JEWELRY.add(item);
        });

        // register all the custom jewelry (uses components)
        customJewelryList.forEach(triple -> {
            Item item = registerItem(triple.getLeft(), triple.getMiddle());
            JewelryRegistry.register(item, triple.getRight());
            CUSTOM_JEWELRY.add(item);
            ALL_JEWELRY.add(item);
        });

        // spell scroll
        SpellRegistry.values().forEach(spell -> {
            Item scroll = registerItem(spell.getName().getPath() + "_scroll",
                    new SpellScroll(new Item.Settings(), spell));
            // add scroll registry item to a list
            ALL_SPELL_SCROLLS.add(scroll);
        });
    }

    // register items to built-in item group
    // force load of static properties
    public static void register() {}

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MagicTreasures.MOD_ID, name), item);
    }

    @Deprecated
    public static Item.Settings createSettings(IJewelryType type, JewelryMaterial material) {
        return new Item.Settings()
                .component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), JewelrySizeTier.REGULAR.getName(),
                                material.getId(), null))
                .component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent((List<Identifier>) null))
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

    public static Item.Settings createSettings(IJewelryType type, JewelryMaterial material, IJewelrySizeTier sizeTier) {
        return new Item.Settings()
                .component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), sizeTier.getName(),
                                material.getId(), null))
                .component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent((List<Identifier>)null))
                .component(MagicTreasuresComponents.SPELL_FACTORS,
                        new SpellFactorsComponent.Builder(material).build())
                .component(MagicTreasuresComponents.MAX_LEVEL,
                        new MaxLevelComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.USES,
                        new UsesComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.REPAIRS,
                        new RepairsComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.MANA,
                        new ManaComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.RECHARGES,
                        new RechargesComponent.Builder(material).build());
    }

    public static Item.Settings createSettings(IJewelryType type, JewelryMaterial material, IJewelrySizeTier sizeTier, Identifier gemstone) {
        return new Item.Settings()
                .component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), sizeTier.getName(),
                                material.getId(), gemstone))
                .component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent((List<Identifier>)null))
                .component(MagicTreasuresComponents.SPELL_FACTORS,
                        new SpellFactorsComponent.Builder(material, gemstone).build())
                .component(MagicTreasuresComponents.MAX_LEVEL,
                        new MaxLevelComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.USES,
                        new UsesComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.REPAIRS,
                        new RepairsComponent.Builder(material, sizeTier).build())
                .component(MagicTreasuresComponents.MANA,
                        new ManaComponent.Builder(material, sizeTier, gemstone).build())
                .component(MagicTreasuresComponents.RECHARGES,
                        new RechargesComponent.Builder(material, gemstone).build());
    }

    public static class ItemSettingsDataComponentBuilder {
        private IJewelryType type;
        private JewelryMaterial material;
        private IJewelrySizeTier sizeTier;
        private JewelryStoneTier stoneTier;
        private Identifier gemstone;

        public JewelryAttribsComponent attribsComponent;
        public MaxLevelComponent maxLevelComponent;
        public UsesComponent usesComponent;
        public RepairsComponent repairsComponent;
        public ManaComponent manaComponent;
        public RechargesComponent rechargesComponent;
        public SpellFactorsComponent spellFactorsComponent;
        public SpellsComponent spellsComponent;

        public boolean infinite;

        public ItemSettingsDataComponentBuilder(IJewelryType type, JewelryMaterial material) {
            this.type = type;
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
            this.gemstone = null;
        }

        public ItemSettingsDataComponentBuilder(IJewelryType type, JewelryMaterial material, IJewelrySizeTier sizeTier) {
            this.type = type;
            this.material = material;
            this.sizeTier = sizeTier;
            this.gemstone = null;
        }

        public ItemSettingsDataComponentBuilder with(Consumer<ItemSettingsDataComponentBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public ItemSettingsDataComponentBuilder setInfinite() {
            this.infinite = true;
            return this;
        }

        public Item.Settings build() {
            Item.Settings settings = new Item.Settings();
            // attribs
            if (attribsComponent == null) {
                settings.component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        new JewelryAttribsComponent(type.getName(), sizeTier.getName(),
                                material.getId(), gemstone));
            } else {
                settings.component(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                        attribsComponent);
            }

            // max level
            if (maxLevelComponent == null) {
                settings.component(MagicTreasuresComponents.MAX_LEVEL,
                        new MaxLevelComponent.Builder(material, sizeTier).build());
            } else {
                settings.component(MagicTreasuresComponents.MAX_LEVEL, maxLevelComponent);
            }
            // uses
            if (usesComponent == null) {
                settings.component(MagicTreasuresComponents.USES,
                        new UsesComponent.Builder(material, sizeTier).build());
            } else if (this.infinite) {
                settings.component(MagicTreasuresComponents.USES, new UsesComponent(Integer.MAX_VALUE));
            } else {
                settings.component(MagicTreasuresComponents.USES, usesComponent);
            }
            // repairs
            if (repairsComponent == null) {
                settings.component(MagicTreasuresComponents.REPAIRS,
                        new RepairsComponent.Builder(material, sizeTier).build());
            } else {
                settings.component(MagicTreasuresComponents.REPAIRS, repairsComponent);
            }
            // mana
            if (manaComponent == null) {
                settings.component(MagicTreasuresComponents.MANA,
                        new ManaComponent.Builder(material, sizeTier, gemstone).build());
            } else {
                settings.component(MagicTreasuresComponents.MANA, manaComponent);
            }
            // recharges
            if (rechargesComponent == null) {
                settings.component(MagicTreasuresComponents.RECHARGES,
                        new RechargesComponent.Builder(material, gemstone).build());
            } else {
                settings.component(MagicTreasuresComponents.RECHARGES, rechargesComponent);
            }

            // spell factors
            if (spellFactorsComponent == null) {
                settings.component(MagicTreasuresComponents.SPELL_FACTORS,
                        new SpellFactorsComponent.Builder(material, gemstone).build());
            } else {
                settings.component(MagicTreasuresComponents.SPELL_FACTORS, spellFactorsComponent);
            }

            // spells
            if (spellsComponent == null) {
                settings.component(MagicTreasuresComponents.SPELLS,
                        new SpellsComponent((List<Identifier>)null));
            } else {
                settings.component(MagicTreasuresComponents.SPELLS, spellsComponent);
            }

            return settings;
        }
    }
}
