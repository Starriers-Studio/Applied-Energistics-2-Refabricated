package appeng.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;

public interface ClientboundPacket extends CustomAppEngPayload {
    default void handleOnClient(ClientPlayNetworking.Context context) {
        handleOnClient(context.player());
    }

    default void handleOnClient(Player player) {
        throw new AbstractMethodError("Unimplemented method on " + getClass());
    }
}
