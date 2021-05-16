package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Module.Info(name = "NoParticles", category = Category.Render)
public final class NoParticles extends Module {

    public final BooleanSetting all = new BooleanSetting("All", false);
    public final BooleanSetting ash = new BooleanSetting("Ash", true);
    public final BooleanSetting spore = new BooleanSetting("Spore", true);
    public final BooleanSetting explosion = new BooleanSetting("Explosion", true);
    public final BooleanSetting underWater = new BooleanSetting("Underwater", true);
    public final BooleanSetting lava = new BooleanSetting("Lava", false);
    public final BooleanSetting portal = new BooleanSetting("Portal", false);
    public final BooleanSetting eating = new BooleanSetting("Item Use Effect", false);
    public final BooleanSetting potions = new BooleanSetting("Potion Effect", false);
}
