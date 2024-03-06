package net.ninjadev.ninjautils.util;

public class Cooldown {
    private int ticksRemaining;

    public Cooldown(int startingTicks) {
        this.ticksRemaining = startingTicks;
    }

    public void update() {
        ticksRemaining--;
    }

    public boolean isComplete() {
        return ticksRemaining <= 0;
    }
}
