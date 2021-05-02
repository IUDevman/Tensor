package dev.tensor.backend.events;

import dev.tensor.misc.imp.Event;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

public final class ClientRenderEvent extends Event {

    private final Type type;

    public ClientRenderEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        World,
        HUD
    }
}
