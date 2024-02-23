package net.ninjadev.ninjautils.feature;

import com.google.gson.annotations.Expose;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ninjadev.ninjautils.init.ModSetup;
import net.ninjadev.ninjautils.util.Cooldown;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerSleepFeature extends Feature {
    public static final String NAME = "player_sleep";

    private static final HashMap<UUID, Cooldown> COOLDOWNS = new HashMap<>();

    @Expose private List<String> sleepMessages = List.of(
            "is taking a little nappy.",
            "is sweeping wike a wittle baby.",
            "is dreaming of diamonds.",
            "is sleeping.",
            "had a long day so going to bed.",
            "is \"just resting their eyes\".",
            "is going mimis.",
            "is laying in bed scrolling the socials.",
            "is going to sleep, recalling all the sticks they took from raens... wait what?",
            "is trying to sleep but probably will only get like 10 seconds of it.",
            "may be sleeping. Maybe not? Who knows...",
            "just needs a wee rest..",
            "is having a nightmare.",
            "fell head first into the bed.",
            "is sleeping despite monsters being nearby.",
            "is making sure Phantoms wont get the better of them.",
            "is afraid of the dark"
    );

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
                MutableText text = playerName.append(Text.literal(" " + this.getSleepMessage(player)).formatted(Formatting.WHITE));
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

    private String getSleepMessage(PlayerEntity player) {
        List<String> filtered = this.sleepMessages.stream()
                .filter(msg -> {
                    if (player.getName().getString().contains("raens")) {
                        return !msg.contains("raens");
                    }
                    return true;
                })
                .toList();
        return filtered.get(random.nextInt(filtered.size()));
    }
}
