package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Module.Info(name = "NoPush", category = Category.Movement)
public final class NoPush extends Module {

    public final BooleanSetting collisions = new BooleanSetting("Collisions", true);
    public final BooleanSetting blocks = new BooleanSetting("Blocks", true);
    public final BooleanSetting water = new BooleanSetting("Water", true);
}
