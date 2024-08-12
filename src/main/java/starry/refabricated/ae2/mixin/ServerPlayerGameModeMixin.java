package starry.refabricated.ae2.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @ModifyVariable(method = "useItemOn", at = @At(value = "STORE"), ordinal = 1)
    private boolean injected(boolean x, @Local(argsOnly = true) ServerPlayer player, @Local(argsOnly = true) Level level, @Local BlockPos blockpos) {
        return x && !(player.getMainHandItem().doesSneakBypassUse(level, blockpos, player) && player.getOffhandItem().doesSneakBypassUse(level, blockpos, player));
    }

}
