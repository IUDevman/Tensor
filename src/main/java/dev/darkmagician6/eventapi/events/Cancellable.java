package dev.darkmagician6.eventapi.events;

/**
 * @author DarkMagician6
 * @since 08-27-2013
 */

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean state);
}
