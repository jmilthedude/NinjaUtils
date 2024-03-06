package net.ninjadev.ninjautils.event;

public abstract class CancellableEvent<T> extends Event<CancellableEvent<T>, T> {

    public static class Data {
        private boolean cancelled = false;

        public boolean isCancelled() {
            return cancelled;
        }

        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
}
