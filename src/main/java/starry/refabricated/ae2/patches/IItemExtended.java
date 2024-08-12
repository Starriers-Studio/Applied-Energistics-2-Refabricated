package starry.refabricated.ae2.patches;

import net.minecraft.world.item.ItemStack;

public interface IItemExtended {

    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.equals(newStack); // !ItemStack.areItemStacksEqual(oldStack, newStack);
    }

}
