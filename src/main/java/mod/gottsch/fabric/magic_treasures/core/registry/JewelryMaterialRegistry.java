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
package mod.gottsch.fabric.magic_treasures.core.registry;

import com.google.common.collect.Lists;
import mod.gottsch.fabric.magic_treasures.core.jewelry.JewelryMaterial;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author Mark Gottschling May 8, 2024
 *
 */
public class JewelryMaterialRegistry {
    private static final Map<Identifier, JewelryMaterial> REGISTRY = new HashMap<>();

    public static Optional<JewelryMaterial> register(JewelryMaterial material) {
        if (!REGISTRY.containsKey(material.getId())) {
            REGISTRY.put(material.getId(), material);
            return Optional.of(material);
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
    public static Optional<JewelryMaterial> get(Identifier name) {

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

    public static List<JewelryMaterial> getMaterials() {
        return Lists.newArrayList(REGISTRY.values());
    }
}
