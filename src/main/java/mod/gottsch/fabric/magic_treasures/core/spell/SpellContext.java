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

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 
 * @author Mark Gottschling on May 3, 20024
 *
 */
public class SpellContext {
	private Hand hand;
	private String slotProviderId;
	private String slot;
	private ItemStack itemStack;
	private int index;
	private ISpell spell;

	/**
	 *
	 * @param builder
	 */
	SpellContext(Builder builder) {
		this.hand = builder.hand;
		this.slotProviderId = builder.slotProviderId;
		this.slot = builder.slot;
		this.itemStack = builder.itemStack;
		this.index = builder.index;
		this.spell = builder.spell;
	}

	public static Comparator<SpellContext> priorityComparator = new Comparator<SpellContext>() {
		@Override
		public int compare(SpellContext p1, SpellContext p2) {
			// use p1 < p2 because the sort should be ascending
			if (p1.getSpell().getPriority() < p2.getSpell().getPriority()) {
				// greater than
				return 1;
			} else {
				// less than
				return -1;
			}
		}
	};

	public Hand getHand() {
		return hand;
	}

	public String getSlotProviderId() {
		return slotProviderId;
	}

	public String getSlot() {
		return slot;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getIndex() {
		return index;
	}

	public ISpell getSpell() {
		return spell;
	}

	public static class Builder {
		public Hand hand;
		public String slotProviderId;
		public String slot;
		public ItemStack itemStack;
		public int index;
		public ISpell spell;

		public Builder with(Consumer<Builder> builder)  {
			builder.accept(this);
			return this;
		}

		public Builder withIndex(int index) {
			this.index = index;
			return this;
		}

		public SpellContext build() {
			return new SpellContext(this);
		}
	}

}
