package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.ninjadev.ninjautils.common.network.C2SNotifyPacket;
import net.ninjadev.ninjautils.common.network.C2SSortInventoryPacket;
import net.ninjadev.ninjautils.common.network.S2CNotifyPacket;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.feature.InventorySortFeature;

public class ModNetwork {

    public static void register() {

        ServerPlayNetworking.registerGlobalReceiver(C2SNotifyPacket.TYPE, (packet, player, responseSender) -> {
            ModPlayerManager.addInstalledClient(packet.getPlayerId());
            ServerPlayNetworking.send(player, new S2CNotifyPacket(true));
            SharedConstants.LOG.info("Player joined with NinjaUtils Client installed: {} - {}", player.getNameForScoreboard(), packet.getPlayerId());
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SSortInventoryPacket.TYPE, (packet, player, responseSender) -> {
            InventorySortFeature feature = ModConfigs.FEATURES.getFeature(InventorySortFeature.NAME);
            feature.sortInventory(player);
        });

    }
}
