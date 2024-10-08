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

package appeng.block.qnb;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import appeng.blockentity.qnb.QuantumBridgeBlockEntity;
import appeng.client.EffectType;
import appeng.core.AppEng;
import appeng.core.AppEngClient;
import appeng.menu.MenuOpener;
import appeng.menu.implementations.QNBMenu;
import appeng.menu.locator.MenuLocators;

public class QuantumLinkChamberBlock extends QuantumBaseBlock {

    private static final VoxelShape SHAPE;

    static {
        final double onePixel = 2.0 / 16.0;
        SHAPE = Shapes.create(
                new AABB(onePixel, onePixel, onePixel, 1.0 - onePixel, 1.0 - onePixel, 1.0 - onePixel));
    }

    public QuantumLinkChamberBlock() {
        super(glassProps());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        final QuantumBridgeBlockEntity bridge = this.getBlockEntity(level, pos);
        if (bridge != null && bridge.hasQES() && AppEngClient.instance().shouldAddParticles(rand)) {
            AppEng.instance().spawnEffect(EffectType.Energy, level, pos.getX() + 0.5, pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    null);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof QuantumBridgeBlockEntity be) {
            if (!level.isClientSide()) {
                MenuOpener.open(QNBMenu.TYPE, player, MenuLocators.forBlockEntity(be));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

}
