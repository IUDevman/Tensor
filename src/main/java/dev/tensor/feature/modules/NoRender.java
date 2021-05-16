package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 05-15-2021
 */

@Module.Info(name = "NoRender", category = Category.Render)
public final class NoRender extends Module {

    public final BooleanSetting armor = new BooleanSetting("Armor", false);
    public final BooleanSetting enchantBooks = new BooleanSetting("Enchantment Table Books", false);
}
