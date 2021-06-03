package dev.tensor.feature.modules;

import dev.tensor.backend.events.BeginRenderTickEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "Timer", category = Category.Movement)
public final class Timer extends Module {

    public final NumberSetting multiplier = new NumberSetting("Multiplier", 2.0, 0.1, 20.0, 1);

    @SuppressWarnings("unused")
    @EventTarget
    public void onBeginRenderTick(BeginRenderTickEvent event) {
        event.setMultiplier(multiplier.getValue());
    }
}
