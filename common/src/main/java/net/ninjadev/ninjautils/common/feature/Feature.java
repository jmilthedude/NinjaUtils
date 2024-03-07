package net.ninjadev.ninjautils.common.feature;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.common.config.Config;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;

import java.util.Optional;

public abstract class Feature {

    protected boolean registered = false;
    @Expose private boolean enabled;

    public abstract String getName();
    public void onTick() {}

    public void enable() {
        this.enabled = true;
        this.onEnable();
        this.getConfig().ifPresent(Config::markDirty);
    }
    public abstract void onEnable();

    public void disable() {
        this.enabled = false;
        this.onDisable();
        this.getConfig().ifPresent(Config::markDirty);
    }
    public abstract void onDisable();

    public <T extends Feature> T setEnabled(boolean enabled) {
        if(enabled) {
            this.enable();
        } else {
            this.disable();
        }
        if (this instanceof FeedbackFeature feedbackFeature) {
            feedbackFeature.sendFeedback();
        }
        return (T) this;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract <C extends FeaturesConfig<?>> Optional<C> getConfig();

}
