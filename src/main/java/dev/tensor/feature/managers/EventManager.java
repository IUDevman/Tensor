package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.feature.modules.ClickGUI;
import dev.tensor.feature.modules.Commands;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.event.imp.Priority;
import dev.tensor.misc.gui.TensorGUI;
import dev.tensor.misc.imp.HUDComponent;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class EventManager implements Manager {

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("EventHandler");
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGHEST)
    public void onClientTick(ClientTickEvent event) {
        Tensor.INSTANCE.MODULE_MANAGER.getEnabledModules().forEach(Module::onTick);
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGHEST)
    public void onClientRender(ClientRenderEvent event) {
        Tensor.INSTANCE.MODULE_MANAGER.getEnabledModules().forEach(module -> {
            if (event.getType().equals(ClientRenderEvent.Type.World)) {
                module.onRender3D();
            } else if (event.getType().equals(ClientRenderEvent.Type.HUD) && module instanceof HUDComponent && shouldRender2D()) {
                ((HUDComponent) module).onRender2D(new MatrixStack());
            }
        });
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGHEST)
    public void onKeyPressed(KeyPressedEvent event) {
        Tensor.INSTANCE.MODULE_MANAGER.getModules().forEach(module -> {
            if (module.getBind() == event.getBind()) module.toggle();
        });
    }

    @SuppressWarnings("unused")
    @EventTarget(Priority.HIGHEST)
    public void onPacket(PacketEvent event) {
        if (!event.getType().equals(PacketEvent.Type.Send)) return;

        if (event.getPacket() instanceof ChatMessageC2SPacket chatMessageC2SPacket) {

            if (chatMessageC2SPacket.getChatMessage().startsWith(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix()) && shouldDispatchCommands()) {
                event.setCancelled(true);
                Tensor.INSTANCE.COMMAND_MANAGER.dispatchCommands(chatMessageC2SPacket.getChatMessage().substring(1));
            }
        }
    }

    private boolean shouldRender2D() {
        ClickGUI clickGUI = Tensor.INSTANCE.MODULE_MANAGER.getModule(ClickGUI.class);

        if (clickGUI == null) return false;

        return !(this.getMinecraft().currentScreen instanceof TensorGUI) || clickGUI.showHUDComponents.getValue();
    }

    private boolean shouldDispatchCommands() {
        Commands commands = Tensor.INSTANCE.MODULE_MANAGER.getModule(Commands.class);

        if (commands == null) return false;

        return commands.isEnabled();
    }
}
