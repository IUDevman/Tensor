package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.event.imp.Priority;
import dev.tensor.misc.imp.Manager;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum EventManager implements Manager {

    INSTANCE;

    private final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("EventHandler");
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGHEST)
    public void onClientTick(ClientTickEvent event) {
        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) this.threadPoolExecutor.execute(module::onTick);
        });
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGH)
    public void onClientRender(ClientRenderEvent event) {
        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) {
                switch (event.getType()) {
                    case World: {
                        module.onRender3D();
                        break;
                    }
                    case HUD: {
                        module.onRender2D();
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onKeyPressed(KeyPressedEvent event) {
        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.getBind() == event.getBind()) module.toggle();
        });
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (!event.getType().equals(PacketEvent.Type.Send)) return;

        if (event.getPacket() instanceof ChatMessageC2SPacket) {

            ChatMessageC2SPacket chatMessageC2SPacket = (ChatMessageC2SPacket) event.getPacket();

            if (chatMessageC2SPacket.getChatMessage().startsWith(CommandManager.INSTANCE.getPrefix())) {
                event.setCancelled(true);
                CommandManager.INSTANCE.dispatchCommands(chatMessageC2SPacket.getChatMessage().substring(1));
            }
        }
    }
}
