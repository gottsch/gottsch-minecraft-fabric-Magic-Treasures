package mod.gottsch.fabric.magic_treasures.datagen;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static net.fabricmc.fabric.api.biome.v1.BiomeSelectors.tag;

/**
 * Created by Mark Gottschling on Nov 14, 2024
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        try {
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONES)
                    .add(MagicTreasuresItems.TOPAZ)
                    .add(MagicTreasuresItems.ONYX)
                    .add(Items.DIAMOND)
                    .add(MagicTreasuresItems.JADEITE)
                    .add(MagicTreasuresItems.RUBY)
                    .add(MagicTreasuresItems.SAPPHIRE)
                    .add(MagicTreasuresItems.WHITE_PEARL)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER1)
                    .add(MagicTreasuresItems.TOPAZ);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER2)
                    .add(MagicTreasuresItems.ONYX);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER3)
                    .add(Items.DIAMOND);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER4)
                    .add(MagicTreasuresItems.JADEITE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER5)
                    .add(MagicTreasuresItems.RUBY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER6)
                    .add(MagicTreasuresItems.SAPPHIRE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER5)
                    .add(MagicTreasuresItems.WHITE_PEARL);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_TIER6)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_COMMON).add(MagicTreasuresItems.TOPAZ);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_UNCOMMON).add(MagicTreasuresItems.ONYX);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_SCARCE)
                    .add(Items.DIAMOND)
                    .add(MagicTreasuresItems.JADEITE);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_RARE)
                    .add(MagicTreasuresItems.RUBY)
                    .add(MagicTreasuresItems.WHITE_PEARL);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.STONE_RARITY_EPIC)
                    .add(MagicTreasuresItems.SAPPHIRE)
                    .add(MagicTreasuresItems.BLACK_PEARL);

            // jewelry
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).add(MagicTreasuresItems.GOLD_RING);

            // spell scrolls
            Registries.ITEM.forEach(o -> {
                if (o instanceof SpellScroll) {
                    getOrCreateTagBuilder(MagicTreasuresTags.Items.SPELL_SCROLLS).add(o);
                }
            });

        } catch(Exception e) {
            MagicTreasures.LOGGER.error("error", e);
        }
//                .addOptional(MagicTreasuresItems.TOPAZ.getRegistryEntry().registryKey());
//                .addOptional(identifier(MagicTreasuresItems.ONYX));
    }

    public final Identifier identifier(Item item) {
        return Registries.ITEM.getId(item);
    }
}
