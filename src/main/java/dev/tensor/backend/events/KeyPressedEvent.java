package dev.tensor.backend.events;

import dev.darkmagician6.eventapi.events.EventCancellable;

/**
 * @author IUDevman
 * @since 04-13-2021
 */

public final class KeyPressedEvent extends EventCancellable {

    private final int bind;

    public KeyPressedEvent(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return this.bind;
    }
}
