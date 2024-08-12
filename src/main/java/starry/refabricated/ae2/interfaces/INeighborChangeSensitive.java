package starry.refabricated.ae2.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public interface INeighborChangeSensitive {

    // This is usually a Forge extension. We replace it using a Mixin.
    void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor);

}
