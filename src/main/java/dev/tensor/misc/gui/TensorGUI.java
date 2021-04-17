package dev.tensor.misc.gui;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.mc16.MinecraftGUI;
import dev.tensor.misc.imp.Wrapper;

/**
 * @author IUDevman
 * @since 04-15-2021
 */

public final class TensorGUI extends MinecraftGUI implements Wrapper {

    @Override
    protected ClickGUI getGUI() {
        return null;
    }

    @Override
    protected GUIInterface getInterface() {
        return null;
    }

    @Override
    protected int getScrollSpeed() {
        return 0;
    }
}
