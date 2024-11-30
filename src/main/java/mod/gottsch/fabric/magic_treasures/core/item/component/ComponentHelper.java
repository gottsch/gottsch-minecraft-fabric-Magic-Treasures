package mod.gottsch.fabric.magic_treasures.core.item.component;

import mod.gottsch.fabric.magic_treasures.core.api.MagicTreasuresApi;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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

    public static Optional<Identifier> gemstone(ItemStack stack) {
        return attribs(stack).map(JewelryAttribsComponent::gemstone);
    }
    public static Optional<Item> gemstoneItem(ItemStack stack) {
        return attribs(stack).flatMap(a -> StoneRegistry.get(a.gemstone()));
    }
    // datagen method
    public static Optional<Item> gemstoneMinecraftItem(ItemStack stack) {
        return attribs(stack).map((a -> Registries.ITEM.get(a.gemstone())));
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
    public static double manaValueOrDefault(ItemStack stack, double defaultValue) {
        return mana(stack).map((ManaComponent::mana)).orElse(defaultValue);
    }
    public static Optional<Double> maxManaValue(ItemStack stack) {
        return mana(stack).map((ManaComponent::maxMana));
    }

    public static Optional<Integer> repairsValue(ItemStack stack) {
        return repairs(stack).map((RepairsComponent::repairs));
    }

    public static Optional<Integer> rechargesValue(ItemStack stack) {
        return recharges(stack).map((RechargesComponent::recharges));
    }
    public static int rechargesValueOrDefault(ItemStack stack, int defaultValue) {
        return recharges(stack).map((RechargesComponent::recharges)).orElse(defaultValue);
    }
    public static Optional<Integer> maxRechargesValue(ItemStack stack) {
        return recharges(stack).map((RechargesComponent::maxRecharges));
    }

    /*
     * component setters
     */
    public static ManaComponent setMana(ItemStack stack, double max, double mana) {
        return stack.set(MagicTreasuresComponents.MANA, new ManaComponent(max, mana));
    }

    public static RechargesComponent setRecharges(ItemStack stack, int max, int recharges) {
        return stack.set(MagicTreasuresComponents.RECHARGES, new RechargesComponent(max, recharges));
    }

    public static JewelryAttribsComponent setJewelryAttribs(ItemStack stack , IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, Item gemstone) {
        return stack.set(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                new JewelryAttribsComponent(type.getName(), sizeTier.getName(), material.getId(), gemstone.getRegistryEntry().registryKey().getValue()));
    }

    public static JewelryAttribsComponent setJewelryAttribs(ItemStack stack , String type, String sizeTier, Identifier material, Identifier gemstone) {
        return stack.set(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                new JewelryAttribsComponent(type, sizeTier, material, gemstone));
    }

    /*
     * component updaters
     * updaters assume the item stack already contains the component.
     * if it doesn't, no action is taken.
     */
    // uses
    public static Optional<UsesComponent> updateUses(ItemStack stack, int uses) {
        return uses(stack).map(component -> stack.set(MagicTreasuresComponents.USES, new UsesComponent(component.maxUses(), uses)));
    }

    // repairs
    public static Optional<RepairsComponent> updateRepairs(ItemStack stack, int repairs) {
        return repairs(stack).map(component -> stack.set(MagicTreasuresComponents.REPAIRS, new RepairsComponent(component.maxRepairs(), repairs)));
    }

    // mana
    public static Optional<ManaComponent> updateMana(ItemStack stack, double mana) {
        return mana(stack).map(component -> stack.set(MagicTreasuresComponents.MANA, new ManaComponent(component.maxMana(), mana)));
    }
    public static Optional<ManaComponent> incrementMana(ItemStack stack, double amount) {
        return mana(stack).map(
                component -> stack.set(MagicTreasuresComponents.MANA,
                new ManaComponent(component.maxMana() + amount, component.mana() + amount)));
    }

    // recharges
    public static Optional<RechargesComponent> updateRecharges(ItemStack stack, int recharges) {
        return recharges(stack).map(component -> stack.set(MagicTreasuresComponents.RECHARGES, new RechargesComponent(component.maxRecharges(), recharges)));
    }
    public static Optional<RechargesComponent> incrementRecharges(ItemStack stack, int amount) {
        return recharges(stack).map(component -> stack.set(MagicTreasuresComponents.RECHARGES,
                new RechargesComponent(component.maxRecharges() + amount, component.recharges() + amount)));
    }

    // gemstone
    public static Optional<JewelryAttribsComponent> updateGemstone(ItemStack stack, Identifier gemstone) {
        return attribs(stack).map(component -> stack.set(MagicTreasuresComponents.JEWELRY_ATTRIBS,
                new JewelryAttribsComponent(component.type(), component.sizeTier(), component.material(), gemstone)));
    }

    /*
     * component copiers
     */
    public static void copyUsesComponent(ItemStack source, ItemStack dest) {
        dest.set(MagicTreasuresComponents.USES, source.get(MagicTreasuresComponents.USES));
    }
    public static void copyRepairsComponent(ItemStack source, ItemStack dest) {
        dest.set(MagicTreasuresComponents.REPAIRS, source.get(MagicTreasuresComponents.REPAIRS));
    }
    public static void copyManaComponent(ItemStack source, ItemStack dest) {
        dest.set(MagicTreasuresComponents.MANA, source.get(MagicTreasuresComponents.MANA));
    }
    public static void copySpellsComponent(ItemStack source, ItemStack dest) {
        dest.set(MagicTreasuresComponents.SPELLS, source.get(MagicTreasuresComponents.SPELLS));
    }

    /*
     * component property copiers
     */
    // uses
    public static Optional<UsesComponent> copyUses(ItemStack source, ItemStack dest) {
        return uses(dest).map(destComponent -> dest.set(MagicTreasuresComponents.USES,
                new UsesComponent(destComponent.maxUses(), uses(source).map(UsesComponent::uses).orElse(destComponent.uses()))
            )
        );
    }
}
