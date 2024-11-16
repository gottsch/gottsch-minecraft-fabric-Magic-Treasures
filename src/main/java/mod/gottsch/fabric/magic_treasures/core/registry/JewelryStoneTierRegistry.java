package mod.gottsch.fabric.magic_treasures.core.registry;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author Mark Gottschling May 9, 2024
 *
 */
public class JewelryStoneTierRegistry {
    private static final Map<Identifier, JewelryStoneTier> REGISTRY = new HashMap<>();

    public static Optional<JewelryStoneTier> register(JewelryStoneTier tier) {
        if (!REGISTRY.containsKey(tier.getName())) {
            REGISTRY.put(tier.getName(), tier);
            return Optional.of(tier);
        }
        return Optional.empty();
    }

    public static boolean has(Identifier name) {
        return REGISTRY.containsKey(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public static Optional<JewelryStoneTier> get(Identifier name) {

        if (name != null && REGISTRY.containsKey(name)) {
            return Optional.of(REGISTRY.get(name));
        }
        return Optional.empty();
    }

    /**
     *
     * @return
     */
    public static List<Identifier> getNames() {
        return Lists.newArrayList(REGISTRY.keySet());
    }

    public static List<JewelryStoneTier> getStoneTiers() {
        return Lists.newArrayList(REGISTRY.values());
    }
}
