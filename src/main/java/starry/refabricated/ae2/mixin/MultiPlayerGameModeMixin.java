package starry.refabricated.ae2.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @ModifyVariable(method = "performUseItemOn", at = @At(value = "STORE"), ordinal = 1)
    private boolean injected(boolean x, @Local(argsOnly = true) LocalPlayer player, @Local BlockPos blockpos) {
        return x && !(player.getMainHandItem().doesSneakBypassUse(player.level(), blockpos, player) && player.getOffhandItem().doesSneakBypassUse(player.level(), blockpos, player));
    }

}
