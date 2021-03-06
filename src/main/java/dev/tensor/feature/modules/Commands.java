package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 07-29-2021
 */

@Module.Info(name = "Commands", category = Category.Client, drawn = false, enabled = true)
public final class Commands extends Module {

    public final BooleanSetting preview = new BooleanSetting("Preview", true);
}
