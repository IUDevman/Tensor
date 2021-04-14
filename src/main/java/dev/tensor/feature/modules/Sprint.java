package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import org.lwjgl.glfw.GLFW;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "Sprint", category = Category.Movement, bind = GLFW.GLFW_KEY_U, messages = true)
public final class Sprint extends Module {

    public BooleanSetting stationary = new BooleanSetting("Stationary", false);
    public BooleanSetting reverse = new BooleanSetting("Reverse", true);
    public BooleanSetting sideways = new BooleanSetting("Sideways", true);

    public void onDisable() {
        getPlayer().setSprinting(false);
    }

    public void onTick() {
        if (!stationary.getValue() && getPlayer().forwardSpeed == 0 && getPlayer().sidewaysSpeed == 0) return;
        else if (!sideways.getValue() && getPlayer().sidewaysSpeed != 0) return;
        else if (!reverse.getValue() && getPlayer().forwardSpeed < 0) return;

        getPlayer().setSprinting(true);
    }
}
