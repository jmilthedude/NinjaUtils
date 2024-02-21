package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.ninjadev.ninjautils.feature.Feature;

public class ModSetup {

    public static MinecraftServer SERVER;

    public static void registerLifecycleEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ModFeatures.init();
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            SERVER = server;
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ModEvents.releaseAll();
            ModConfigs.saveAll();
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Feature feature : ModConfigs.FEATURES.features) {
                if (feature.isEnabled()) {
                    feature.onTick();
                }
            }
        });
        ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> {
            ModConfigs.saveAll();
        });
    }
}
