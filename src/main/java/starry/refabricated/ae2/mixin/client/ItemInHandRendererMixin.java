package starry.refabricated.ae2.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import starry.refabricated.ae2.client.utils.ClientUtils;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow private ItemStack mainHandItem;

    @Shadow private ItemStack offHandItem;

    @Shadow private float mainHandHeight;

    @Shadow private float offHandHeight;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F", shift = At.Shift.BY, by = 3))
    private void inject$ae2refabricated$patches(CallbackInfo ci, @Local LocalPlayer localPlayer, @Local(ordinal = 0) ItemStack itemStack, @Local(ordinal = 1) ItemStack itemStack1, @Local float f) {
        boolean requipM = ClientUtils.shouldCauseReequipAnimation(mainHandItem, itemStack, localPlayer.getInventory().selected);
        boolean requipO = ClientUtils.shouldCauseReequipAnimation(offHandItem, itemStack1, -1);
        if (!requipM && this.mainHandItem != itemStack) this.mainHandItem = itemStack;
        if (!requipO && this.offHandItem != itemStack1) this.offHandItem = itemStack1;

        mainHandHeight += Mth.clamp((!requipM ? f * f * f : 0.0F) - this.mainHandHeight, -0.4F, 0.4F);
        offHandHeight += Mth.clamp((float)(!requipO ? 1 : 0) - this.offHandHeight, -0.4F, 0.4F);
    }

}
