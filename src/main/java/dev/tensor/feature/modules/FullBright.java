package dev.tensor.feature.modules;

import dev.tensor.imp.Category;
import dev.tensor.imp.Module;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Module.Info(name = "FullBright", category = Category.Render, enabled = true)
public final class FullBright extends Module {

    private double oldFloat = -1;

    public void onEnable() {
        oldFloat = getMinecraft().options.gamma;
    }

    public void onDisable() {
        if (oldFloat != -1) getMinecraft().options.gamma = oldFloat;
    }

    public void onTick() {
        if (getMinecraft().options.gamma < 100) {
            getMinecraft().options.gamma++;
        }
    }
}
