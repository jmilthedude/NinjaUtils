package net.ninjadev.ninjautils;

import net.fabricmc.api.ClientModInitializer;
import net.ninjadev.ninjautils.init.ModKeybinds;
import net.ninjadev.ninjautils.init.ModNetwork;
import net.ninjadev.ninjautils.init.ModSetup;

public class NinjaUtilsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModSetup.registerLifecycleEvents();
        ModKeybinds.register();
        ModNetwork.register();
    }
}
