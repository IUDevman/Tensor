package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.backend.events.*;
import dev.tensor.misc.imp.Manager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum EventManager implements Manager {

    INSTANCE;

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("EventManager");
    }

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<ClientTickEvent> clientTickEventListener = new Listener<>(event -> {
        if (isNull()) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) module.onTick();
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<ClientRenderEvent> clientRenderEventListener = new Listener<>(event -> {
        if (isNull()) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.isEnabled()) {
                switch (event.getType()) {
                    case World: {
                        module.onRender3D();
                        break;
                    }
                    case HUD: {
                        module.onRender2D(new MatrixStack());
                        break;
                    }
                    default: break;
                }
            }
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<KeyPressedEvent> keyPressedEventListener = new Listener<>(event -> {
        if (isNull()) return;

        ModuleManager.INSTANCE.getModules().forEach(module -> {
            if (module.getBind() == event.getBind()) module.toggle();
        });
    });

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent> packetSendEventListener = new Listener<>(event -> {
        if (isNull() || !event.getType().equals(PacketEvent.Type.Send)) return;

        if (event.getPacket() instanceof ChatMessageC2SPacket) {

            ChatMessageC2SPacket chatMessageC2SPacket = (ChatMessageC2SPacket) event.getPacket();

            if (chatMessageC2SPacket.getChatMessage().startsWith(CommandManager.INSTANCE.getPrefix())) {
                event.cancel();
                CommandManager.INSTANCE.dispatchCommands(chatMessageC2SPacket.getChatMessage().substring(1));
            }
        }
    });
}
