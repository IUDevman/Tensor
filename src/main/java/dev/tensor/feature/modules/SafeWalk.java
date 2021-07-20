package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 07-20-2021
 */

@Module.Info(name = "SafeWalk", category = Category.Movement)
public final class SafeWalk extends Module {

    public final BooleanSetting fluids = new BooleanSetting("Fluids", false);
}
