package net.ninjadev.ninjautils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.ninjadev.ninjautils.compat.DiscordIntegrationCompat;
import net.ninjadev.ninjautils.init.ModPlayerManager;
import net.ninjadev.ninjautils.init.server.ModCommands;
import net.ninjadev.ninjautils.init.server.ServerConfigs;
import net.ninjadev.ninjautils.init.server.ServerNetwork;
import net.ninjadev.ninjautils.init.server.ServerSetup;

import static net.ninjadev.ninjautils.util.Constants.LOG;


public class NinjaUtils implements ModInitializer {

    @Override
    public void onInitialize() {
        LOG.info("Starting NinjaUtils");
        ServerConfigs.init();
        ModCommands.init();
        ServerSetup.registerLifecycleEvents();
        ServerNetwork.register();
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
