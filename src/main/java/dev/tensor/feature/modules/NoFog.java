package dev.tensor.feature.modules;

import dev.tensor.backend.events.ApplyFogEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "NoFog", category = Category.Render)
public final class NoFog extends Module {

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<ApplyFogEvent> applyFogEventListener = new Listener<>(event -> {
        event.cancel();
    });
}
