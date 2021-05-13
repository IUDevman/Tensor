package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Module.Info(name = "NoSlow", category = Category.Movement)
public final class NoSlow extends Module {

    public final BooleanSetting items = new BooleanSetting("Items", true);
    public final BooleanSetting blocks = new BooleanSetting("Blocks", true);
    public final BooleanSetting sticky = new BooleanSetting("Sticky", true);
    public final BooleanSetting sneaking = new BooleanSetting("Sneaking", false);
}
