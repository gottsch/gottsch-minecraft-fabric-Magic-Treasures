package mod.gottsch.fabric.magic_treasures.core.spell.cost;

import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.Jewelry;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryVitalsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTiers;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.spell.ICastSpellContext;
import mod.gottsch.forge.gottschcore.spatial.ICoords;
import mod.gottsch.forge.magic_treasures.MagicTreasures;
import mod.gottsch.forge.magic_treasures.core.capability.IJewelryHandler;
import mod.gottsch.forge.magic_treasures.core.capability.MagicTreasuresCapabilities;
import mod.gottsch.forge.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.forge.magic_treasures.core.jewelry.JewelryStoneTiers;
import mod.gottsch.forge.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.forge.magic_treasures.core.spell.ICastSpellContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.World;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.Random;

/*
 * Generic cost evaluator
 * @param amount cost requested
 * @return actual cost incurred
 */
public class CostEvaluator implements ICostEvaluator {
	@Override
	public double apply(World level, Random random, ICoords coords, ICastSpellContext context, double amount) {
//		IJewelryHandler handler = context.getJewelry().getCapability(MagicTreasuresCapabilities.JEWELRY_CAPABILITY).orElseThrow(IllegalStateException::new);

		Jewelry item = (Jewelry)context.getJewelry().getItem();
		JewelryVitalsComponent vitals = Jewelry.vitals(context.getJewelry()).orElseThrow(IllegalStateException::new); //context.getJewelry().get(MagicTreasuresComponents.JEWELRY_VITALS_COMPONENT)
		Optional<Item> stone = StoneRegistry.get(item.getGemstone());
		JewelryStoneTier stoneTier = StoneRegistry.getStoneTier(stone.orElseGet(() -> Items.AIR)).orElse(JewelryStoneTiers.NONE);

		// calculate the new amount for cost
		double newAmount = amount * item.getMaterial().getSpellCostFactor()
				* stoneTier.getSpellCostFactor();

		double cost = 0;
		if (vitals.mana() >= newAmount) {
			cost = newAmount;
			handler.setMana(MathHelper.clamp(handler.getMana() - newAmount, 0D, handler.getMana()));
		}
		else {
			cost = handler.getMana();
			handler.setMana(0);
		}
		return cost;
	}
}
