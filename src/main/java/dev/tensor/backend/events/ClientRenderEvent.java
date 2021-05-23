package dev.tensor.backend.events;

import dev.darkmagician6.eventapi.imp.EventCancellable;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

public final class ClientRenderEvent extends EventCancellable {

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
