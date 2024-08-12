/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.api;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;

import appeng.api.behaviors.GenericInternalInventory;
import appeng.api.implementations.blockentities.ICraftingMachine;
import appeng.api.implementations.blockentities.ICrankable;
import appeng.api.networking.IInWorldGridNodeHost;
import appeng.api.storage.MEStorage;
import appeng.core.AppEng;

/**
 * Utility class that holds the capabilities provided by AE2.
 */
public final class AECapabilities {
    private AECapabilities() {
    }

    public static BlockApiLookup<MEStorage, @Nullable Direction> ME_STORAGE = BlockApiLookup
            .get(AppEng.makeId("me_storage"), MEStorage.class, Direction.class);

    public static BlockApiLookup<ICraftingMachine, @Nullable Direction> CRAFTING_MACHINE = BlockApiLookup
            .get(AppEng.makeId("crafting_machine"), ICraftingMachine.class, Direction.class);

    public static BlockApiLookup<GenericInternalInventory, @Nullable Direction> GENERIC_INTERNAL_INV = BlockApiLookup
            .get(AppEng.makeId("generic_internal_inv"), GenericInternalInventory.class, Direction.class);

    public static BlockApiLookup<IInWorldGridNodeHost, Void> IN_WORLD_GRID_NODE_HOST = BlockApiLookup
            .get(AppEng.makeId("inworld_gridnode_host"), IInWorldGridNodeHost.class, Void.class);

    public static BlockApiLookup<ICrankable, @Nullable Direction> CRANKABLE = BlockApiLookup
            .get(AppEng.makeId("crankable"), ICrankable.class, Direction.class);

}
