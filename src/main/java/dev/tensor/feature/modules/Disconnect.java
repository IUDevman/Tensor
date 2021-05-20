package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Module.Info(name = "Disconnect", category = Category.Misc)
public final class Disconnect extends Module {

    @Override
    public void onEnable() {
        getWorld().disconnect();
        disable();
    }
}
