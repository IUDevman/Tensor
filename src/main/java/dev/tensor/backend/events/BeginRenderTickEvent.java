package dev.tensor.backend.events;

import dev.tensor.misc.event.imp.EventCancellable;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

public final class BeginRenderTickEvent extends EventCancellable {

    private double multiplier = 1.00;

    public double getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
