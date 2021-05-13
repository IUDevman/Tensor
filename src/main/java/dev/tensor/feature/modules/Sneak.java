package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Module.Info(name = "Sneak", category = Category.Movement)
public final class Sneak extends Module {

    public void onDisable() {
        if (!getPlayer().isSneaking()) return;

        getMinecraft().options.keySneak.setPressed(false);
    }

    public void onTick() {
        if (getPlayer().isSneaking()) return;

        getMinecraft().options.keySneak.setPressed(true);
    }
}
