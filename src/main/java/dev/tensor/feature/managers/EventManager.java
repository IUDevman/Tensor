package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.imp.Manager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
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
        Tensor.INSTANCE.LOGGER.info("EventManager");
    }

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<ClientTickEvent> clientTickEventListener = new Listener<>(event -> {
        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) this.threadPoolExecutor.execute(module::onTick);
        });
    });

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<ClientRenderEvent> clientRenderEventListener = new Listener<>(event -> {
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
    });

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<KeyPressedEvent> keyPressedEventListener = new Listener<>(event -> {
        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.getBind() == event.getBind()) module.toggle();
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent> packetSendEventListener = new Listener<>(event -> {
        if (!event.getType().equals(PacketEvent.Type.Send)) return;

        if (event.getPacket() instanceof ChatMessageC2SPacket) {

            ChatMessageC2SPacket chatMessageC2SPacket = (ChatMessageC2SPacket) event.getPacket();

            if (chatMessageC2SPacket.getChatMessage().startsWith(CommandManager.INSTANCE.getPrefix())) {
                event.cancel();
                CommandManager.INSTANCE.dispatchCommands(chatMessageC2SPacket.getChatMessage().substring(1));
            }
        }
    });
}
