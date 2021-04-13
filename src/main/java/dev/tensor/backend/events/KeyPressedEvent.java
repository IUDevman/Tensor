package dev.tensor.backend.events;

import dev.tensor.misc.imp.Event;

/**
 * @author Hoosiers
 * @since 04-13-2021
 */

public final class KeyPressedEvent extends Event {

    private final int bind;

    public KeyPressedEvent(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return this.bind;
    }
}
