package net.ninjadev.ninjautils.util;

public class TickTimer {
    private int ticksRemaining;

    public TickTimer(int startingTicks) {
        this.ticksRemaining = startingTicks;
    }

    public void update() {
        ticksRemaining--;
    }

    public boolean isComplete() {
        return ticksRemaining <= 0;
    }
}
