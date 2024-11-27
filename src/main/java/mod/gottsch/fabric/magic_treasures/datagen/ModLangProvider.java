package mod.gottsch.fabric.magic_treasures.datagen;

import mod.gottsch.fabric.magic_treasures.core.block.MagicTreasuresBlocks;
import mod.gottsch.fabric.magic_treasures.core.item.MagicTreasuresItems;
import mod.gottsch.fabric.magic_treasures.core.item.ModItemGroups;
import mod.gottsch.fabric.magic_treasures.core.item.SpellScroll;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class ModLangProvider extends FabricLanguageProvider {

    public ModLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        // ores
        translationBuilder.add(MagicTreasuresBlocks.TOPAZ_ORE, "Topaz Ore");
        translationBuilder.add(MagicTreasuresBlocks.ONYX_ORE, "Onyx Ore");
        translationBuilder.add(MagicTreasuresBlocks.JADEITE_ORE, "Jadeite Ore");
        translationBuilder.add(MagicTreasuresBlocks.RUBY_ORE, "Ruby Ore");
        translationBuilder.add(MagicTreasuresBlocks.SAPPHIRE_ORE, "Sapphire Ore");
        translationBuilder.add(MagicTreasuresBlocks.SILVER_ORE, "Silver Ore");

        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_TOPAZ_ORE, "Deepslate Topaz Ore");
        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_ONYX_ORE, "Deepslate Onyx Ore");
        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_JADEITE_ORE, "Deepslate Jadeite Ore");
        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_RUBY_ORE, "Deepslate Ruby Ore");
        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_SAPPHIRE_ORE, "Deepslate Sapphire Ore");
        translationBuilder.add(MagicTreasuresBlocks.DEEPSLATE_SILVER_ORE, "Deepslate Silver Ore");

        // gems
        translationBuilder.add(MagicTreasuresItems.TOPAZ, "Topaz");
        translationBuilder.add(MagicTreasuresItems.ONYX, "Onyx");
        translationBuilder.add(MagicTreasuresItems.JADEITE, "Jadeite");
        translationBuilder.add(MagicTreasuresItems.RUBY, "Ruby");
        translationBuilder.add(MagicTreasuresItems.SAPPHIRE, "Sapphire");
        translationBuilder.add(MagicTreasuresItems.WHITE_PEARL, "White Pearl");
        translationBuilder.add(MagicTreasuresItems.BLACK_PEARL, "Black Pearl");

        Registries.ITEM_GROUP.getKey(ModItemGroups.MAGIC_TREASURES_ITEMS_GROUP).ifPresent(key -> {
            translationBuilder.add(key, "Magic Treasures");
        });

        // regular jewelry
        MagicTreasuresItems.STANDARD_JEWELRY.forEach(item -> {
            translationBuilder.add(item, WordUtils.capitalizeFully(name(item).replace("_", " ")));
        });

        // special jewelry
        translationBuilder.add(MagicTreasuresItems.SILBROS_RING_OF_VITALITY, WordUtils.capitalizeFully("Silbro's Ring of Vitality"));
        translationBuilder.add(MagicTreasuresItems.STRONGMANS_BRACERS, WordUtils.capitalizeFully("Strongman's Bracers"));
        translationBuilder.add(MagicTreasuresItems.MALDRITCHS_FIRST_AMULET, WordUtils.capitalizeFully("Maldritch's First Amulet"));

        translationBuilder.add(MagicTreasuresItems.PEASANTS_FORTUNE, WordUtils.capitalizeFully("Peasant's Fortune"));
        translationBuilder.add(MagicTreasuresItems.AQUA_RING, WordUtils.capitalizeFully("Aqua Ring"));
        translationBuilder.add(MagicTreasuresItems.AMULET_OF_DEFENCE, WordUtils.capitalizeFully("Amulet of Defence"));
        translationBuilder.add(MagicTreasuresItems.JOURNEYMANS_BANDS, WordUtils.capitalizeFully("Journeyman's Bands"));

        translationBuilder.add(MagicTreasuresItems.MEDICS_TOKEN, WordUtils.capitalizeFully("Medic's Token"));
        translationBuilder.add(MagicTreasuresItems.ADEPHAGIAS_BOUNTY, WordUtils.capitalizeFully("Adephagia's Bounty"));
        translationBuilder.add(MagicTreasuresItems.ANGELS_RING, WordUtils.capitalizeFully("Ring of Angels"));
        translationBuilder.add(MagicTreasuresItems.SALANDAARS_WARD, WordUtils.capitalizeFully("Sal'andaar's Ward"));
        translationBuilder.add(MagicTreasuresItems.RING_OF_FORTITUDE, WordUtils.capitalizeFully("Ring of Fortitude"));
        translationBuilder.add(MagicTreasuresItems.EYE_OF_THE_PHOENIX, WordUtils.capitalizeFully("Eye of the Phoenix"));
        translationBuilder.add(MagicTreasuresItems.RING_LIFE_DEATH, WordUtils.capitalizeFully("Ring of Life and Death"));

        // scrolls
        Registries.ITEM.forEach(o -> {
            if (o instanceof SpellScroll) {
                translationBuilder.add(o, WordUtils.capitalizeFully("Scroll of " + ((SpellScroll) o).getSpell().getName().getPath().replace("_", " ")));
            }
        });

        /*
         *  Util.tooltips
         */
        // general
        translationBuilder.add(LangUtil.tooltip("boolean.yes"), "Yes");
        translationBuilder.add(LangUtil.tooltip("boolean.no"), "No");
        translationBuilder.add(LangUtil.tooltip("infinite"), "Infinite");
        translationBuilder.add(LangUtil.tooltip("hold_shift"), "Hold [SHIFT] to expand");
        translationBuilder.add(LangUtil.tooltip("divider"), "--------------------");

        // tools
        translationBuilder.add(LangUtil.tooltip("tools.jewelry_pliers"), "Required for removing gemstones from jewelry.");

        // gemstones
        translationBuilder.add(LangUtil.tooltip("gemstone.usage"), "Place on an anvil with Magic Treasures jewelry to combine.");
        translationBuilder. add(LangUtil.tooltip("gemstone.recharge.usage"), "Place on an anvil with Magic Treasures jewelry to recharge.");
        translationBuilder.add(LangUtil.tooltip("gemstone.rarity"), "Rarity: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.tier"), "Tier: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.mana"), "Mana: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.recharges"), "Recharges: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.cost_factor"), "Mana Cost: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.cooldown_factor"), "Cooldown Time: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.effect_amount_factor"), "Effect Amount: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.frequency_factor"), "Frequency: %s");
        translationBuilder.add(LangUtil.tooltip("gemstone.range_factor"), "Range: %s");

// jewelry
        translationBuilder.add(LangUtil.tooltip("jewelry.usage"), "Hold in hand or equip for buffs.");
        translationBuilder.add(LangUtil.tooltip("jewelry.material"), "Material: %s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stone"), "Stone: %s");

//        translationBuilder.add(LangUtil.tooltip("jewelry.durability"), "Durability: ");
//        translationBuilder.add(LangUtil.tooltip("jewelry.durability.gauge"), "[%s/%s]");
        translationBuilder.add(LangUtil.tooltip("jewelry.durability.amount"), "Durability: [%s/%s]");
        translationBuilder.add(LangUtil.tooltip("jewelry.durability.infinite"), "Durability: -%s-");
        translationBuilder.add(LangUtil.tooltip("jewelry.durability.repairs"), "Repairs: %s");

        translationBuilder.add(LangUtil.tooltip("jewelry.max_level"), "Spell Max. Level: %s");
        translationBuilder.add(LangUtil.tooltip("jewelry.mana"), "Mana: [%s/%s]");
        translationBuilder.add(LangUtil.tooltip("jewelry.mana.recharges"), "Recharges: %s");
        translationBuilder.add(LangUtil.tooltip("jewelry.mana.gauge"), "[%s/%s]");
        translationBuilder.add(LangUtil.tooltip("jewelry.spells"), "Spells:");

        translationBuilder.add(LangUtil.tooltip("jewelry.stats.cost_factor"), "C:%s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stats.cooldown_factor"), "Cd:%s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stats.duration_factor"), "D:%s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stats.effect_amount_factor"), "E:%s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stats.frequency_factor"), "Fq:%s");
        translationBuilder.add(LangUtil.tooltip("jewelry.stats.range_factor"), "R:%s");

        /*
         * specific jewelry
         */
        // lore
        translationBuilder.add(LangUtil.tooltip("jewelry.castle_ring.lore"), "Castle rings contain an abundance of mana and durability.~Can only be affixed with Rubies & Sapphires.");
        translationBuilder.add(LangUtil.tooltip("jewelry.hawk_ring.lore"), "Hawk rings contain an abundance of mana and a higher max spell level.~Cannot be affixed with any gems.");
        translationBuilder.add(LangUtil.tooltip("jewelry.silbros_ring_of_vitality.lore"), "Silbro was a master Naturalist mage.~He was able to give organic jewelry the power of metals.");
        translationBuilder.add(LangUtil.tooltip("jewelry.maldritchs_first_amulet.lore"), "Maldritch is a powerful lich, but he wasn't always one.~This is his first amulet when he was a mere apprentice.");

        /*
         * spells
         */

        // spell scroll stats
        translationBuilder.add(LangUtil.tooltip("spell.name"), "Name: %s");
        translationBuilder.add(LangUtil.tooltip("spell.level"), "Level: %s");
        translationBuilder.add(LangUtil.tooltip("spell.rarity"), "Rarity: %s");
        translationBuilder.add(LangUtil.tooltip("spell.cost"), "Cost: %s");
        translationBuilder.add(LangUtil.tooltip("spell.cost.varies"), "Cost: Varies");
        translationBuilder.add(LangUtil.tooltip("spell.effect_amount"), "Effect Amount: %s");
        translationBuilder.add(LangUtil.tooltip("spell.cooldown"), "Cooldown Time: %s seconds.");
        translationBuilder.add(LangUtil.tooltip("spell.frequency"), "Frequency: Every %s seconds.");
        translationBuilder.add(LangUtil.tooltip("spell.range"), "Range: %s blocks.");

        ///// healing /////
        translationBuilder.add(LangUtil.tooltip("spell.name.lesser_healing"), "Lesser Healing");
        translationBuilder.add(LangUtil.tooltip("spell.name.healing"), "Healing");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_healing"), "Greater Healing");
        translationBuilder.add(LangUtil.tooltip("spell.name.regeneration"), "Regeneration");

        translationBuilder.add(LangUtil.tooltip("spell.healing.rate"), "Heals %s hp every %s seconds");

        ///// mana shield /////
        translationBuilder.add(LangUtil.tooltip("spell.name.mana_buckler"), "Mana Buckler");
        translationBuilder.add(LangUtil.tooltip("spell.name.mana_shield"), "Mana Shield");
        translationBuilder.add(LangUtil.tooltip("spell.name.mana_tower_shield"), "Mana Tower Shield");
        translationBuilder.add(LangUtil.tooltip("spell.name.mana_pavise_shield"), "Mana Pavise Shield");

        translationBuilder.add(LangUtil.tooltip("spell.mana_shield.rate"), "Absorbs %s damage.");

        ///// spectral armor /////
        translationBuilder.add(LangUtil.tooltip("spell.name.ghostly_armor"), "Ghostly Armor");
        translationBuilder.add(LangUtil.tooltip("spell.name.spectral_armor"), "Spectral Armor");
        translationBuilder.add(LangUtil.tooltip("spell.name.shadow_armor"), "Shadow Armor");
        translationBuilder.add(LangUtil.tooltip("spell.name.disembodied_armor"), "Disembodied Armor");

        translationBuilder.add(LangUtil.tooltip("spell.spectral_armor.rate"), "Reduces %s mob damage.");

        ///// drain /////
        translationBuilder.add(LangUtil.tooltip("spell.name.drain"), "Drain");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_drain"), "Greater Drain");
        translationBuilder.add(LangUtil.tooltip("spell.name.desiccate"), "Desiccate");

        translationBuilder.add(LangUtil.tooltip("spell.drain.rate"), "Drains %s hp from mobs within %s blocks every %s seconds.");

        ///// fire resistance /////
        translationBuilder.add(LangUtil.tooltip("spell.name.fire_resistance"), "Fire Resistance");
        translationBuilder.add(LangUtil.tooltip("spell.name.fire_ward"), "Fire Ward");
        translationBuilder.add(LangUtil.tooltip("spell.name.blessing_of_the_phoenix"), "Blessing of the Phoenix");

        translationBuilder.add(LangUtil.tooltip("spell.fire_resistance.rate"), "Resists %s fire damage.");

        ///// magic resistance /////
        translationBuilder.add(LangUtil.tooltip("spell.name.magic_resistance"), "Magic Resistance");
        translationBuilder.add(LangUtil.tooltip("spell.name.magic_ward"), "Magic Ward");
        translationBuilder.add(LangUtil.tooltip("spell.name.salandaars_magic_coat"), "Sal'andaar's Magic Coat");

        translationBuilder.add(LangUtil.tooltip("spell.magic_resistance.rate"), "Resists %s magic damage.");

        ///// wither resistance /////
        translationBuilder.add(LangUtil.tooltip("spell.name.wither_resistance"), "Wither Resistance");
        translationBuilder.add(LangUtil.tooltip("spell.name.wither_ward"), "Wither Ward");
        translationBuilder.add(LangUtil.tooltip("spell.name.withers_skin"), "Wither's Skin");

        translationBuilder.add(LangUtil.tooltip("spell.wither_resistance.rate"), "Resists %s wither damage.");

        ///// reflection /////
        translationBuilder.add(LangUtil.tooltip("spell.name.reflection"), "Reflection");
        translationBuilder.add(LangUtil.tooltip("spell.name.crushing_response"), "Crushing Response");

        translationBuilder.add(LangUtil.tooltip("spell.reflection.rate"), "Reflects %s damage back onto mob.");

        ///// paladin strike /////
        translationBuilder.add(LangUtil.tooltip("spell.name.paladin_strike"), "Paladin Strike");
        translationBuilder.add(LangUtil.tooltip("spell.name.paladin_smite"), "Paladin Smite");

        translationBuilder.add(LangUtil.tooltip("spell.paladin_strike.rate"), "Inflicts extra %s hp every %s seconds costing %s mana and %s hp.");

        ///// satiety /////
        translationBuilder.add(LangUtil.tooltip("spell.name.satiety"), "Satiety");
        translationBuilder.add(LangUtil.tooltip("spell.satiety.rate"), "Restores 0.5 hunger every %s seconds.");

        ///// cheat death /////
        translationBuilder.add(LangUtil.tooltip("spell.name.cheat_death"), "Cheat Death");
        translationBuilder.add(LangUtil.tooltip("spell.cheat_death.rate"), "Prevents death. Cooldown: %s seconds.");

        ///// strength /////
        translationBuilder.add(LangUtil.tooltip("spell.name.quick_strength"), "Quick Strength");
        translationBuilder.add(LangUtil.tooltip("spell.name.strength"), "Strength");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_strength"), "Greater Strength");
        translationBuilder.add(LangUtil.tooltip("spell.name.giant_strength"), "Giant Strength");
        translationBuilder.add(LangUtil.tooltip("spell.strength.rate"), "Bestows Strength effect for %s seconds. Cooldown: %s seconds.");

        ///// speed /////
        translationBuilder.add(LangUtil.tooltip("spell.name.speed"), "Speed");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_speed"), "Greater Speed");
        translationBuilder.add(LangUtil.tooltip("spell.name.horse_power"), "Horse Power");
        translationBuilder.add(LangUtil.tooltip("spell.speed.rate"), "Bestows Speed effect for %s seconds. Cooldown: %s seconds.");

        ///// night vision /////
        translationBuilder.add(LangUtil.tooltip("spell.name.night_vision"), "Night Vision");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_night_vision"), "Greater Night Vision");
        translationBuilder.add(LangUtil.tooltip("spell.name.cat_sight"), "Cat Sight");
        translationBuilder.add(LangUtil.tooltip("spell.night_vision.rate"), "Bestows Night Vision effect for %s seconds. Cooldown: %s seconds.");

        ///// invisibility /////
        translationBuilder.add(LangUtil.tooltip("spell.name.invisibility"), "Invisibility");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_invisibility"), "Greater Invisibility");
        translationBuilder.add(LangUtil.tooltip("spell.invisibility.rate"), "Bestows Invisibility effect for %s seconds. Cooldown: %s seconds.");

        ///// water-breathing /////
        translationBuilder.add(LangUtil.tooltip("spell.name.water_breathing"), "Water Breathing");
        translationBuilder.add(LangUtil.tooltip("spell.name.greater_water_breathing"), "Greater Water Breathing");
        translationBuilder.add(LangUtil.tooltip("spell.water_breathing.rate"), "Bestows Water Breathing effect for %s seconds. Cooldown: %s seconds.");

        ///// harm /////
        translationBuilder.add(LangUtil.tooltip("spell.name.harm"), "Harm");
        translationBuilder.add(LangUtil.tooltip("spell.name.mind_jab"), "Mind Jab");
        translationBuilder.add(LangUtil.tooltip("spell.name.mind_fist"), "Mind Fist");
        translationBuilder.add(LangUtil.tooltip("spell.harm.rate"), "Inflicts %s hp to a single mob within %s blocks. Cooldown: %s seconds.");


        // scrolls
        translationBuilder.add(LangUtil.tooltip("spell_scroll.usage"), "Place on an anvil with Magic Treasures jewelry to combine.");

        translationBuilder.add(LangUtil.tooltip("jewelry_recipe_scroll.usage"), "Combine with 4 ingots of respective material on crafting table to craft jewelry item.");

    }

    public final String name(Item item) {
        return Registries.ITEM.getId(item).getPath();
    }
}
