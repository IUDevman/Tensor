package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.backend.events.Client2DRenderEvent;
import dev.tensor.backend.events.Client3DRenderEvent;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.imp.Manager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum EventManager implements Manager {

    INSTANCE;

    @Override
    public void load() {
        Tensor.LOGGER.info("EventManager");
    }

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<ClientTickEvent> clientTickEventListener = new Listener<>(event -> {
        if (getPlayer() == null || getWorld() == null) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) module.onTick();
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<Client2DRenderEvent> client2DRenderEventListener = new Listener<>(event -> {
        if (getPlayer() == null || getWorld() == null) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) module.onRender2D(); //todo: set up 2d render functions
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<Client3DRenderEvent> client3DRenderEventListener = new Listener<>(event -> {
        if (getPlayer() == null || getWorld() == null) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) module.onRender3D(); //todo: set up 3d render functions
        });
    });
}
