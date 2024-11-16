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
package mod.gottsch.fabric.magic_treasures.core.spell;

import mod.gottsch.fabric.gottschcore.enums.IRarity;
import mod.gottsch.fabric.magic_treasures.core.util.ModUtil;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Predicate;

public class SpellRegistry {
    private static final Map<Identifier, ISpell> REGISTRY = new HashMap<>();
    private static final Map<Integer, List<ISpell>> REGISTRY_BY_LEVEL = new HashMap<>();
    private static final Map<IRarity, List<ISpell>> REGISTRY_BY_RARITY = new HashMap<>();

    /**
     * 
     * @param spell
     */
    public static ISpell register(ISpell spell) {
        if (!REGISTRY.containsKey(spell.getName())) {
            REGISTRY.put(spell.getName(), spell);
        }
        if (!REGISTRY_BY_LEVEL.containsKey(spell.getLevel())) {
        	List<ISpell> spellList = new ArrayList<>();
        	spellList.add(spell);
        	REGISTRY_BY_LEVEL.put(spell.getLevel(), spellList);
        }
        else {
        	REGISTRY_BY_LEVEL.get(spell.getLevel()).add(spell);
        }
        if (!REGISTRY_BY_RARITY.containsKey(spell.getRarity())) {
            List<ISpell> charmList = new ArrayList<>();
            charmList.add(spell);
            REGISTRY_BY_RARITY.put(spell.getRarity(), charmList);
        }
        else {
            REGISTRY_BY_RARITY.get(spell.getRarity()).add(spell);
        }

        return spell;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Optional<ISpell> get(String name) {
        return get(ModUtil.asLocation(name));
    }

    /**
     * 
     * @param name
     * @return
     */
    public static Optional<ISpell> get(Identifier name) {
        
        if (REGISTRY.containsKey(name)) {
            return Optional.of(REGISTRY.get(name));
        }
        return Optional.empty();
    }
    
    /**
     * @param level
     * @return
     */
    public static List<ISpell> get(Integer level) {
        if (REGISTRY_BY_LEVEL.containsKey(level)) {
            return REGISTRY_BY_LEVEL.get(level);
        }
        return new ArrayList<>();
    }

    /**
     * @param rarity
     * @return
     */
    public static List<ISpell> get(IRarity rarity) {
        if (REGISTRY_BY_RARITY.containsKey(rarity)) {
            return REGISTRY_BY_RARITY.get(rarity);
        }
        return new ArrayList<>();
    }

	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public static Optional<List<ISpell>> getBy(Predicate<ISpell> predicate) {
		List<ISpell> charms = new ArrayList<>();
		for (ISpell c : SpellRegistry.values()) {
			if (predicate.test(c)) {
				charms.add(c);
			}
		}
		if (charms.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(charms);
	}
	
    /**
     * 
     * @return
     */
    public static List<ISpell> values() {
    	return new ArrayList<ISpell>(REGISTRY.values());
    }
}
