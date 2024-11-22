package mod.gottsch.fabric.magic_treasures.core.item;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems.*;

/**
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
    public int maxRepairs = -1;
    public int maxLevel = 0;
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

    public List<Pair<String, Jewelry>> build() {
        List<Pair<String, Jewelry>> jewelry = new ArrayList<>();

        if (types.isEmpty()) return jewelry;
        if (sizes.isEmpty()) return jewelry;

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

                        // build the jewelry supplier
                        Jewelry a = createJewelry(type, material, size, stone);
                        MagicTreasures.LOGGER.debug("adding deferred jewelry item -> {}", name);

                        // build a pair
                        Pair<String, Jewelry> pair = Pair.of(name.toLowerCase(), a);
                        jewelry.add(pair);
                    });
                });
            });
        });
        return jewelry;
    }

    public Jewelry createJewelry(JewelryType type, JewelryMaterial material, JewelrySizeTier size, Identifier stone) {
            Jewelry j = new Jewelry(createSettings(type, material, size, stone));
            return (Jewelry) j.setLoreKey(JewelryBuilder.this.loreKey);
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
