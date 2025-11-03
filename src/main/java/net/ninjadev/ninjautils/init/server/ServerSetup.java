package net.ninjadev.ninjautils.init.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.ModEvents;

public class ServerSetup {

    public static MinecraftServer SERVER;

    public static void registerLifecycleEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> ServerFeatures.init());
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ModEvents.releaseAll();
            ServerConfigs.saveAll();
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> ServerConfigs.FEATURES.features.values().stream().filter(Feature::isEnabled).forEach(Feature::onTick));
        ServerLifecycleEvents.BEFORE_SAVE.register((server, flush, force) -> ServerConfigs.saveAll());
    }
}
