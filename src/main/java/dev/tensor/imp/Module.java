package dev.tensor.imp;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public abstract class Module implements Wrapper {

    private boolean enabled = false;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void onTick() {

    }
}
