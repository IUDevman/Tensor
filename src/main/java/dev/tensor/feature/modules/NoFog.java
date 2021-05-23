package dev.tensor.feature.modules;

import dev.darkmagician6.eventapi.EventTarget;
import dev.tensor.backend.events.ApplyFogEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Module.Info(name = "NoFog", category = Category.Render)
public final class NoFog extends Module {

    @SuppressWarnings("unused")
    @EventTarget
    public void onApplyFog(ApplyFogEvent event) {
        event.setCancelled(true);
    }
}
