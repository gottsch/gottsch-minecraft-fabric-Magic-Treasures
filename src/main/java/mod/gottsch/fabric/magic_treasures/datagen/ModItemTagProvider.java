package mod.gottsch.fabric.magic_treasures.datagen;

import com.google.common.collect.Maps;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.component.ComponentHelper;
import mod.gottsch.fabric.magic_treasures.core.item.component.JewelryAttribsComponent;
import mod.gottsch.fabric.magic_treasures.core.item.component.MagicTreasuresComponents;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterials;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryType;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.rarity.MagicTreasuresRarity;
import mod.gottsch.fabric.magic_treasures.core.tag.MagicTreasuresTags;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

        Map<JewelryMaterial, TagKey<Item>> MATERIAL_TAG_MAP = Maps.newHashMap();
        MATERIAL_TAG_MAP.put(JewelryMaterials.WOOD, MagicTreasuresTags.Items.WOOD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.IRON, MagicTreasuresTags.Items.IRON);
        MATERIAL_TAG_MAP.put(JewelryMaterials.COPPER, MagicTreasuresTags.Items.COPPER);
        MATERIAL_TAG_MAP.put(JewelryMaterials.SILVER, MagicTreasuresTags.Items.SILVER);
        MATERIAL_TAG_MAP.put(JewelryMaterials.GOLD, MagicTreasuresTags.Items.GOLD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.BLOOD, MagicTreasuresTags.Items.BLOOD);
        MATERIAL_TAG_MAP.put(JewelryMaterials.BONE, MagicTreasuresTags.Items.BONE);
        MATERIAL_TAG_MAP.put(JewelryMaterials.SHADOW, MagicTreasuresTags.Items.SHADOW);
        MATERIAL_TAG_MAP.put(JewelryMaterials.ATIUM, MagicTreasuresTags.Items.ATIUM);

        Map<IJewelrySizeTier, TagKey<Item>> SIZE_TAG_MAP = Maps.newHashMap();
        SIZE_TAG_MAP.put(JewelrySizeTier.REGULAR, MagicTreasuresTags.Items.REGULAR);
        SIZE_TAG_MAP.put(JewelrySizeTier.GREAT, MagicTreasuresTags.Items.GREAT);
        SIZE_TAG_MAP.put(JewelrySizeTier.LORDS, MagicTreasuresTags.Items.LORDS);

        Map<IRarity, TagKey<Item>> JEWELRY_RARITY_TAG_MAP = Maps.newHashMap();
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.COMMON, MagicTreasuresTags.Items.JEWELRY_COMMON);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.UNCOMMON, MagicTreasuresTags.Items.JEWELRY_UNCOMMON);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.SCARCE, MagicTreasuresTags.Items.JEWELRY_SCARCE);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.RARE, MagicTreasuresTags.Items.JEWELRY_RARE);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.EPIC, MagicTreasuresTags.Items.JEWELRY_EPIC);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.LEGENDARY, MagicTreasuresTags.Items.JEWELRY_LEGENDARY);
        JEWELRY_RARITY_TAG_MAP.put(MagicTreasuresRarity.MYTHICAL, MagicTreasuresTags.Items.JEWELRY_MYTHICAL);

        // default stone rarity mapping
        Map<Item, IRarity> STONE_RARITY_MAP = Maps.newHashMap();
        STONE_RARITY_MAP.put(MagicTreasuresItems.TOPAZ, MagicTreasuresRarity.COMMON);
        STONE_RARITY_MAP.put(MagicTreasuresItems.ONYX, MagicTreasuresRarity.UNCOMMON);
        STONE_RARITY_MAP.put(MagicTreasuresItems.JADEITE, MagicTreasuresRarity.SCARCE);
        STONE_RARITY_MAP.put(Items.DIAMOND, MagicTreasuresRarity.SCARCE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.RUBY, MagicTreasuresRarity.RARE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.SAPPHIRE, MagicTreasuresRarity.EPIC);
        STONE_RARITY_MAP.put(MagicTreasuresItems.WHITE_PEARL, MagicTreasuresRarity.RARE);
        STONE_RARITY_MAP.put(MagicTreasuresItems.BLACK_PEARL, MagicTreasuresRarity.EPIC);

        // default material rarity mapping
        Map<JewelryMaterial, IRarity> MATERIAL_RARITY_MAP = Maps.newHashMap();
        MATERIAL_RARITY_MAP.put(JewelryMaterials.WOOD, MagicTreasuresRarity.COMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.IRON, MagicTreasuresRarity.COMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.COPPER, MagicTreasuresRarity.UNCOMMON);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.SILVER, MagicTreasuresRarity.SCARCE);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.GOLD, MagicTreasuresRarity.RARE);
        MATERIAL_RARITY_MAP.put(JewelryMaterials.BONE, MagicTreasuresRarity.SCARCE);

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
            /*
             *  process all items.
             *  if item has Jewelry capability, then categorize it into the different tags
             */
            Registries.ITEM.forEach(registryItem -> {
                ItemStack stack = new ItemStack(registryItem);
                if (stack.contains(MagicTreasuresComponents.JEWELRY_ATTRIBS)) {
                    JewelryAttribsComponent attribs = stack.get(MagicTreasuresComponents.JEWELRY_ATTRIBS);
                    // add to the type tag
                    getOrCreateTagBuilder(TYPE_TAG_MAP.get(ComponentHelper.jewelryType(stack).get())).add(registryItem);
                    // -- curios integration
//                    TagKey<Item> curiosTagKey = CURIOS_TYPE_TAG_MAP.get(handler.getJewelryType());
//                    if (curiosTagKey != null) {
//                        getOrCreateTagBuilder(curiosTagKey).add(registryItem.get());
//                    }
                    // add to the material tag
                    getOrCreateTagBuilder(MATERIAL_TAG_MAP.get(ComponentHelper.material(stack).get())).add(registryItem);
                    getOrCreateTagBuilder(SIZE_TAG_MAP.get(ComponentHelper.sizeTier(stack).get())).add(registryItem);

                    // add to the stone tag
//				if (c.hasStone()) {
//					Item stoneItem = ForgeRegistries.ITEMS.getValue(c.getStone());
//					getOrCreateTagBuilder(STONE_TAG_MAP.get(stoneItem)).add(item.get());
//				}

                    //
                    //
				/*
				 ********************************
				 calculate rarity by examining the stone and/ore material
				 ********************************
				 NOTE stone rarity takes precedence over material
				 NOTE stoneHandler, stoneTier, material do not contain any data related to Rarity.
				 Rarities are registered via tags and so, tags wouldn't be populated here yet.
				 Will have to setup hard-coded maps for the default setup that map from stone,
				 material to a rarity.
				 Ex.
				 	map.put(wood, common)
				 	...
				 	map.put(gold, rare)

				 	map2.put(topaz, common)
				 	...
				 	map2.put(ruby, rare)
				 */

                    // only run this for standard jewelry (special is handled explicitly)
                    if (!MagicTreasuresItems.STANDARD_JEWELRY.contains(registryItem)) return;

                    IRarity materialRarity = MATERIAL_RARITY_MAP.get(ComponentHelper.material(stack).get());
                    IRarity rarity = materialRarity;
                    Optional<Item> gemstone = ComponentHelper.gemstone(stack);
                    if (gemstone.isPresent() && gemstone.get() != Items.AIR) {
//				if (handler.hasStone()) { // can't use hasStone here as it uses tag which aren't loaded yet
//                        Item stoneItem = ForgeRegistries.ITEMS.getValue(handler.getStone());
                        IRarity stoneRarity = STONE_RARITY_MAP.get(gemstone);

                        if (stoneRarity != null) {
                            rarity = MagicTreasuresRarity.getByCode(Math.max(stoneRarity.getCode(), materialRarity.getCode()));

//						if (handler.getJewelrySizeTier() != JewelrySizeTier.REGULAR && rarity.getCode() < 2) {
//							rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + 1);
//						}
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).get().getCode());
                        }
//					else {
//						// check the material
//						if (handler.getJewelrySizeTier() != JewelrySizeTier.REGULAR && materialRarity.getCode() < 2) {
//							rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + 1);
//						}
//					}
                        else {
                            rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).get().getCode());
                        }
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(registryItem);
                    } else {
//					if (materialRarity.getCode() < 2) {
                        rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + ComponentHelper.sizeTier(stack).get().getCode());
//					}
//					if (handler.getJewelrySizeTier() != JewelrySizeTier.REGULAR) {
//						if (materialRarity.getCode() < 2) {
//							rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + 2);
//						} else {
//							rarity = MagicTreasuresRarity.getByCode(rarity.getCode() + 1);
//						}
//					}
                        getOrCreateTagBuilder(JEWELRY_RARITY_TAG_MAP.get(rarity)).add(registryItem);
                    }
                }
            });
            
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.SILBROS_RING_OF_VITALITY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.STRONGMANS_BRACERS);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_COMMON).add(MagicTreasuresItems.PEASANTS_FORTUNE);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.AQUA_RING);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_UNCOMMON).add(MagicTreasuresItems.AMULET_OF_DEFENCE);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.JOURNEYMANS_BANDS);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.ADEPHAGIAS_BOUNTY);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_SCARCE).add(MagicTreasuresItems.MEDICS_TOKEN);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.SALANDAARS_WARD);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.ANGELS_RING);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_RARE).add(MagicTreasuresItems.RING_OF_FORTITUDE);

            // TODO move to Legendary or mythical ?
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_EPIC).add(MagicTreasuresItems.RING_LIFE_DEATH);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY_EPIC).add(MagicTreasuresItems.EYE_OF_THE_PHOENIX);

            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addTag(MagicTreasuresTags.Items.RINGS);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addTag(MagicTreasuresTags.Items.NECKLACES);
            getOrCreateTagBuilder(MagicTreasuresTags.Items.JEWELRY).addTag(MagicTreasuresTags.Items.BRACELETS);

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
