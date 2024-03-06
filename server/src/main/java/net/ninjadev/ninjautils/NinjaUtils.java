package net.ninjadev.ninjautils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.ninjadev.ninjautils.compat.DiscordIntegrationCompat;
import net.ninjadev.ninjautils.init.ModCommands;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NinjaUtils implements ModInitializer {
    public static final Logger LOG = LoggerFactory.getLogger(NinjaUtils.class);

    @Override
    public void onInitialize() {
        LOG.info("Starting NinjaUtils");
        ModConfigs.init();
        ModCommands.init();
        ModSetup.registerLifecycleEvents();
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
