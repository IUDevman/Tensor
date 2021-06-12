package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Module.Info(name = "Sneak", category = Category.Movement)
public final class Sneak extends Module {

    @Override
    public void onDisable() {
        if (!this.getPlayer().isSneaking()) return;

        this.getMinecraft().options.keySneak.setPressed(false);
    }

    @Override
    public void onTick() {
        if (this.getPlayer().isSneaking()) return;

        this.getMinecraft().options.keySneak.setPressed(true);
    }
}
