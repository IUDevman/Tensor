package dev.tensor.backend.events;

import dev.tensor.misc.imp.Event;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

public final class BeginRenderTickEvent extends Event {

    private double multiplier = 1.00;

    public double getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
