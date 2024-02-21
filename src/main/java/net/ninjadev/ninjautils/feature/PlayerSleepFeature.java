package net.ninjadev.ninjautils.feature;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ninjadev.ninjautils.init.ModSetup;
import net.ninjadev.ninjautils.util.Cooldown;

import java.util.*;

public class PlayerSleepFeature extends Feature {
    public static final String NAME = "player_sleep";

    private boolean registered = false;
    private static final HashMap<UUID, Cooldown> COOLDOWNS = new HashMap<>();

    public PlayerSleepFeature(boolean enabled) {
        super(enabled);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEnable() {
        if (!registered) {
            EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
                if (!this.isEnabled()) return;
                if (!(entity instanceof ServerPlayerEntity player)) return;

                if (COOLDOWNS.containsKey(player.getUuid())) return;

                COOLDOWNS.put(player.getUuid(), new Cooldown(20));
                MutableText playerName = player.getDisplayName() == null ? player.getName().copy() : player.getDisplayName().copy();
                MutableText text = playerName.append(Text.literal(this.getSleepMessage()).formatted(Formatting.WHITE));
                ModSetup.SERVER.getPlayerManager().broadcast(text, false);

            });
            registered = true;
        }
    }

    @Override
    public void onTick() {
        COOLDOWNS.values().forEach(Cooldown::update);
        COOLDOWNS.entrySet().removeIf(entrySet -> entrySet.getValue().isComplete());
    }

    @Override
    public void onDisable() {
    }

    private final Random random = new Random();

    private String getSleepMessage() {
        List<String> messages = List.of(
                " is taking a little nappy.",
                " is sweeping wike a wittle baby.",
                " is dreaming of diamonds.",
                " is sleeping.",
                " had a long day so going to bed.",
                " is \"just resting their eyes\".",
                " is going mimis.",
                " is laying in bed scrolling the socials.",
                " is going to sleep, recalling all the sticks they took from raens... wait what?",
                " is trying to sleep but probably will only get like 10 seconds of it.",
                " may be sleeping. Maybe not? Who knows...",
                " just needs a wee rest.."
        );
        return messages.get(random.nextInt(messages.size()));
    }
}
