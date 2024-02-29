package net.ninjadev.ninjautils.init;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.ninjadev.ninjautils.command.*;

public class ModCommands {

    public static void init() {
        register(new NameColorCommand());
        register(new DeathPointCommand());
        register(new RestoreInventoryCommand());
        register(new SleepMessageCommand());
        register(new PeacefulPlayerCommand());
    }

    private static <T extends Command> void register(T command) {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> command.register(dispatcher)));
    }
}
