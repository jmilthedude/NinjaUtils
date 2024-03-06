package net.ninjadev.ninjautilsclient.init;


import net.ninjadev.ninjautilsclient.config.base.ModuleConfig;
import net.ninjadev.ninjautilsclient.module.AntiFogModule;
import net.ninjadev.ninjautilsclient.module.FullBrightnessModule;
import net.ninjadev.ninjautilsclient.module.base.AbstractModule;

import java.util.Collection;
import java.util.HashMap;

public class ModModules {

    private static final HashMap<String, AbstractModule<? extends ModuleConfig>> REGISTRY = new HashMap<>();

    public static Collection<AbstractModule<? extends ModuleConfig>> getModules() {
        return REGISTRY.values();
    }

    public static AntiFogModule ANTI_FOG;
    public static FullBrightnessModule FULL_BRIGHT;

    public static void register() {
        ANTI_FOG = registerModule(new AntiFogModule());
        FULL_BRIGHT = registerModule(new FullBrightnessModule());

    }

    private static <T extends AbstractModule<C>, C extends ModuleConfig> T registerModule(T module) {
        REGISTRY.put(module.getName(), module);
        return module;
    }
}
