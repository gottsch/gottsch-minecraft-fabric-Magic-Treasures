package mod.gottsch.fabric.magic_treasures.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryStoneTier;
import mod.gottsch.fabric.magic_treasures.core.registry.GemstoneRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
public record RechargesComponent(int maxRecharges, int recharges) {
    public static final Codec<RechargesComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("maxRecharges").forGetter(RechargesComponent::maxRecharges),
            Codec.INT.fieldOf("recharges").forGetter(RechargesComponent::recharges)

    ).apply(instance, RechargesComponent::new));

    public RechargesComponent(int maxRecharges) {
        this(maxRecharges, maxRecharges);
    }

    public static class Builder {
        public int maxRecharges = -1;
        public int recharges;

        // transient
        public JewelryMaterial material;
        public Identifier stone;

        public Builder(JewelryMaterial material) {
            this.material = material;
        }

        public Builder(JewelryMaterial material, Identifier stone) {
            this.material = material;
            this.stone = stone;
        }

        public RechargesComponent.Builder with(Consumer<RechargesComponent.Builder> builder) {
            builder.accept(this);
            return this;
        }

        public Builder withStone(Identifier stone) {
            this.stone = stone;
            return this;
        }

        public RechargesComponent build() {
            // get the stone and stone tier
            Item stone = GemstoneRegistry.get(this.stone).orElse(Items.AIR);
            // determine the tier
            Optional<JewelryStoneTier> stoneTier = GemstoneRegistry.getStoneTier(stone);

            if (this.maxRecharges < 0) {
                this.maxRecharges =
                        this.material.getRecharges() +
                                stoneTier.map(JewelryStoneTier::getRecharges).orElseGet(() -> 0);
            }
            this.recharges = this.maxRecharges;

            return new RechargesComponent(maxRecharges, recharges);
        }
    }
}
