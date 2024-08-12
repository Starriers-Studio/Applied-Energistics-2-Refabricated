package starry.refabricated.ae2.patches;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

public interface IItemStackExtended {

    private ItemStack self() {
        return (ItemStack) this;
    }

    default boolean doesSneakBypassUse(net.minecraft.world.level.LevelReader level, BlockPos pos, Player player) {
        //return self().isEmpty() || self().getItem().doesSneakBypassUse(self(), level, pos, player);
    }

}
