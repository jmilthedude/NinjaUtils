package net.ninjadev.ninjautils.init;

import net.ninjadev.ninjautils.common.data.PlayerSettingsData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ModPlayerManager {

    private static final HashSet<UUID> INSTALLED_CLIENTS = new HashSet<>();
    private static final HashMap<UUID, PlayerSettingsData> PLAYER_SETTINGS = new HashMap<>();

    public static void addInstalledClient(UUID playerId) {
        INSTALLED_CLIENTS.add(playerId);
    }

    public static void removeInstalledClient(UUID playerId) {
        INSTALLED_CLIENTS.remove(playerId);
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
