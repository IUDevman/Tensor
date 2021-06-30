package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.gui.TensorGUI;
import dev.tensor.misc.imp.Manager;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

public final class ClickGUIManager implements Manager {

    private final TensorGUI tensorGUI = new TensorGUI();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ClickGUIManager");
    }

    public TensorGUI getGUI() {
        return this.tensorGUI;
    }
}
