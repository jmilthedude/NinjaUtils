package net.ninjadev.ninjautils.init.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ninjadev.ninjautils.network.S2CNotifyPacket;

public class ClientNetwork {

    private static boolean serverInstalled = false;

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(S2CNotifyPacket.PACKET_ID, (payload, context) -> {
            serverInstalled = payload.acknowledged();
        });
    }

    public static boolean isServerInstalled() {
        return serverInstalled;
    }
}
