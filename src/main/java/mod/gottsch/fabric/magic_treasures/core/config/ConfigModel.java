package mod.gottsch.fabric.magic_treasures.core.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

/**
 * Created by Mark Gottschling on Nov 11, 2024
 */
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Modmenu(modId = "magictreasures")
@Config(name = "magictreasures", wrapperName = "MyConfig")
public class ConfigModel {
    @SectionHeader("lootProperties")

    @Nest
    public Loot loot = new Loot();
    public static class Loot {
        @RestartRequired
        public boolean enableVanillaLootModifiers = true;
        @RestartRequired
        public boolean enableGeneralEntityLootModifier = true;
        @RestartRequired
        public boolean enableZombieEntityLootModifier = true;
        @RestartRequired
        public boolean enableSkeletonEntityLootModifier = true;
        @RestartRequired
        public boolean enableWitherSkeletonEntityLootModifier = true;
        @RestartRequired
        public boolean enableScarceEntityLootModifier = true;
        @RestartRequired
        public boolean enableRareEntityLootModifier = true;
        @RestartRequired
        public boolean enableEpicEntityLootModifier = true;

        public boolean enableUncommonChestLootModifier = true;
        public boolean enableScarceChestLootModifier = true;
        public boolean enableRareChestLootModifier = true;
        public boolean enableEpicChestLootModifier = true;
        public boolean enableLegendaryChestLootModifier = true;
        public boolean enableMythicalChestLootModifier = true;

        public boolean enableFishingJunkLootModifier = true;
        public boolean enableFishingTreasureLootModifier = true;
    }
}
