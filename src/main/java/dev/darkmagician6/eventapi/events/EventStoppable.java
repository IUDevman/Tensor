package dev.darkmagician6.eventapi.events;

/**
 * @author DarkMagician6
 * @since 09-26-2013
 */

public abstract class EventStoppable implements Event {

    private boolean stopped;

    public void stop() {
        this.stopped = true;
    }

    public boolean isStopped() {
        return this.stopped;
    }
}
