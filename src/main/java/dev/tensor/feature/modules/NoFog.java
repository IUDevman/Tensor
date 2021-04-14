package dev.tensor.feature.modules;

import dev.tensor.backend.events.ApplyFogEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.glfw.GLFW;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "NoFog", category = Category.Render, bind = GLFW.GLFW_KEY_G, messages = true)
public final class NoFog extends Module {

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<ApplyFogEvent> applyFogEventListener = new Listener<>(event -> {
        event.cancel();
    });
}
