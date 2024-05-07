package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ninjadev.ninjautils.common.network.C2SNotifyPacket;
import net.ninjadev.ninjautils.common.network.C2SSortInventoryPacket;
import net.ninjadev.ninjautils.common.network.C2SSyncSettingsPacket;
import net.ninjadev.ninjautils.common.network.S2CNotifyPacket;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.feature.InventorySortFeature;

import java.util.UUID;

public class ModNetwork {

    public static void register() {
        PayloadTypeRegistry.playS2C().register(S2CNotifyPacket.PACKET_ID, S2CNotifyPacket.PACKET_CODEC);

        PayloadTypeRegistry.playC2S().register(C2SNotifyPacket.PACKET_ID, C2SNotifyPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SNotifyPacket.PACKET_ID, (payload, context) -> {
            UUID uuid = payload.playerId();
            ServerPlayerEntity player = context.player();
            ModPlayerManager.addInstalledClient(uuid);
            ServerPlayNetworking.send(player, new S2CNotifyPacket(true));
            SharedConstants.LOG.info("Player joined with NinjaUtils Client installed: {} - {}", player.getNameForScoreboard(), uuid);
        });

        PayloadTypeRegistry.playC2S().register(C2SSortInventoryPacket.PACKET_ID, C2SSortInventoryPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SSortInventoryPacket.PACKET_ID, (payload, context) -> {
            InventorySortFeature feature = ModConfigs.FEATURES.getFeature(InventorySortFeature.NAME);
            feature.sortInventory(context.player());
        });

        PayloadTypeRegistry.playC2S().register(C2SSyncSettingsPacket.PACKET_ID, C2SSyncSettingsPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SSyncSettingsPacket.PACKET_ID, (payload, context) -> {
            ModPlayerManager.updatePlayerSettings(context.player().getUuid(), payload.getPlayerSettings());
        });

    }
}
