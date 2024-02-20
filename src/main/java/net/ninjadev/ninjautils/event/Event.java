package net.ninjadev.ninjautils.event;

import net.ninjadev.ninjautils.NinjaUtils;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class Event<E extends Event<E, T>, T> {

    protected Map<Object, List<Consumer<T>>> listeners;
    protected boolean cancellable = false;

    public Event() {
        this(false);
    }

    public Event(boolean cancellable) {
        this.listeners = Collections.synchronizedMap(new LinkedHashMap<>());
        this.cancellable = cancellable;
    }

    public T invoke(T data) {
        this.listeners.values()
                .stream()
                .flatMap(List::stream)
                .forEach(consumer -> {
                    try {
                        consumer.accept(data);
                    } catch (Exception e) {
                        NinjaUtils.LOG.error("Error invoking event {}: {}", this.getClass().getSimpleName(), e.getMessage());
                    }
                });
        return data;
    }

    public E register(Object reference, Consumer<T> listener) {
        this.listeners.computeIfAbsent(reference, r -> new ArrayList<>()).add(listener);
        return (E) this;
    }

    public E release(Object reference) {
        this.listeners.remove(reference);
        return (E)this;
    }

    public void releaseAll() {
        this.listeners.clear();
    }
}
