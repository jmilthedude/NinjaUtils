package net.ninjadev.ninjautilsclient.module;

import net.ninjadev.ninjautilsclient.config.AntiFogModuleConfig;
import net.ninjadev.ninjautilsclient.init.ModConfigs;
import net.ninjadev.ninjautilsclient.module.base.AbstractModule;

public class AntiFogModule extends AbstractModule<AntiFogModuleConfig> {

    public AntiFogModule() {
        super(ModConfigs.ANTI_FOG);
    }

    @Override
    public String getName() {
        return "AntiFog";
    }
}
