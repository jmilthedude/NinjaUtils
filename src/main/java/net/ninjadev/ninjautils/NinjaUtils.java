package net.ninjadev.ninjautils;

import net.fabricmc.api.ModInitializer;
import net.ninjadev.ninjautils.init.ModCommands;
import net.ninjadev.ninjautils.init.ModConfigs;
import net.ninjadev.ninjautils.init.ModSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NinjaUtils implements ModInitializer {
    public static final Logger LOG = LoggerFactory.getLogger(NinjaUtils.class);
    public static final String MOD_ID = "ninjautils";

    @Override
    public void onInitialize() {
        LOG.info("Starting NinjaUtils");
        ModConfigs.init();
        ModCommands.init();
        ModSetup.registerLifecycleEvents();
    }
}
