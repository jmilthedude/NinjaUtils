package net.ninjadev.ninjautils.feature;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.ninjadev.ninjautils.init.ModSetup;

import java.util.List;

public class DimensionSymbolFeature extends Feature {

    public static final String NAME = "dimension_symbol";

    private boolean hasChanged = false;
    private final int delayInTicks = 30;
    private int ticksSinceChanged;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        if (this.registered) return;
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (this.hasChanged) {
                if (this.ticksSinceChanged++ >= delayInTicks) {
                    this.updatePlayerList();
                    this.ticksSinceChanged = -1;
                }
            }
        });
        this.registered = true;
    }

    @Override
    public void onDisable() {

    }

    public void updatePlayer(ServerPlayerEntity player, Identifier worldId) {
        this.hasChanged = true;
        this.ticksSinceChanged = 0;
    }

    public void updatePlayerList() {
        PlayerManager playerManager = ModSetup.SERVER.getPlayerManager();
        List<ServerPlayerEntity> playerList = playerManager.getPlayerList();
        playerManager.sendToAll(PlayerListS2CPacket.entryFromPlayer(playerList));
        this.hasChanged = false;
    }
}
