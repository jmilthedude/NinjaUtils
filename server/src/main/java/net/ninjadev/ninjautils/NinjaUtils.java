package net.ninjadev.ninjautils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.ninjadev.ninjautils.compat.DiscordIntegrationCompat;
import net.ninjadev.ninjautils.init.*;

import static net.ninjadev.ninjautils.common.util.SharedConstants.LOG;

public class NinjaUtils implements ModInitializer {

    @Override
    public void onInitialize() {
        LOG.info("Starting NinjaUtils");
        ModConfigs.init();
        ModCommands.init();
        ModSetup.registerLifecycleEvents();
        ModNetwork.register();
        ModPlayerManager.registerEvents();
    }

    private static DiscordIntegrationCompat DISCORD;

    public static DiscordIntegrationCompat getDiscord() {
        if (!FabricLoader.getInstance().isModLoaded("dcintegration-fabric")) {
            return null;
        }
        if (DISCORD == null) {
            DISCORD = new DiscordIntegrationCompat();
        }
        return DISCORD;
    }
}
