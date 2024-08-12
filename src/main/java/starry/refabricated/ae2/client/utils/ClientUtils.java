package starry.refabricated.ae2.client.utils;

import net.minecraft.world.item.ItemStack;
import starry.refabricated.ae2.patches.IItemExtended;

public class ClientUtils {

    private static int slotMainHand = 0;

    public static boolean shouldCauseReequipAnimation(ItemStack from, ItemStack to, int slot) {
        boolean fromInvalid = from.isEmpty();
        boolean toInvalid = to.isEmpty();

        if (fromInvalid && toInvalid) return false;
        if (fromInvalid || toInvalid) return true;

        boolean changed = false;
        if (slot != -1) {
            changed = slot != slotMainHand;
            slotMainHand = slot;
        }
        return ((IItemExtended) from.getItem()).shouldCauseReequipAnimation(from, to, changed);
    }

}
