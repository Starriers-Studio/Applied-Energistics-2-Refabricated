package appeng.core.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import appeng.core.network.bidirectional.ConfigValuePacket;
import appeng.core.network.clientbound.AssemblerAnimationPacket;
import appeng.core.network.clientbound.BlockTransitionEffectPacket;
import appeng.core.network.clientbound.ClearPatternAccessTerminalPacket;
import appeng.core.network.clientbound.CompassResponsePacket;
import appeng.core.network.clientbound.CraftConfirmPlanPacket;
import appeng.core.network.clientbound.CraftingJobStatusPacket;
import appeng.core.network.clientbound.CraftingStatusPacket;
import appeng.core.network.clientbound.ExportedGridContent;
import appeng.core.network.clientbound.GuiDataSyncPacket;
import appeng.core.network.clientbound.ItemTransitionEffectPacket;
import appeng.core.network.clientbound.LightningPacket;
import appeng.core.network.clientbound.MEInventoryUpdatePacket;
import appeng.core.network.clientbound.MatterCannonPacket;
import appeng.core.network.clientbound.MockExplosionPacket;
import appeng.core.network.clientbound.NetworkStatusPacket;
import appeng.core.network.clientbound.PatternAccessTerminalPacket;
import appeng.core.network.clientbound.SetLinkStatusPacket;
import appeng.core.network.serverbound.ColorApplicatorSelectColorPacket;
import appeng.core.network.serverbound.ConfigButtonPacket;
import appeng.core.network.serverbound.ConfirmAutoCraftPacket;
import appeng.core.network.serverbound.FillCraftingGridFromRecipePacket;
import appeng.core.network.serverbound.GuiActionPacket;
import appeng.core.network.serverbound.HotkeyPacket;
import appeng.core.network.serverbound.InventoryActionPacket;
import appeng.core.network.serverbound.MEInteractionPacket;
import appeng.core.network.serverbound.MouseWheelPacket;
import appeng.core.network.serverbound.PartLeftClickPacket;
import appeng.core.network.serverbound.RequestClosestMeteoritePacket;
import appeng.core.network.serverbound.SelectKeyTypePacket;
import appeng.core.network.serverbound.SwapSlotsPacket;
import appeng.core.network.serverbound.SwitchGuisPacket;

public class InitNetwork {
    public static void init() {
        // Clientbound
        clientbound(AssemblerAnimationPacket.TYPE, AssemblerAnimationPacket.STREAM_CODEC);
        clientbound(BlockTransitionEffectPacket.TYPE, BlockTransitionEffectPacket.STREAM_CODEC);
        clientbound(ClearPatternAccessTerminalPacket.TYPE, ClearPatternAccessTerminalPacket.STREAM_CODEC);
        clientbound(CompassResponsePacket.TYPE, CompassResponsePacket.STREAM_CODEC);
        clientbound(CraftConfirmPlanPacket.TYPE, CraftConfirmPlanPacket.STREAM_CODEC);
        clientbound(CraftingJobStatusPacket.TYPE, CraftingJobStatusPacket.STREAM_CODEC);
        clientbound(CraftingStatusPacket.TYPE, CraftingStatusPacket.STREAM_CODEC);
        clientbound(GuiDataSyncPacket.TYPE, GuiDataSyncPacket.STREAM_CODEC);
        clientbound(ItemTransitionEffectPacket.TYPE, ItemTransitionEffectPacket.STREAM_CODEC);
        clientbound(LightningPacket.TYPE, LightningPacket.STREAM_CODEC);
        clientbound(MatterCannonPacket.TYPE, MatterCannonPacket.STREAM_CODEC);
        clientbound(MEInventoryUpdatePacket.TYPE, MEInventoryUpdatePacket.STREAM_CODEC);
        clientbound(MockExplosionPacket.TYPE, MockExplosionPacket.STREAM_CODEC);
        clientbound(NetworkStatusPacket.TYPE, NetworkStatusPacket.STREAM_CODEC);
        clientbound(PatternAccessTerminalPacket.TYPE, PatternAccessTerminalPacket.STREAM_CODEC);
        clientbound(SetLinkStatusPacket.TYPE, SetLinkStatusPacket.STREAM_CODEC);
        clientbound(ExportedGridContent.TYPE, ExportedGridContent.STREAM_CODEC);

        // Serverbound
        serverbound(ColorApplicatorSelectColorPacket.TYPE, ColorApplicatorSelectColorPacket.STREAM_CODEC);
        serverbound(RequestClosestMeteoritePacket.TYPE, RequestClosestMeteoritePacket.STREAM_CODEC);
        serverbound(ConfigButtonPacket.TYPE, ConfigButtonPacket.STREAM_CODEC);
        serverbound(ConfirmAutoCraftPacket.TYPE, ConfirmAutoCraftPacket.STREAM_CODEC);
        serverbound(FillCraftingGridFromRecipePacket.TYPE, FillCraftingGridFromRecipePacket.STREAM_CODEC);
        serverbound(GuiActionPacket.TYPE, GuiActionPacket.STREAM_CODEC);
        serverbound(HotkeyPacket.TYPE, HotkeyPacket.STREAM_CODEC);
        serverbound(InventoryActionPacket.TYPE, InventoryActionPacket.STREAM_CODEC);
        serverbound(MEInteractionPacket.TYPE, MEInteractionPacket.STREAM_CODEC);
        serverbound(MouseWheelPacket.TYPE, MouseWheelPacket.STREAM_CODEC);
        serverbound(PartLeftClickPacket.TYPE, PartLeftClickPacket.STREAM_CODEC);
        serverbound(SelectKeyTypePacket.TYPE, SelectKeyTypePacket.STREAM_CODEC);
        serverbound(SwapSlotsPacket.TYPE, SwapSlotsPacket.STREAM_CODEC);
        serverbound(SwitchGuisPacket.TYPE, SwitchGuisPacket.STREAM_CODEC);

        // Bidirectional
        bidirectional(ConfigValuePacket.TYPE, ConfigValuePacket.STREAM_CODEC);
    }

    private static <T extends ClientboundPacket> void clientbound(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        PayloadTypeRegistry.playS2C().register(type, codec);
        ClientPlayNetworking.registerGlobalReceiver(type, ClientboundPacket::handleOnClient);
    }

    private static <T extends ServerboundPacket> void serverbound(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        PayloadTypeRegistry.playC2S().register(type, codec);
        ServerPlayNetworking.registerGlobalReceiver(type, ServerboundPacket::handleOnServer);
    }

    private static <T extends ServerboundPacket & ClientboundPacket> void bidirectional(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        PayloadTypeRegistry.playC2S().register(type, codec);
        PayloadTypeRegistry.playS2C().register(type, codec);
        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
            if (payload instanceof ServerboundPacket packet) {
                packet.handleOnServer(context);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
            if (payload instanceof ClientboundPacket packet) {
                packet.handleOnClient(context);
            }
        });
    }
}
