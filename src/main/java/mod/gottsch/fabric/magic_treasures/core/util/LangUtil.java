
package mod.gottsch.fabric.magic_treasures.core.util;

import mod.gottsch.fabric.magic_treasures.MagicTreasures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * @author Mark Gottschling Jun 2, 2023
 *
 */
public class LangUtil {
	public static final String BLANK = "";
	public static final String NEWLINE = "";
	public static final String INDENT2 = "  ";
	public static final String INDENT4 = "    ";
	
	/**
	 * 
	 * @param tooltip
	 * @param consumer
	 */
	public static void appendAdvancedHoverText(String modid, List<Text> tooltip, Consumer<List<Text>> consumer) {
		if (!Screen.hasShiftDown()) {
			tooltip.add(Text.literal(NEWLINE));
			// TODO how do make this call to tooltip generic for any mod because it would require the modid
			tooltip.add(Text.translatable(tooltip(modid, "hold_shift")).formatted(Formatting.GRAY));
			tooltip.add(Text.literal(LangUtil.NEWLINE));
		}
		else {
			consumer.accept(tooltip);
		}
	}

	public static void appendHideableHoverText(String modid, List<Text> tooltip, Consumer<List<Text>> consumer) {
		if (!Screen.hasShiftDown()) {
			consumer.accept(tooltip);
		}
	}

    public static String name(String modid, String prefix, String suffix) {
    	return StringUtils.stripEnd(prefix.trim(), ".")
    			+ "."
    			+ modid
    			+ "."
    			+ StringUtils.stripStart(suffix.trim(), ".");
    }
    
    public static String item(String modid, String suffix) {
    	return name(modid, "item", suffix);
    }
    
    public static String tooltip(String modid, String suffix) {
    	return name(modid, "tooltip", suffix);
    }
    
    public static String screen(String modid, String suffix) {
    	return name(modid, "screen", suffix);
    }

	public static String chat(String modid, String suffix) {
		return name(modid, "chat", suffix);
	}
	
	/**
	 * TODO this is Magic Thing's extended methods
	 */
	public static void appendAdvancedHoverText(List<Text> tooltip, Consumer<List<Text>> consumer) {
		LangUtil.appendAdvancedHoverText(MagicTreasures.MOD_ID, tooltip, consumer);
	}

	public static void appendHideableHoverText(List<Text> tooltip, Consumer<List<Text>> consumer) {
		LangUtil.appendHideableHoverText(MagicTreasures.MOD_ID, tooltip, consumer);
	}
	
    public static String name(String prefix, String suffix) {
    	return name(MagicTreasures.MOD_ID, prefix, suffix);
    }
    
    /**
     * 
     * @param suffix
     * @return
     */
    public static String item(String suffix) {
    	return name(MagicTreasures.MOD_ID, "item", suffix);
    }
    
    public static String tooltip(String suffix) {
    	return name(MagicTreasures.MOD_ID, "tooltip", suffix);
    }
    
    public static String screen(String suffix) {
    	return name(MagicTreasures.MOD_ID, "screen", suffix);
    }

	public static String chat(String suffix) {
		return name(MagicTreasures.MOD_ID, "chat", suffix);
	}

	public static String asPercentString(double value) {
		return MathUtil.r1d(value) + "%";
	}

	public static String negativePercent(double value) {
		return "-" + MathUtil.r0d(Math.min(100, 100 - (value * 100))) + "%";
	}

	public static String
	positivePercent(double value) {
		return "+" + MathUtil.r0d(value * 100 - 100) + "%";
	}
}
