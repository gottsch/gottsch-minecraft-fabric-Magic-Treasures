/*
 * This file is part of  Magic Treasures.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * Magic Treasures is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magic Treasures is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Magic Treasures.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.magic_treasures.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.gottsch.fabric.magic_treasures.core.spell.ISpell;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this component contains a list of spell identifiers.
 * Created by Mark Gottschling on 11/15/2024
 */
public record SpellsComponent(List<Identifier> spellNames) {

    public static final Codec<SpellsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.listOf().fieldOf("spellNames").forGetter(SpellsComponent::spellNames)
    ).apply(instance, SpellsComponent::new));

//    public SpellsComponent(List<Identifier> spellNames) {
//        if (spellNames == null) {
//            this.spellNames = new ArrayList<>();
//        }
//    }

    public SpellsComponent(Identifier... spellNames) {
        this(Arrays.asList(spellNames));
    }

    public SpellsComponent(ISpell... spells) {
        this(Arrays.stream(spells).map(ISpell::getName).toList());
    }

    /**
     *
     * @return
     */
    public List<Identifier> spellNames() {
        if (this.spellNames == null) {
            return new ArrayList<>();
        }
        return this.spellNames;
    }
}
