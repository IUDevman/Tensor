package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 05-21-2021
 */

@Module.Info(name = "AutoWalk", category = Category.Movement)
public final class AutoWalk extends Module {

    @Override
    public void onTick() {
        this.getMinecraft().options.keyForward.setPressed(true);
    }
}
