package appeng.api.behaviors;

import com.google.common.primitives.Ints;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.GenericStack;
import appeng.util.GenericContainerHelper;
import appeng.util.fluid.FluidSoundHelper;

class FluidContainerItemStrategy
        implements ContainerItemStrategy<AEFluidKey, FluidContainerItemStrategy.Context> {
    @Override
    public @Nullable GenericStack getContainedStack(ItemStack stack) {
        return GenericContainerHelper.getContainedFluidStack(stack);
    }

    @Override
    public @Nullable Context findCarriedContext(Player player, AbstractContainerMenu menu) {
        var handler = ContainerItemContext.ofPlayerCursor(player, menu).find(FluidStorage.ITEM);
        if (handler != null) return new CarriedContext(handler, player, menu);
        return null;
    }

    @Override
    public @Nullable Context findPlayerSlotContext(Player player, int slot) {
        var handler = ContainerItemContext.ofPlayerSlot(player, PlayerInventoryStorage.of(player.getInventory()).getSlots().get(slot)).find(FluidStorage.ITEM);
        if (handler != null) return new PlayerInvContext(handler, player, slot);
        return null;
    }

    @Override
    public long extract(Context context, AEFluidKey what, long amount, Actionable mode) {
        try (var tx = Transaction.openOuter()) {
            var extracted = context.getStorage().extract(FluidVariant.of(what.getFluid()), amount, tx);
            if (mode == Actionable.MODULATE) {
                tx.commit();
            }
            return extracted;
        }
    }

    @Override
    public long insert(Context context, AEFluidKey what, long amount, Actionable mode) {
        try (var tx = Transaction.openOuter()) {
            var inserted = context.getStorage().insert(FluidVariant.of(what.getFluid()), amount, tx);
            if (mode == Actionable.MODULATE) {
                tx.commit();
            }
            return inserted;
        }
    }

    @Override
    public void playFillSound(Player player, AEFluidKey what) {
        FluidSoundHelper.playFillSound(player, what);
    }

    @Override
    public void playEmptySound(Player player, AEFluidKey what) {
        FluidSoundHelper.playEmptySound(player, what);
    }

    @Override
    public @Nullable GenericStack getExtractableContent(Context context) {
        return getContainedStack(context.getStack());
    }

    interface Context {
        Storage<FluidVariant> getStorage();

        ItemStack getStack();

        void setStack(ItemStack stack);

        void addOverflow(ItemStack stack);
    }

    private record CarriedContext(Storage<FluidVariant> storage, Player player, AbstractContainerMenu menu) implements Context {
        @Override
        public Storage<FluidVariant> getStorage() {
            return storage;
        }

        @Override
        public ItemStack getStack() {
            return menu.getCarried();
        }

        @Override
        public void setStack(ItemStack stack) {
            menu.setCarried(stack);
        }

        public void addOverflow(ItemStack stack) {
            if (menu.getCarried().isEmpty()) {
                menu.setCarried(stack);
            } else {
                player.getInventory().placeItemBackInInventory(stack);
            }
        }
    }

    private record PlayerInvContext(Storage<FluidVariant> storage, Player player, int slot) implements Context {
        @Override
        public Storage<FluidVariant> getStorage() {
            return storage;
        }

        @Override
        public ItemStack getStack() {
            return player.getInventory().getItem(slot);
        }

        @Override
        public void setStack(ItemStack stack) {
            player.getInventory().setItem(slot, stack);
        }

        public void addOverflow(ItemStack stack) {
            player.getInventory().placeItemBackInInventory(stack);
        }
    }
}
