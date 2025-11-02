package net.ninjadev.ninjautils.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.ninjadev.ninjautils.data.PlayerSettingsData;
import net.ninjadev.ninjautils.event.InputEvents;
import net.ninjadev.ninjautils.feature.client.ClientInventorySortFeature;
import net.ninjadev.ninjautils.feature.Feature;
import net.ninjadev.ninjautils.init.client.ClientConfigs;
import net.ninjadev.ninjautils.init.client.ClientFeatures;
import net.ninjadev.ninjautils.init.client.ClientNetwork;
import net.ninjadev.ninjautils.init.client.ModKeybinds;
import net.ninjadev.ninjautils.init.server.ServerConfigs;
import net.ninjadev.ninjautils.network.C2SNotifyPacket;
import net.ninjadev.ninjautils.network.C2SSyncSettingsPacket;

public class NinjaUtilsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        this.registerEvents();
        ModKeybinds.register();
        ClientNetwork.register();
    }

    private void registerEvents() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ClientConfigs.register();
            ClientFeatures.init();
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> InputEvents.handleInput());

        ClientTickEvents.END_CLIENT_TICK.register(client -> ServerConfigs.FEATURES.features.stream().filter(Feature::isEnabled).forEach(Feature::onTick));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
                ClientPlayNetworking.send(new C2SNotifyPacket(client.player.getUuid()));

                ClientInventorySortFeature feature = ServerConfigs.FEATURES.getFeature(ClientInventorySortFeature.NAME);
                PlayerSettingsData data = new PlayerSettingsData()
                        .setSortInventoryEnabled(feature.isEnabled())
                        .setSortInventoryKeybind(feature.useKeybind());
                ClientPlayNetworking.send(new C2SSyncSettingsPacket(data));
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ServerConfigs.FEATURES.save();
        });
    }
}
