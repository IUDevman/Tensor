package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "Sprint", category = Category.Movement)
public final class Sprint extends Module {

    public final BooleanSetting stationary = new BooleanSetting("Stationary", false);
    public final BooleanSetting reverse = new BooleanSetting("Reverse", true);
    public final BooleanSetting sideways = new BooleanSetting("Sideways", true);
    public final BooleanSetting liquids = new BooleanSetting("Liquids", false);

    public void onDisable() {
        if (isNull()) return;

        getPlayer().setSprinting(false);
    }

    public void onTick() {
        if (!stationary.getValue() && getPlayer().forwardSpeed == 0 && getPlayer().sidewaysSpeed == 0) return;
        else if (!sideways.getValue() && getPlayer().sidewaysSpeed != 0) return;
        else if (!reverse.getValue() && getPlayer().forwardSpeed < 0) return;
        else if (!liquids.getValue() && (getPlayer().isInsideWaterOrBubbleColumn() || getPlayer().isInLava())) return;

        getPlayer().setSprinting(true);
    }
}
