package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.ninjadev.ninjautils.common.network.C2SNotifyPacket;
import net.ninjadev.ninjautils.common.network.C2SSortInventoryPacket;
import net.ninjadev.ninjautils.common.network.C2SSyncSettingsPacket;
import net.ninjadev.ninjautils.common.network.S2CNotifyPacket;

public class ModNetwork {

    private static boolean serverInstalled = false;

    public static void register() {
        PayloadTypeRegistry.playC2S().register(C2SNotifyPacket.PACKET_ID, C2SNotifyPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(C2SSortInventoryPacket.PACKET_ID, C2SSortInventoryPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(C2SSyncSettingsPacket.PACKET_ID, C2SSyncSettingsPacket.PACKET_CODEC);

        PayloadTypeRegistry.playS2C().register(S2CNotifyPacket.PACKET_ID, S2CNotifyPacket.PACKET_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(S2CNotifyPacket.PACKET_ID, (payload, context) -> {
            serverInstalled = payload.acknowledged();
        });
    }

    public static boolean isServerInstalled() {
        return serverInstalled;
    }
}
