package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.ninjadev.ninjautils.common.data.PlayerSettingsData;
import net.ninjadev.ninjautils.common.util.SharedConstants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ModPlayerManager {

    private static final HashSet<UUID> INSTALLED_CLIENTS = new HashSet<>();
    private static final HashMap<UUID, PlayerSettingsData> PLAYER_SETTINGS = new HashMap<>();

    public static void registerEvents() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (removeInstalledClient(handler.player.getUuid())) {
                SharedConstants.LOG.info("Player left with NinjaUtils Client installed: {} - {}", handler.player.getNameForScoreboard(), handler.player.getUuid());
            }
        });
    }

    public static void addInstalledClient(UUID playerId) {
        INSTALLED_CLIENTS.add(playerId);
    }

    public static boolean removeInstalledClient(UUID playerId) {
        return INSTALLED_CLIENTS.remove(playerId);
    }

    public static boolean isClientInstalled(UUID playerId) {
        return INSTALLED_CLIENTS.contains(playerId);
    }

    public static void updatePlayerSettings(UUID playerId, PlayerSettingsData data) {
        PLAYER_SETTINGS.put(playerId, data);
    }

    public static PlayerSettingsData getPlayerSettings(UUID playerId) {
        return PLAYER_SETTINGS.getOrDefault(playerId, PlayerSettingsData.EMPTY);
    }
}
