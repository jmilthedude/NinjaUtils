package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ninjadev.ninjautils.common.network.S2CNotifyPacket;

public class ModNetwork {

    private static boolean serverInstalled = false;

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(S2CNotifyPacket.TYPE, (packet, player, responseSender) -> {
            serverInstalled = packet.acknowledged();
        });
    }

    public static boolean isServerInstalled() {
        return serverInstalled;
    }
}
