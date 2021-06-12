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

    @Override
    public void onTick() {
        if (!stationary.getValue() && this.getPlayer().forwardSpeed == 0 && this.getPlayer().sidewaysSpeed == 0) return;
        else if (!sideways.getValue() && this.getPlayer().sidewaysSpeed != 0) return;
        else if (!reverse.getValue() && this.getPlayer().forwardSpeed < 0) return;
        else if (!liquids.getValue() && (this.getPlayer().isInsideWaterOrBubbleColumn() || this.getPlayer().isInLava())) return;

        this.getPlayer().setSprinting(true);
    }
}
