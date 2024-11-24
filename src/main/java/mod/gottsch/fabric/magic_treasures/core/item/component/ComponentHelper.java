package mod.gottsch.fabric.magic_treasures.core.item.component;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Optional;

/**
 * Created by Mark Gottschling on 11/24/2024
 */
public class ComponentHelper {
    /*
     * component accessors
     */
    // attribs
    public static Optional<JewelryAttribsComponent> attribs(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.JEWELRY_ATTRIBS));
    }

    // max level
    public static Optional<MaxLevelComponent> maxLevel(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.MAX_LEVEL));
    }

    // uses
    public static Optional<UsesComponent> uses(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.USES));
    }

    // repairs
    public static Optional<RepairsComponent> repairs(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.REPAIRS));
    }


    // mana
    public static Optional<ManaComponent> mana(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.MANA));
    }

    // recharges
    public static Optional<RechargesComponent> recharges(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.RECHARGES));
    }

    // spell factors
    public static Optional<SpellFactorsComponent> spellFactors(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.SPELL_FACTORS));
    }

    // spells
    public static Optional<SpellsComponent> spells(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.SPELLS));
    }

    // cooldown
    public static Optional<Long> cooldown(ItemStack stack) {
        return Optional.ofNullable(stack.get(MagicTreasuresComponents.COOLDOWN));
    }

    /*
     * component property accessors
     */
    public static Optional<IJewelryType> jewelryType(ItemStack stack) {
        return attribs(stack).flatMap(a -> MagicTreasuresApi.getJewelryType(a.type()));
    }

    public static Optional<IJewelrySizeTier> sizeTier(ItemStack stack) {
        return attribs(stack).flatMap(a -> MagicTreasuresApi.getJewelrySize(a.sizeTier()));
    }

    public static Optional<JewelryMaterial> material(ItemStack stack) {
        return attribs(stack).flatMap(a -> MagicTreasuresApi.getJewelryMaterial(a.material()));
    }

    public static Optional<Item> gemstone(ItemStack stack) {
        return attribs(stack).flatMap(a -> StoneRegistry.get(a.gemstone()));
    }

    public static boolean hasGemstone(ItemStack stack) {
        return attribs(stack).map(a -> {
            if (a.gemstone() != null) {
                Item stoneItem = StoneRegistry.get(a.gemstone()).orElse(Items.AIR);
                return stoneItem != null
                        && stoneItem != Items.AIR
                        && stoneItem.getRegistryEntry().isIn(MagicTreasuresTags.Items.STONES);
            }
            return false;
        }).orElse(false);
    }

    public static Optional<Double> manaValue(ItemStack stack) {
        return mana(stack).map((ManaComponent::mana));
    }

    /*
     * component setters
     */
    public static ManaComponent setMana(ItemStack stack, double max, double mana) {
        return stack.set(MagicTreasuresComponents.MANA, new ManaComponent(max, mana));
    }

    /*
     * component updaters
     * updaters assume the item stack already contains the component.
     * if it doesn't, no action is taken.
     */
    // mana
    public static Optional<ManaComponent> updateMana(ItemStack stack, double mana) {
        return mana(stack).map(component -> stack.set(MagicTreasuresComponents.MANA, new ManaComponent(component.maxMana(), mana)));
    }

    /*
     * component copiers - necessary?
     */
    public static void copyMana(ItemStack source, ItemStack dest) {
        dest.set(MagicTreasuresComponents.MANA, source.get(MagicTreasuresComponents.MANA));
    }
}
