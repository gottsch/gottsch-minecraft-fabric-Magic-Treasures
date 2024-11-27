
package mod.gottsch.fabric.magic_treasures.core.registry;

import com.google.common.collect.Maps;
import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Map;

/**
 * 
 * @author Mark Gottschling Jun 8, 2023
 *
 */
public class TagRegistry {
	private static final Map<IRarity, TagKey<Item>> STONE_RARITY_TAG_KEY_REGISTRY = Maps.newHashMap();
	private static final Map<IRarity, TagKey<Item>> JEWELRY_RARITY_TAG_KEY_REGISTRY = Maps.newHashMap();
	private static final Map<IJewelryType, TagKey<Item>> JEWELRY_TYPE_TAGS_REGISTRY = Maps.newHashMap();
	private static final Map<JewelryMaterial, TagKey<Item>> JEWELRY_MATERIAL_TAGS_REGISTRY = Maps.newHashMap();
	private static final Map<IJewelrySizeTier, TagKey<Item>> JEWELRY_SIZE_TAGS_REGISTRY = Maps.newHashMap();
	private static final Map<Identifier, TagKey<Item>> JEWELRY_STONE_TAGS_REGISTRY = Maps.newHashMap();
	private static final Map<JewelryStoneTier, TagKey<Item>> STONE_TIER_TAGS_REGISTRY = Maps.newHashMap();
	
	private TagRegistry() { }

	public static void registerJewelryStoneRarity(IRarity rarity, TagKey<Item> tagKey) {
		STONE_RARITY_TAG_KEY_REGISTRY.put(rarity, tagKey);
	}

	public static void registerJewelryRarity(IRarity rarity, TagKey<Item> tagKey) {
		JEWELRY_RARITY_TAG_KEY_REGISTRY.put(rarity, tagKey);
	}

	public static void registerJewelryType(IJewelryType type, TagKey<Item> tagKey) {
		JEWELRY_TYPE_TAGS_REGISTRY.put(type, tagKey);
	}

	public static void registerJewelryMaterial(JewelryMaterial material, TagKey<Item> tagKey) {
		JEWELRY_MATERIAL_TAGS_REGISTRY.put(material, tagKey);
	}

	public static void registerJewelrySizeTier(IJewelrySizeTier sizeTier, TagKey<Item> tagKey) {
		JEWELRY_SIZE_TAGS_REGISTRY.put(sizeTier, tagKey);
	}

	public static void registerJewelryStone(Identifier stone, TagKey<Item> tagKey) {
		JEWELRY_STONE_TAGS_REGISTRY.put(stone, tagKey);
	}

	/**
	 * register a stoneTier tag to a stoneTier
	 * @param tier
	 * @param tierTag
	 */
//	@Deprecated
//	public static void registerJewelryStoneTier(IJewelryStoneTier tier, TagKey<Item> tierTag) {
////		STONE_TIER_TAGS_REGISTRY.put(tier, tierTag);
//	}

	public static void registerJewelryStoneTier(JewelryStoneTier tier, TagKey<Item> tierTag) {
		STONE_TIER_TAGS_REGISTRY.put(tier, tierTag);
	}

	public static TagKey<Item> getStoneRarityTag(IRarity rarity) {
		if (STONE_RARITY_TAG_KEY_REGISTRY.containsKey(rarity)) {
			return STONE_RARITY_TAG_KEY_REGISTRY.get(rarity);
		}
		return null;
	}

	public static TagKey<Item> getJewelryRarityTag(IRarity rarity) {
		if (JEWELRY_RARITY_TAG_KEY_REGISTRY.containsKey(rarity)) {
			return JEWELRY_RARITY_TAG_KEY_REGISTRY.get(rarity);
		}
		return null;
	}

	// TODO these need to return Optional<>
	public static TagKey<Item> getJewelryTypeTag(IJewelryType type) {
		if (JEWELRY_TYPE_TAGS_REGISTRY.containsKey(type)) {
			return JEWELRY_TYPE_TAGS_REGISTRY.get(type);
		}
		return null;
	}

	public static TagKey<Item> getJewelryMaterialTag(JewelryMaterial material) {
		if (JEWELRY_MATERIAL_TAGS_REGISTRY.containsKey(material)) {
			return JEWELRY_MATERIAL_TAGS_REGISTRY.get(material);
		}
		return null;
	}

	public static TagKey<Item> getJewelrySizeTierTag(IJewelrySizeTier sizeTier) {
		if (JEWELRY_SIZE_TAGS_REGISTRY.containsKey(sizeTier)) {
			return JEWELRY_SIZE_TAGS_REGISTRY.get(sizeTier);
		}
		return null;
	}

	public static TagKey<Item> getJewelryStoneTag(Identifier stone) {
		if (JEWELRY_STONE_TAGS_REGISTRY.containsKey(stone)) {
			return JEWELRY_STONE_TAGS_REGISTRY.get(stone);
		}
		return null;
	}

	public static TagKey<Item> getJewelryStoneTag(Item stone) {
		return getJewelryStoneTag(ModUtil.getName(stone));
	}


	public static TagKey<Item> getJewelryStoneTierTag(JewelryStoneTier tier) {
		if (STONE_TIER_TAGS_REGISTRY.containsKey(tier)) {
			return STONE_TIER_TAGS_REGISTRY.get(tier);
		}
		return null;
	}
}
