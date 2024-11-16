package mod.gottsch.fabric.magic_treasures;

import mod.gottsch.fabric.magic_treasures.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MagicTreasuresDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		// TODO loot table provider is messing things up - ie does not gen, everything is erased
//		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
//		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModLangProvider::new);
	}
}
