package starry.refabricated.ae2.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(JJJ)J"))
    private void inject$ae2refabricated$ItemExtendedImpl(CallbackInfo ci, @Local(ordinal = 0) ItemStack itemstack1, @Local(ordinal = 1) ItemStack itemstack2, @Local(name = "bl") boolean bl) {
        //TODO
    }

}
