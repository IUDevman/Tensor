package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.misc.gui.TensorChatGUI;
import dev.tensor.misc.gui.TensorGUI;
import dev.tensor.misc.imp.Manager;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

public final class GUIManager implements Manager {

    private final TensorGUI tensorGUI = new TensorGUI();
    private final TensorChatGUI tensorChatGUI = new TensorChatGUI();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("GUIManager");
    }

    public TensorGUI getGUI() {
        return this.tensorGUI;
    }

    public TensorChatGUI getTensorChatGUI() {
        return this.tensorChatGUI;
    }
}
