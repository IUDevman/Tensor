package dev.tensor.feature.modules;

import dev.tensor.misc.gui.TensorGUI;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

@Module.Info(name = "ClickGUI", category = Category.Client)
public final class ClickGUI extends Module {

    public void onEnable() {
        getMinecraft().openScreen(new TensorGUI());
        disable();
    }
}