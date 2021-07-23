package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import org.lwjgl.glfw.GLFW;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

@Module.Info(name = "ClickGUI", category = Category.Client, bind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public final class ClickGUI extends Module {

    public final BooleanSetting showHUDComponents = new BooleanSetting("Show HUD Components", true);

    @Override
    public void onEnable() {
        this.getMinecraft().setScreen(Tensor.INSTANCE.GUI_MANAGER.getGUI());
        this.disable();
    }
}
