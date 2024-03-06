package net.ninjadev.ninjautilsclient.config.base;

import com.google.gson.annotations.Expose;

public abstract class ModuleConfig extends Config {

    @Expose protected boolean enabled;

    @Override
    public String getFolder() {
        return "module";
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.save();
    }

    public boolean isEnabled() {
        return enabled;
    }
}
