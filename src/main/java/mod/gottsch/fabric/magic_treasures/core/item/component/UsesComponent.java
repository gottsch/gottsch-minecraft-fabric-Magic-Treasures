package mod.gottsch.fabric.magic_treasures.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.StoneRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
public record UsesComponent(int maxUses, int uses) {
    public static final Codec<UsesComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maxUses").forGetter(UsesComponent::maxUses),
            Codec.INT.fieldOf("uses").forGetter(UsesComponent::uses)

    ).apply(instance, UsesComponent::new));

    public boolean isInfinite() {
        return uses == Integer.MAX_VALUE;
    }

    /*
     * use the builder when creating a new component from scratch ie need to calculate the maxUses and uses values.
     */
    public static class Builder {
        public int maxUses = -1;
        public int uses;

        // transient
        public IJewelrySizeTier sizeTier;
        public JewelryMaterial material;

        public Builder(JewelryMaterial material) {
            this.material = material;
            this.sizeTier = JewelrySizeTier.REGULAR;
        }

        public Builder(JewelryMaterial material, IJewelrySizeTier sizeTier) {
            this.material = material;
            this.sizeTier = sizeTier;
        }

        public Builder with(Consumer<Builder> builder) {
            builder.accept(this);
            return this;
        }

        public Builder setInfinite() {
            this.maxUses = Integer.MAX_VALUE;
            return this;
        }

        public UsesComponent build() {
            if (this.maxUses <= 0) {
                this.maxUses = Math.round(this.material.getUses() * this.sizeTier.getUsesMultiplier());
            }
            this.uses = this.maxUses;

            return new UsesComponent(maxUses, uses);
        }
    }
}
