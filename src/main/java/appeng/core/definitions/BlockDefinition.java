/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.core.definitions;

import java.util.Objects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.GenericStack;

public record BlockDefinition<T extends Block>(String englishName, T block,
                                               ItemDefinition<BlockItem> item) implements ItemLike {
    public BlockDefinition(String englishName, T block, ItemDefinition<BlockItem> item) {
        this.englishName = englishName;
        this.item = Objects.requireNonNull(item, "item");
        this.block = Objects.requireNonNull(block, "block");
    }

    public ResourceLocation id() {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    @Override
    public T block() {
        return this.block;
    }

    public ItemStack stack() {
        return item.stack();
    }

    public ItemStack stack(int stackSize) {
        return item.stack(stackSize);
    }

    public GenericStack genericStack(long stackSize) {
        return item.genericStack(stackSize);
    }

    public boolean is(ItemStack comparableStack) {
        return item.is(comparableStack);
    }

    public boolean is(AEKey key) {
        return item.is(key);
    }

    @Override
    public Item asItem() {
        return item.asItem();
    }
}
