package mod.gottsch.fabric.magic_treasures.core.event;

/**
 * Created by Mark Gottschling on 11/18/2024
 */
public class SpellEventHandler {
    private static IEquipmentSpellHandler equipmentSpellHandler;

    /**
     * this class deviates a lot from the Forge version. here it is a Singleton where the
     * equipment handler is registered.
     * all the 'event' tie-ins are handled via mixins since most don't exist as events
     * out-of-the-box in Fabric.
     */
    private SpellEventHandler() {}

    public static void register(IEquipmentSpellHandler handler) {
        if (SpellEventHandler.equipmentSpellHandler == null) {
            SpellEventHandler.equipmentSpellHandler = handler;
        }
    }

    public static IEquipmentSpellHandler equipmentSpellHandler() {
        return SpellEventHandler.equipmentSpellHandler;
    }
}
