package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.ninjadev.ninjautils.command.Command;
import net.ninjadev.ninjautils.command.NameColorCommand;
import net.ninjadev.ninjautils.feature.NameColorFeature;

public class ModCommands {

    public static void init() {
        if (ModConfigs.FEATURES.isEnabled(NameColorFeature.NAME)) register(new NameColorCommand());
    }

    private static <T extends Command> void register(T command) {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            command.register(dispatcher);
        }));
    }
}
