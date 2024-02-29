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
}
