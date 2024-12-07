/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * Magic Treasures is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magic Treasures is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Magic Treasures.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.magic_treasures.core.item;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
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

    // metals / ingots / ores
    public static Item SILVER_INGOT = registerItem("silver_ingot", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text>tooltip, TooltipType flag) {
            // TODO
        }
    });
    public static final Item RAW_SILVER = registerItem("raw_silver", new Gemstone(new Item.Settings()));

    // recipe scrolls
    public static Item RING_RECIPE = registerItem("ring_recipe", new JewelryRecipeScroll(new Item.Settings()));
    public static Item NECKLACE_RECIPE = registerItem("necklace_recipe", new JewelryRecipeScroll(new Item.Settings()));
    public static Item BRACELET_RECIPE = registerItem("bracelet_recipe", new JewelryRecipeScroll(new Item.Settings()));
    static {
        MagicTreasuresApi.registerGemstone(MagicTreasuresItems.TOPAZ);
    }

    // recharge scroll
    public static Item RECHARGE_SCROLL = registerItem("recharge_scroll", new Item(new Item.Settings()));

    /*
     * custom jewelry
     */
    // common/uncommon
    public static Item SILBROS_RING_OF_VITALITY = registerItem("silbros_ring_of_vitality",
            new NamedJewelry(new Item.Settings())
                    .setAcceptsAffixing(p -> { return false;})
                    .setLoreKey("jewelry.silbros_ring_of_vitality.lore")
    );

    public static Item STRONGMANS_BRACERS = registerItem("strongmans_bracers",
            (Item) new NamedJewelry(new Item.Settings())
                    .setAcceptsAffixing(p -> { return false;})
    );

    // common
    public static Item PEASANTS_FORTUNE = registerItem("peasants_fortune",
            new NamedJewelry(new Item.Settings())
    );

    // uncommon
    public static Item AMULET_OF_DEFENCE = registerItem("amulet_of_defence",
            new NamedJewelry(new Item.Settings())
    );

    public static Item MALDRITCHS_FIRST_AMULET = registerItem("maldritchs_first_amulet",
            new NamedJewelry(new Item.Settings())
                    .setLoreKey("jewelry.maldritchs_first_amulet.lore")
    );

    public static Item AQUA_RING = registerItem("aqua_ring",
            new NamedJewelry(new Item.Settings())
    );

    // scarce
    public static Item JOURNEYMANS_BANDS = registerItem("journeyman_bands",
            new NamedJewelry(new Item.Settings())
    );

    public static Item MEDICS_TOKEN = registerItem("medics_token",
            new NamedJewelry(new Item.Settings())
    );

    // scarce
    public static Item ADEPHAGIAS_BOUNTY = registerItem("adephagias_bounty",
            new NamedJewelry(new Item.Settings())
    );

    // rare
    public static Item SALANDAARS_WARD = registerItem("salandaars_ward",
            new NamedJewelry(new Item.Settings())
    );

    // epic
    public static Item ANGELS_RING = registerItem("angels_ring",
            new NamedJewelry(new Item.Settings())
    );

    // TODO move to MagicTreasuresAffixes or Jewelry class
    static Predicate<ItemStack> castleRingAffixer = p -> {
        return p.isIn(MagicTreasuresTags.Items.CASTLE_RING_RUBY)
                | p.isIn(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE);
    };

    // rare / epic
    public static Item RING_OF_FORTITUDE = registerItem("ring_of_fortitude",
            (Item) new NamedJewelry(new Item.Settings())
                    .setBaseName("castle_ring")
                    .setAcceptsAffixing(castleRingAffixer)
            //            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    // epic / legendary
    public static Item RING_LIFE_DEATH = registerItem("ring_of_life_death",
            (Item) new NamedJewelry(new Item.Settings())
                    .setBaseName("twisted_ring")
                    .setAcceptsAffixing(p -> {return false;})
//            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    // rare / epic
    public static Item EYE_OF_THE_PHOENIX = registerItem("eye_of_the_phoenix",
            (Item) new NamedJewelry(new Item.Settings()).setBaseName("amulet")
                    .setAcceptsAffixing(p -> {return false;})
//            if (!EnchantmentHelper.hasVanishingCurse(stack)) {
//                stack.enchant(Enchantments.VANISHING_CURSE, 1);
//            }
    );

    static {
        // NOTE list for datagen purposes
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
        List<Triple<String, Jewelry, JewelryComponents.Builder>> standardJewelryList = new ArrayList<>();
        JewelryBuilder builder = new JewelryBuilder(MagicTreasures.MOD_ID);
        standardJewelryList = builder.useBaseDefaults().buildComponentsBuilders();
        standardJewelryList.addAll(builder.useStoneDefaults().buildComponentsBuilders());

        // wood jewelry (only regular size)
        JewelryBuilder woodBuilder = new JewelryBuilder(MagicTreasures.MOD_ID);
        standardJewelryList.addAll(woodBuilder
                .materials(JewelryMaterials.WOOD)
                .sizes(JewelrySizeTier.REGULAR)
                .types(JewelryType.RING, JewelryType.BRACELET, JewelryType.NECKLACE)
                .buildComponentsBuilders()
        );
        standardJewelryList.addAll(woodBuilder.useStoneDefaults().buildComponentsBuilders());
        // TODO have Custom Wood that are in larger sizes like Silbro's Strong Ring, etc.

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
                .buildComponentsBuilders()
        );
        standardJewelryList.addAll(lordsNecklacesBuilder.useStoneDefaults().buildComponentsBuilders());

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
        standardJewelryList.forEach(triple -> {
            Item item = registerItem(triple.getLeft(), triple.getMiddle());
            JewelryRegistry.register(item, triple.getRight());
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
