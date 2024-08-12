package starry.refabricated.ae2.helpers;

import appeng.core.network.CustomAppEngPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class NetworkHelper {
    public static void sendToPlayersNear(ServerLevel serverLevel, double x, double y, double z, double radius, CustomAppEngPayload packet) {
        var players = PlayerLookup.around(serverLevel, new Vec3(x, y, z), radius);
        for (ServerPlayer player : players) {
            ServerPlayNetworking.send(player, packet);
        }
    }

    public static void sendToServer(CustomAppEngPayload payload) {
        if (ClientPlayNetworking.canSend(payload.type())) {
            ClientPlayNetworking.send(payload);
        }
    }
}
