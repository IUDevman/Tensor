package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 07-21-2021
 */

@Module.Info(name = "InventoryMove", category = Category.Movement)
public final class InventoryMove extends Module {

    public final BooleanSetting chatScreen = new BooleanSetting("Chat Screen", false);
    public final BooleanSetting jump = new BooleanSetting("Jump", true);
    public final BooleanSetting sneak = new BooleanSetting("Sneak", true);
}
