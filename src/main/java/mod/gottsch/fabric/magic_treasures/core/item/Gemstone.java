package mod.gottsch.fabric.magic_treasures.core.item;

import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import mod.gottsch.fabric.magic_treasures.core.util.LangUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Mark Gottschling on Nov 14, 2024 (fabric version)
 */
public class Gemstone extends Item {

    public Gemstone(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((MutableText)super.getName(stack)).formatted(getNameColor());
    }

    public Formatting getNameColor() {
        return Formatting.WHITE;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        addHoverText(stack, context, tooltip, type);
    }

    public static void addHoverText(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(LangUtil.tooltip("gemstone.usage")).formatted(Formatting.GOLD).formatted(Formatting.ITALIC));
        tooltip.add(Text.translatable(LangUtil.NEWLINE));

        // TODO all this needs to be added to TooltipEvent
        LangUtil.appendAdvancedHoverText(tooltip, tt -> {

            StoneRegistry.getStoneTier(itemStack.getItem()).ifPresent(tier -> {
//                tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("divider")).formatted(Formatting.GRAY)));
//                tt.add(Text.translatable(LangUtil.NEWLINE));

                StoneRegistry.getRarity(itemStack.getItem()).ifPresent(r -> {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.rarity"), Formatting.GOLD + WordUtils.capitalizeFully(r.getName()))));
                });
//                tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.tier"), Formatting.GOLD + WordUtils.capitalizeFully(tier.getName().getPath()))));
                tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.mana"), Formatting.BLUE + String.valueOf(tier.getMana()))));
                tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.recharges"), Formatting.GREEN + String.valueOf(tier.getRecharges()))));

                if (tier.getSpellCostFactor() < 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.cost_factor"), Formatting.AQUA + LangUtil.negativePercent(tier.getSpellCostFactor()))));
                } else if (tier.getSpellCostFactor() > 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.cost_factor"), Formatting.AQUA + LangUtil.positivePercent(tier.getSpellCostFactor()))));
                }

                if (tier.getSpellCooldownFactor() < 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.cooldown_factor"), Formatting.AQUA + LangUtil.negativePercent(tier.getSpellCooldownFactor()))));
                } else if (tier.getSpellCooldownFactor() > 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.cooldown_factor"), Formatting.AQUA + LangUtil.positivePercent(tier.getSpellCooldownFactor()))));
                }

                if (tier.getSpellEffectAmountFactor() < 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.effect_amount_factor"), Formatting.AQUA + LangUtil.negativePercent(tier.getSpellEffectAmountFactor()))));
                } else if (tier.getSpellEffectAmountFactor() > 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.effect_amount_factor"), Formatting.AQUA + LangUtil.positivePercent(tier.getSpellEffectAmountFactor()))));
                }

                // frequency
                // NOTE the sign changes, is different than the others
                double ff = 1.0 + (1.0 - tier.getSpellFrequencyFactor());
                if (ff < 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.frequency_factor"), Formatting.AQUA + LangUtil.negativePercent(ff))));
                } else if (ff > 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.frequency_factor"), Formatting.AQUA + LangUtil.positivePercent(ff))));
                }

                // range
                if (tier.getSpellRangeFactor() < 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.range_factor"), Formatting.AQUA + LangUtil.negativePercent(tier.getSpellRangeFactor()))));
                } else if (tier.getSpellRangeFactor() > 1.0) {
                    tt.add(Text.translatable(LangUtil.INDENT2).append(Text.translatable(LangUtil.tooltip("gemstone.range_factor"), Formatting.AQUA + LangUtil.positivePercent(tier.getSpellRangeFactor()))));
                }

                tt.add(Text.translatable(LangUtil.NEWLINE));
            });
        });
    }
}
