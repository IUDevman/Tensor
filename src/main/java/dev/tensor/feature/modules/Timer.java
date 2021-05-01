package dev.tensor.feature.modules;

import dev.tensor.backend.events.BeginRenderTickEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "Timer", category = Category.Player)
public final class Timer extends Module {

    public final NumberSetting multiplier = new NumberSetting("Multiplier", 2.0, 0.1, 20.0, 1);

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<BeginRenderTickEvent> beginRenderTickEventListener = new Listener<>(event -> {
        event.setMultiplier(multiplier.getValue());
    });
}
