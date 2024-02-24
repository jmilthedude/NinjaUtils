package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.ninjadev.ninjautils.command.Command;
import net.ninjadev.ninjautils.command.DeathPointCommand;
import net.ninjadev.ninjautils.command.NameColorCommand;
import net.ninjadev.ninjautils.command.RestoreInventoryCommand;
import net.ninjadev.ninjautils.feature.DeathPointFeature;
import net.ninjadev.ninjautils.feature.InventorySaveFeature;
import net.ninjadev.ninjautils.feature.NameColorFeature;

public class ModCommands {

    public static void init() {
        if (ModConfigs.FEATURES.isEnabled(NameColorFeature.NAME)) register(new NameColorCommand());
        if (ModConfigs.FEATURES.isEnabled(DeathPointFeature.NAME)) register(new DeathPointCommand());
        if (ModConfigs.FEATURES.isEnabled(InventorySaveFeature.NAME)) register(new RestoreInventoryCommand());
    }

    private static <T extends Command> void register(T command) {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            command.register(dispatcher);
        }));
    }
}
