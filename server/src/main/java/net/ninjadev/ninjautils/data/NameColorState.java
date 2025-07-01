package net.ninjadev.ninjautils.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.ninjadev.ninjautils.common.util.SharedConstants;
import net.ninjadev.ninjautils.init.ModSetup;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class NameColorState extends PersistentState {
    protected static final String DATA_NAME = SharedConstants.SERVER_MOD_ID + "_nameColor";

    private final HashMap<UUID, Color> entries = new HashMap<>();

    private static final Codec<Color> COLOR_CODEC = RecordCodecBuilder.create(colorInstance ->
            colorInstance.group(
                    Codec.INT.fieldOf("color").forGetter(Color::getRGB)
            ).apply(colorInstance, Color::new)
    );

    public static final Codec<NameColorState> CODEC = Codec.unboundedMap(
            Codec.STRING,
            COLOR_CODEC
    ).xmap(
            colorMap -> {
                NameColorState state = new NameColorState();
                colorMap.forEach((key, color) -> state.setPlayerColor(UUID.fromString(key), color));
                return state;
            },
            state -> {
                HashMap<String, Color> colorMap = new HashMap<>();
                state.entries.forEach((uuid, color) -> colorMap.put(uuid.toString(), color));
                return colorMap;
            }
    );

    private static final PersistentStateType<NameColorState> TYPE = new PersistentStateType<>(DATA_NAME, NameColorState::new, CODEC, null);

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

    public static NameColorState get() {
        return ModSetup.SERVER
                .getOverworld()
                .getPersistentStateManager()
                .getOrCreate(TYPE);
    }
}
