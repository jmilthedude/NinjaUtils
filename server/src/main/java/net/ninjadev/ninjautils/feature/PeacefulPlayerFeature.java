package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;
import net.ninjadev.ninjautils.common.config.FeaturesConfig;
import net.ninjadev.ninjautils.common.feature.Feature;
import net.ninjadev.ninjautils.init.ModConfigs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeacefulPlayerFeature extends Feature {

    public static final String NAME = "peaceful_player";

    @Expose private List<String> players = new ArrayList<>();

    public PeacefulPlayerFeature(List<String> players) {
        this.players.addAll(players);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public boolean addPlayer(String playerName) {
        if (this.players.contains(playerName)) return false;
        return this.players.add(playerName);
    }

    public boolean removePlayer(String playerName) {
        if (!this.players.contains(playerName)) return false;
        return this.players.remove(playerName);
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public <C extends FeaturesConfig<?>> Optional<C> getConfig() {
        return (Optional<C>) Optional.ofNullable(ModConfigs.FEATURES);
    }
}
