package appeng.core.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public interface ServerboundPacket extends CustomAppEngPayload {
    default void handleOnServer(ServerPlayNetworking.Context context) {
        if (context.player() instanceof ServerPlayer serverPlayer) {
            handleOnServer(serverPlayer);
        }
    }

    void handleOnServer(ServerPlayer player);
}
