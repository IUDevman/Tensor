package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Module.Info(name = "NoViewBob", category = Category.Render)
public final class NoViewBob extends Module {

    public final BooleanSetting hurtOnly = new BooleanSetting("Hurt Only", false);
}
