package starry.refabricated.ae2.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.MouseHandler;

import starry.refabricated.ae2.events.MouseEvents;

/**
 * Emulates the Forge MouseWheel-Event that is triggered outside of UIs
 */
@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    /**
     * Inject right before the slot-cycling that would normally be caused by the scroll-wheel
     */
    @Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onScrollWithoutScreen(long windowId, double x, double y, CallbackInfo ci, double verticalAmount) {
        if (MouseEvents.SCROLLED.invoker().onWheelScrolled(verticalAmount)) {
            ci.cancel();
        }
    }

}