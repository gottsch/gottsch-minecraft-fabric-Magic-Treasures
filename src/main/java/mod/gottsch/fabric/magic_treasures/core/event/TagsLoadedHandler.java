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
package mod.gottsch.fabric.magic_treasures.core.event;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import mod.gottsch.fabric.magic_treasures.core.registry.JewelryRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.registry.TagRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.MagicTreasuresSpells;
import mod.gottsch.fabric.magic_treasures.core.spell.SpellRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by Mark Gottschling on Nov 14, 2024
 */
public class TagsLoadedHandler implements CommonLifecycleEvents.TagsLoaded {

    public static class Attribs {
        public IJewelryType type;
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;
        public Identifier stone = Items.AIR.getRegistryEntry().registryKey().getValue();
    }

    private static Map<Item, Attribs> attribsMap = new HashMap<>();

    @Override
    public void onTagsLoaded(DynamicRegistryManager dynamicRegistryManager, boolean b) {
        MagicTreasures.LOGGER.debug("in onTagLoaded event");

        // clear any registries
        JewelryRegistry.clear();

        // NOTE gemstone must be registered first so they are available for the rest of registration
        // process all items in the JewelryStoneTier tags
        List<JewelryStoneTier> tiers = MagicTreasuresApi.getJewelryStoneTiers();
        tiers.forEach(tier -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryStoneTierTag(tier);
            if (tagKey != null) {
                // get the tag
                List<Item> tagItems = getItemsFromTag(tagKey);

                // for each item in the tag
                for (Item stone : tagItems) {
                    // register the stone with the StoneRegistry
                    StoneRegistry.register(stone);
                    // register the stone with the tier in the StoneRegistry
                    StoneRegistry.register(stone, tier);
                    MagicTreasures.LOGGER.debug("registering stone to tier -> {} <--> {} ", ModUtil.getName(stone), tier.getName());
                }
            }
        });

        // TODO maybe create the custom component builders and register them
        // earlier. then here we can build and register the components.
        // MagicTreasureItems/CommonSetup -> registerCustomComponentBuilder(item, builder); -> JewelryRegister.registerComponentBuilder(item, builder);
        // TagsLoadedHandler -> JewelryRegister.getComponentBuilders().forEach() -> JewelryRegistry.register(item. builder.build())
        // NOTE acts like a deferred registry. but this way, custom items from other mods can be registered.
        // NOTE this also makes the Custom Tag moot unless used for anvil event and loot functions.

        // TEMP
        registerCustomJewelry();

        /*
         * register all jewelry items
         */
        // process custom items from custom tag
//        TagKey<Item> tagKey = MagicTreasuresTags.Items.CUSTOM_JEWELRY;
//        if (tagKey != null) {
//            // get the tag
//            List<Item> tagItems = getItemsFromTag(tagKey);
//            // for each item in the tag
//            for (Item jewelry : tagItems) {
//                // TODO at this point we are registering jewelry that is used
//                // in the game. if an item is taken out of the tag, it should not
//                // be available for loot tables etc.
//                // TODO how/where are custom jewelry components setup and registered?
//
//                JewelryRegistry.register(jewelry);
//            }
//        }

        // process all items in the JewelryType tags
        List<IJewelryType> types = MagicTreasuresApi.getJewelryTypes();
        types.forEach(type -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryTypeTag(type);
            if (tagKey != null) {
                // get the tag
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (!jewelry.getRegistryEntry().isIn(MagicTreasuresTags.Items.CUSTOM_JEWELRY)) {
                        Attribs attribs = new Attribs();
                        attribs.type = type;
                        attribsMap.put(jewelry, attribs);
//                    MagicTreasures.LOGGER.debug("registering jewelry -> {} ", ModUtil.getName(jewelry));
                    }
                }
            }
        });

        // process all items in the Material tags
        List<JewelryMaterial> materials = MagicTreasuresApi.getJewelryMaterials();
        materials.forEach(material -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryMaterialTag(material);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).material = material;
                    }
                }
            }
        });

        // process all items in the size tier tags
        List<IJewelrySizeTier> sizeTiers = MagicTreasuresApi.getJewelrySizeTiers();
        sizeTiers.forEach(sizeTier -> {
            TagKey<Item> tagKey = TagRegistry.getJewelrySizeTierTag(sizeTier);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).sizeTier = sizeTier;
                    }
                }
            }
        });

        // NOTE this must come after processing Stone Tiers since that is where
        // the stones are first registered.
        // process all the items in the stones tags
        List<Item> stones = MagicTreasuresApi.getJewelryStones();
        stones.forEach(stone -> {
            TagKey<Item> tagKey = TagRegistry.getJewelryStoneTag(stone);
            if (tagKey != null) {
                // get the tagged items
                List<Item> tagItems = getItemsFromTag(tagKey);
                // for each item in the tag
                for (Item jewelry : tagItems) {
                    if (attribsMap.containsKey(jewelry)) {
                        attribsMap.get(jewelry).stone = ModUtil.getName(stone);
                    }
                }
            }
        });

        // now we can finally register the jewelry
        attribsMap.forEach((k,v) -> {
            // build attribs
            JewelryAttribs attribs = new JewelryAttribs(v.type, v.sizeTier, v.material, v.stone);
            JewelryRegistry.register(k, attribs);
        });

        // registry stones by rarity
        List<IRarity> rarities = MagicTreasuresApi.getRarities();
        rarities.forEach(rarity -> {
            TagKey<Item> tagKey = TagRegistry.getStoneRarityTag(rarity);
            if (tagKey != null) {
                // get the tagged list of items
                List<Item> tagItems = getItemsFromTag(tagKey);
                for (Item stone : tagItems) {
                    StoneRegistry.register(rarity, stone);
                    MagicTreasures.LOGGER.debug("registering stone to rarity -> {} <--> {} ", ModUtil.getName(stone), rarity.getName());
                }
            }
            // register jewelry by rarity
            MagicTreasuresApi.getJewelryRarityTag(rarity)
                    .ifPresent(key -> {
                        List<Item> tagItems = getItemsFromTag(key);
                        tagItems.forEach(item -> {
                            JewelryRegistry.register(rarity, item);
                            MagicTreasures.LOGGER.debug("registering jewelry to rarity -> {} <--> {} ", ModUtil.getName(item), rarity.getName());
                        });
                    });
        });
    }

    @Deprecated
    private void registerCustomJewelry() {
        // castle rings

        // hawk rings


        // build and register all components for custom jewelry
        // NOTE this might be moot as the ItemEntity should use the builder to generate NEW components.
        // this method will generate 1 copy of the components to be shared among all ItemStacks... not good.

//        JewelryRegistry.getComponentsBuilders().forEach(builderSet -> {
//            JewelryRegistry.register(Registries.ITEM.get(builderSet.getKey()),
//                    builderSet.getValue().build());
//        });

        // silbros
//        JewelryRegistry.register(MagicTreasuresItems.SILBROS_RING_OF_VITALITY,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.WOOD)
//                .with($ -> {
//                    $.gemstone = ModUtil.getName(Items.BEDROCK);
//                    $.maxLevelComponent = new MaxLevelComponent(2);
//                    $.repairsComponent = new RepairsComponent(1);
//                    $.manaComponent = new ManaComponent(80);
//                    $.rechargesComponent = new RechargesComponent(0);
//                    $.spellsComponent = new SpellsComponent(MagicTreasuresSpells.DEFAULT_HEALING);
//                }).build()
//        );

//        // strongmans bracers
//        JewelryRegistry.register(MagicTreasuresItems.STRONGMANS_BRACERS,
//                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.WOOD)
//                        .with($ -> {
//                            $.gemstone = ModUtil.getName(Items.BEDROCK);
//                            $.usesComponent = new UsesComponent(100);
//                            $.maxLevelComponent = new MaxLevelComponent(2);
//                            $.manaComponent = new ManaComponent(50);
//                            $.rechargesComponent = new RechargesComponent(1);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.QUICK_STRENGTH).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//        );
//        // peasants fortune
//        JewelryRegistry.register(MagicTreasuresItems.PEASANTS_FORTUNE,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.IRON, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = ModUtil.getName(Items.BEDROCK);
//                            $.usesComponent = new UsesComponent(125);
//                            $.maxLevelComponent = new MaxLevelComponent(4);
//                            $.manaComponent = new ManaComponent(250);
//                            $.rechargesComponent = new RechargesComponent(1);
//                        }).build()
//
//                );
//        // amulet of defence
//        JewelryRegistry.register(MagicTreasuresItems.AMULET_OF_DEFENCE,
//                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.COPPER)
//                        .with($ -> {
//                            $.gemstone = ModUtil.getName(MagicTreasuresItems.TOPAZ);
//                            $.manaComponent = new ManaComponent(100);
//                            $.infinite = true;
//                            $.repairsComponent = new RepairsComponent(0);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MAGIC_RESISTANCE).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//                );
//        // maldritchs first amulet
//        JewelryRegistry.register(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET,
//                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.BONE)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.ONYX.getRegistryEntry().registryKey().getValue();
//                            $.usesComponent = new UsesComponent(300);
//                            $.repairsComponent = new RepairsComponent(1);
//                            $.manaComponent = new ManaComponent(150);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.HARM).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                            $.spellFactorsComponent = new SpellFactorsComponent.Builder(JewelryMaterials.BONE)
//                                    .with($$ -> {
//                                        $$.spellCostFactor = .95; // 0.1 points below regular bone
//                                    }).build();
//                        }).build()
//                );
//        // aqua ring
//        JewelryRegistry.register(MagicTreasuresItems.AQUA_RING,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.SILVER)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.TOPAZ.getRegistryEntry().registryKey().getValue();
//                            $.infinite = true;
//                            $.manaComponent = new ManaComponent(150);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.WATER_BREATHING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//                );
//
//        // journeymans bands
//        JewelryRegistry.register(MagicTreasuresItems.JOURNEYMANS_BANDS,
//                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
//                            $.infinite = true;
//                            $.manaComponent = new ManaComponent(100);
//                            $.rechargesComponent = new RechargesComponent(0);
//                            $.spellsComponent = new SpellsComponent(
//                                    SpellRegistry.get(MagicTreasuresSpells.SPEED).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
//                                    SpellRegistry.get(MagicTreasuresSpells.NIGHT_VISION).orElse(MagicTreasuresSpells.DEFAULT_HEALING)
//                            );
//                        }).build()
//
//                );
//        // medics token
//        JewelryRegistry.register(MagicTreasuresItems.MEDICS_TOKEN,
//                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
//                            $.infinite = true;
//                            $.manaComponent = new ManaComponent(300);
//                            $.rechargesComponent = new RechargesComponent(1);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.GREATER_HEALING).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//                );
//        // adephagias bounty
//        JewelryRegistry.register(MagicTreasuresItems.ADEPHAGIAS_BOUNTY,
//                new JewelryComponents.Builder(JewelryType.BRACELET, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.JADEITE.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(6);
//                            $.infinite = true;
//                            $.manaComponent = new ManaComponent(300);
//                            $.rechargesComponent = new RechargesComponent(1);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SATIETY).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
//        // salandaars ward
//        JewelryRegistry.register(MagicTreasuresItems.SALANDAARS_WARD,
//                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.RUBY.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(7);
//                            $.manaComponent = new ManaComponent(350);
//                            $.rechargesComponent = new RechargesComponent(3);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.MANA_TOWER_SHIELD).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
//        // angles ring
//        JewelryRegistry.register(MagicTreasuresItems.ANGELS_RING,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.WHITE_PEARL.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(8);
//                            $.manaComponent = new ManaComponent(400);
//                            $.rechargesComponent = new RechargesComponent(3);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.REGENERATION).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
//        // ring of fortitude
//        JewelryRegistry.register(MagicTreasuresItems.RING_OF_FORTITUDE,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.SAPPHIRE.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(7);
//                            $.manaComponent = new ManaComponent(350);
//                            $.rechargesComponent = new RechargesComponent(3);
//                            $.spellsComponent = new SpellsComponent(SpellRegistry.get(MagicTreasuresSpells.SHADOW_ARMOR).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
//        // ring of life and death
//        JewelryRegistry.register(MagicTreasuresItems.RING_LIFE_DEATH,
//                new JewelryComponents.Builder(JewelryType.RING, JewelryMaterials.BLOOD, JewelrySizeTier.LORDS)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.RUBY.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(9);
//                            $.repairsComponent = new RepairsComponent(0);
//                            $.manaComponent = new ManaComponent(1000);
//                            $.rechargesComponent = new RechargesComponent(0);
//                            $.spellsComponent = new SpellsComponent(
//                                    SpellRegistry.get(MagicTreasuresSpells.CHEAT_DEATH).orElse(MagicTreasuresSpells.DEFAULT_HEALING),
//                                    SpellRegistry.get(MagicTreasuresSpells.GREATER_DRAIN).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
//        // eye of the phoenix
//        JewelryRegistry.register(MagicTreasuresItems.EYE_OF_THE_PHOENIX,
//                new JewelryComponents.Builder(JewelryType.NECKLACE, JewelryMaterials.GOLD, JewelrySizeTier.GREAT)
//                        .with($ -> {
//                            $.gemstone = MagicTreasuresItems.BLACK_PEARL.getRegistryEntry().registryKey().getValue();
//                            $.maxLevelComponent = new MaxLevelComponent(7);
////                        $.repairsComponent = new RepairsComponent(3);
//                            $.infinite = true;
//                            $.manaComponent = new ManaComponent(300);
//                            $.rechargesComponent = new RechargesComponent(3);
//                            $.spellsComponent = new SpellsComponent(
//                                    SpellRegistry.get(MagicTreasuresSpells.BLESSING_OF_THE_PHOENIX).orElse(MagicTreasuresSpells.DEFAULT_HEALING));
//                        }).build()
//
//                );
    }

    /**
     * this is an extremely inefficient method to get tags items, other than
     * one-time setup situations.
     * @param tagKey
     * @return
     */
    public static List<Item> getItemsFromTag(TagKey<Item> tagKey) {
        return Registries.ITEM.stream()
                .filter(item -> item.getRegistryEntry().isIn(tagKey))
                .toList();
    }
}
