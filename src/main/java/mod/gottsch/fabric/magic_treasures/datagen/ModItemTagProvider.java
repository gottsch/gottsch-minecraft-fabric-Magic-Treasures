package mod.gottsch.fabric.magic_treasures.datagen;

import com.google.common.collect.Maps;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.JewelryType;
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
import java.util.Map;
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
        /*
         * map from jewelry type attribute to tag
         */
        Map<IJewelryType, TagKey<Item>> TYPE_TAG_MAP = Maps.newHashMap();
        TYPE_TAG_MAP.put(JewelryType.RING, MagicTreasuresTags.Items.RINGS);
        TYPE_TAG_MAP.put(JewelryType.BRACELET, MagicTreasuresTags.Items.BRACELETS);
//		TYPE_TAG_MAP.put(JewelryType.BROACH, GealdorCraftTags.Items.BROACHES);
//		TYPE_TAG_MAP.put(JewelryType.CHARM, MagicTreasuresTags.Items.CHARMS);
//		TYPE_TAG_MAP.put(JewelryType.EARRING, GealdorCraftTags.Items.EARRINGS);
        TYPE_TAG_MAP.put(JewelryType.NECKLACE, MagicTreasuresTags.Items.NECKLACES);
        TYPE_TAG_MAP.put(JewelryType.POCKET, MagicTreasuresTags.Items.POCKETS);


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
            // TODO change to get all jewelry items
//            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).add(MagicTreasuresItems.GOLD_RING);

            // special jewelry tags
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CASTLE_RING_RUBY).add(MagicTreasuresItems.RUBY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.CASTLE_RING_SAPPHIRE).add(MagicTreasuresItems.SAPPHIRE);


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
