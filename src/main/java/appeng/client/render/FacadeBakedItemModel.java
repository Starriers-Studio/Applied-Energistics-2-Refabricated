/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2018, AlgorithmX2, All rights reserved.
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

package appeng.client.render;

import java.util.function.Supplier;

import appeng.items.parts.FacadeItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import appeng.client.render.cablebus.FacadeBuilder;

/**
 * This model used the provided FacadeBuilder to "slice" the item quads for the facade provided.
 *
 * @author covers1624
 */
public class FacadeBakedItemModel extends ForwardingBakedModel {

    private final FacadeBuilder facadeBuilder;
    private final Int2ObjectMap<Mesh> cache = new Int2ObjectArrayMap<>();

    protected FacadeBakedItemModel(BakedModel baseModel, FacadeBuilder facadeBuilder) {
        this.wrapped = baseModel;
        this.facadeBuilder = facadeBuilder;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        if (!(stack.getItem() instanceof FacadeItem itemFacade)) {
            return;
        }

        super.emitItemQuads(stack, randomSupplier, context);

        ItemStack textureItem = itemFacade.getTextureItem(stack);
        if (!textureItem.isEmpty()) {
            int itemId = Item.getId(textureItem.getItem());
            Mesh mesh = this.cache.get(itemId);
            if (mesh == null) {
                mesh = this.facadeBuilder.buildFacadeItemQuads(textureItem, Direction.NORTH);
                this.cache.put(itemId, mesh);
            }

            mesh.outputTo(context.getEmitter());
        }
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
