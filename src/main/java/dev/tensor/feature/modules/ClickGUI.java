package dev.tensor.feature.modules;

import dev.tensor.feature.managers.ClickGUIManager;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import org.lwjgl.glfw.GLFW;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

@Module.Info(name = "ClickGUI", category = Category.Client, bind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public final class ClickGUI extends Module {

    public void onEnable() {
        getMinecraft().openScreen(ClickGUIManager.INSTANCE.getGUI());
        disable();
    }
}
