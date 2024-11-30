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

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.component.*;
import mod.gottsch.fabric.magic_treasures.core.jewelry.*;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems.*;

/**
 * this is a helper class to BUILD Jewelry Items at registration time.
 * NOTE since this building items, most mod registries (ex StoneRegistry)
 * will not be populated yet as they are data-driver and processed during Tag registration.
 * Created by Mark Gottschling on 11/21/2024
 */
public class JewelryBuilder {
    public List<JewelryType> types = new ArrayList<>();
    public List<JewelrySizeTier> sizes = new ArrayList<>();
    public List<JewelryMaterial> materials = new ArrayList<>();
    public List<Identifier> stones = new ArrayList<>();
    public String baseName;
    public String loreKey;
    public int maxUses = -1;
    public int maxMana = -1;
    public int maxRecharges = -1;
    public int maxRepairs = -1;
    public int maxLevel = 0;
    public boolean infinite = false;
    public Predicate<ItemStack> acceptsAffixer = p -> true;

    protected String modid;

    public JewelryBuilder(String modid) {
        this.modid = modid;
    }

    public JewelryBuilder clear() {
        types.clear();
        sizes.clear();
        materials.clear();
        stones.clear();
        return this;
    }

    /*
     * convenience setup
     */
    public JewelryBuilder useBaseDefaults() {
        types(JewelryType.BRACELET, JewelryType.NECKLACE, JewelryType.RING);
        sizes(JewelrySizeTier.REGULAR, JewelrySizeTier.GREAT);
        materials(
                JewelryMaterials.IRON,
                JewelryMaterials.COPPER,
                JewelryMaterials.SILVER,
                JewelryMaterials.GOLD,
                JewelryMaterials.BONE);
        return this;
    }

    public JewelryBuilder useStoneDefaults() {
        stones(
                ModUtil.getName(Items.DIAMOND),
                JADEITE.getRegistryEntry().registryKey().getValue(),
                TOPAZ.getRegistryEntry().registryKey().getValue(),
                ONYX.getRegistryEntry().registryKey().getValue(),
                RUBY.getRegistryEntry().registryKey().getValue(),
                SAPPHIRE.getRegistryEntry().registryKey().getValue(),
                WHITE_PEARL.getRegistryEntry().registryKey().getValue(),
                BLACK_PEARL.getRegistryEntry().registryKey().getValue()
        );
        return this;
    }

    public JewelryBuilder with(Consumer<JewelryBuilder> builder) {
        builder.accept(this);
        return this;
    }

    public JewelryBuilder types(JewelryType... types) {
        getTypes().addAll(Arrays.asList(types));
        return this;
    }

    public JewelryBuilder sizes(JewelrySizeTier... sizes) {
        getSizes().addAll(Arrays.asList(sizes));
        return this;
    }

    public JewelryBuilder materials(JewelryMaterial... materials) {
        getMaterials().addAll(Arrays.asList(materials));
        return this;
    }

    public JewelryBuilder stones(Identifier... sources) {
        getStones().addAll(Arrays.asList(sources));
        return this;
    }

    public List<Triple<String, Jewelry, JewelryAttribs>> build() {
        List<Triple<String, Jewelry, JewelryAttribs>> jewelryList = new ArrayList<>();

        if (types.isEmpty()) return jewelryList;
        if (sizes.isEmpty()) return jewelryList;

        List<Identifier> tempStones = new ArrayList<>();
        if (stones.isEmpty()) {
            tempStones.add(ModUtil.getName(Items.AIR));
        }
        else {
            tempStones.addAll(stones);
        }

        // create the jewelry
        types.forEach(type -> {
            sizes.forEach(size -> {
                materials.forEach(material -> {
                    tempStones.forEach(stone -> {
                        // build the name
                        String name = (size == JewelrySizeTier.REGULAR ? "" : size.getValue() + "_")
                                + material.getName() + "_"
                                + (stone.equals(ModUtil.getName(Items.AIR)) ? "" :  stone.getPath() + "_")
                                + (StringUtils.isNotBlank(baseName) ? baseName : type.toString());

                        // build the jewelry
                        Jewelry jewelry = createJewelry(type, material, size, stone);
                        MagicTreasures.LOGGER.debug("adding jewelry item -> {}", name);

                        // register the jewelry attribs
                        // NOTE this is for datagen purposes only
                        JewelryAttribs attribs = new JewelryAttribs(type, size, material, stone);

                        // build a triple
                        Triple<String, Jewelry, JewelryAttribs> triple = Triple.of(name.toLowerCase(), jewelry, attribs);
                        jewelryList.add(triple);
                    });
                });
            });
        });
        return jewelryList;
    }

    public List<Triple<String, Jewelry, JewelryComponents.Builder>> buildComponentsBuilders() {
        List<Triple<String, Jewelry, JewelryComponents.Builder>> jewelryList = new ArrayList<>();

        if (types.isEmpty()) return jewelryList;
        if (sizes.isEmpty()) return jewelryList;

        List<Identifier> tempStones = new ArrayList<>();
        if (stones.isEmpty()) {
            tempStones.add(ModUtil.getName(Items.AIR));
        }
        else {
            tempStones.addAll(stones);
        }

        // create the jewelry
        types.forEach(type -> {
            sizes.forEach(size -> {
                materials.forEach(material -> {
                    tempStones.forEach(stone -> {
                        // build the name
                        String name = (size == JewelrySizeTier.REGULAR ? "" : size.getValue() + "_")
                                + material.getName() + "_"
                                + (stone.equals(ModUtil.getName(Items.AIR)) ? "" :  stone.getPath() + "_")
                                + (StringUtils.isNotBlank(baseName) ? baseName : type.toString());

                        // build the jewelry
                        Jewelry jewelry = createJewelry(type, material, size, stone);
                        MagicTreasures.LOGGER.debug("adding jewelry item -> {}", name);

                        // create components
                        JewelryComponents.Builder builder = createComponentsBuilder(type, material, size, stone);

                        // build a triple
                        Triple<String, Jewelry, JewelryComponents.Builder> triple = Triple.of(name.toLowerCase(), jewelry, builder);
                        jewelryList.add(triple);
                    });
                });
            });
        });
        return jewelryList;
    }

    public Jewelry createJewelry(JewelryType type, JewelryMaterial material, JewelrySizeTier size, Identifier stone) {
            Jewelry j = new Jewelry(new Item.Settings()/*createSettings(type, material, size, stone)*/);
            return (Jewelry) j.setBaseName(JewelryBuilder.this.baseName)
                    .setAcceptsAffixing(JewelryBuilder.this.acceptsAffixer)
                    .setLoreKey(JewelryBuilder.this.loreKey);
    }

    public JewelryComponents.Builder createComponentsBuilder(JewelryType type, JewelryMaterial material, JewelrySizeTier size, Identifier stone) {
        JewelryComponents.Builder builder = new JewelryComponents.Builder(type, material, size)
                .with($ -> {
                    $.gemstone = stone;
                    $.maxLevelComponent = this.maxLevel <= 0 ? null : new MaxLevelComponent(this.maxLevel);
                    $.usesComponent = this.maxUses <= 0 ? null : new UsesComponent(this.maxUses);
                    $.repairsComponent = this.maxRepairs <=0 ? null : new RepairsComponent(this.maxRepairs);
                    $.infinite = this.infinite;
                    $.manaComponent = this.maxMana <= 0 ? null : new ManaComponent(this.maxMana);
                    $.rechargesComponent = this.maxRecharges <= 0 ? null : new RechargesComponent(this.maxRecharges);
                });
        return builder;
    }

    public List<JewelryType> getTypes() {
        return types;
    }

    public List<JewelrySizeTier> getSizes() {
        return sizes;
    }

    public List<JewelryMaterial> getMaterials() {
        return materials;
    }

    public List<Identifier> getStones() {
        return stones;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public String getModid() {
        return modid;
    }
}
