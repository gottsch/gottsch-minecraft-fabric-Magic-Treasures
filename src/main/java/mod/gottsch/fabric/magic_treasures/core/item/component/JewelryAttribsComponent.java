package mod.gottsch.fabric.magic_treasures.core.item.component;

import blue.endless.jankson.annotation.Nullable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelrySizeTier;
import mod.gottsch.fabric.magic_treasures.core.item.IJewelryType;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import net.minecraft.util.Identifier;

/**
 * Created by Mark Gottschling on 11/19/2024
 */
public record JewelryAttribsComponent(IJewelryType type, IJewelrySizeTier sizeTier, JewelryMaterial material, @Nullable Identifier gemstone) {
    public static final Codec<JewelryAttribsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(JewelryAttribsComponent::type)

    ).apply(instance, JewelryAttribsComponent::new));
}
