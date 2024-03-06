package net.ninjadev.ninjautils.init;

import java.util.HashSet;
import java.util.UUID;

public class ModPlayerManager {

    private static final HashSet<UUID> INSTALLED_CLIENTS = new HashSet<>();

    public static void addInstalledClient(UUID playerId) {
        INSTALLED_CLIENTS.add(playerId);
    }

    public static void removeInstalledClient(UUID playerId) {
        INSTALLED_CLIENTS.remove(playerId);
    }

    public static boolean isClientInstalled(UUID playerId) {
        return INSTALLED_CLIENTS.contains(playerId);
    }
}
