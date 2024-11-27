package mod.gottsch.fabric.magic_treasures.core.jewelry;

import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on 11/27/2024
 */
public class JewelryAttribs {
    private IJewelryType type;
    private IJewelrySizeTier sizeTier;
    private JewelryMaterial material;
    private Identifier stone;
    private JewelryStoneTier stoneTier;

    public JewelryAttribs() {}

    public JewelryAttribs(IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, Identifier stone) {
        this.type = type;
        this.sizeTier = sizeTier;
        this.material = material;
        this.stone = stone;
    }

    public IJewelryType getType() {
        return type;
    }

    public void setType(IJewelryType type) {
        this.type = type;
    }

    public IJewelrySizeTier getSizeTier() {
        return sizeTier;
    }

    public void setSizeTier(IJewelrySizeTier sizeTier) {
        this.sizeTier = sizeTier;
    }

    public JewelryMaterial getMaterial() {
        return material;
    }

    public void setMaterial(JewelryMaterial material) {
        this.material = material;
    }

    public Identifier getStone() {
        return stone;
    }

    public void setStone(Identifier stone) {
        this.stone = stone;
    }

    public JewelryStoneTier getStoneTier() {
        return stoneTier;
    }

    public void setStoneTier(JewelryStoneTier stoneTier) {
        this.stoneTier = stoneTier;
    }
}
