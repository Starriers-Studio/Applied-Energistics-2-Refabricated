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
        return (ItemStack) (Object) this;
    }

    default InteractionResult onItemUseFirst(UseOnContext context) {
        Player entityplayer = context.getPlayer();
        BlockPos blockpos = context.getClickedPos();
        BlockInWorld state = new BlockInWorld(context.getLevel(), blockpos, false);
        AdventureModePredicate adventureModePredicate = self().get(DataComponents.CAN_PLACE_ON);
        if (entityplayer != null && !entityplayer.getAbilities().mayBuild && (adventureModePredicate == null || !adventureModePredicate.test(state))) {
            return InteractionResult.PASS;
        } else {
            Item item = self().getItem();
            InteractionResult result = ((IItemExtended) item).onItemUseFirst(self(), context);
            if (entityplayer != null && result == InteractionResult.SUCCESS) {
                entityplayer.awardStat(Stats.ITEM_USED.get(item));
            }

            return result;
        }
    }

}
