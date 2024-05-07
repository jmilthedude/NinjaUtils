package net.ninjadev.ninjautils.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.init.ModSetup;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class NameColorState extends PersistentState {
    protected static final String DATA_NAME = SharedConstants.SERVER_MOD_ID + "_nameColor";

    private final HashMap<UUID, Color> entries = new HashMap<>();

    public void setPlayerColor(ServerPlayerEntity player, Color color) {
        this.setPlayerColor(player.getUuid(), color);
    }

    public void setPlayerColor(UUID id, Color color) {
        this.entries.put(id, color);
        this.markDirty();
    }

    public Color getPlayerColor(UUID playerId) {
        Color color = this.entries.computeIfAbsent(playerId, id -> Color.WHITE);
        this.markDirty();
        return color;
    }

    public Color getPlayerColor(ServerPlayerEntity player) {
        return this.getPlayerColor(player.getUuid());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        entries.forEach((uuid, color) -> {
            NbtCompound colorNbt = new NbtCompound();
            colorNbt.putInt("color", color.getRGB());
            nbt.put(uuid.toString(), colorNbt);
        });
        return nbt;
    }

    private static NameColorState load(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NameColorState state = new NameColorState();
        for (String key : nbt.getKeys()) {
            NbtCompound colorNbt = nbt.getCompound(key);
            state.setPlayerColor(UUID.fromString(key), new Color(colorNbt.getInt("color")));
        }
        return state;
    }

    public static NameColorState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(new PersistentState.Type<>(NameColorState::new, NameColorState::load, null), DATA_NAME);
    }
}
