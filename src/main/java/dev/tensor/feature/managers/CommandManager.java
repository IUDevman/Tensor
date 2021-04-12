package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.imp.Manager;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum CommandManager implements Manager {

    INSTANCE;

    @Override
    public void load() {
        Tensor.LOGGER.info("CommandManager");
    }
}
