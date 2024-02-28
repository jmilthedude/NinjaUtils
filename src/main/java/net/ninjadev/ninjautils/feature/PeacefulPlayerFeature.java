package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getPlayers() {
        return players;
    }
}
