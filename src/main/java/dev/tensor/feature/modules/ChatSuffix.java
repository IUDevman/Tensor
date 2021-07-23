package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.backend.events.PacketEvent;
import dev.tensor.backend.mixins.accessors.ChatMessageC2SPacketAccessor;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.EnumSetting;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Module.Info(name = "ChatSuffix", category = Category.Misc)
public final class ChatSuffix extends Module {

    public final EnumSetting mode = new EnumSetting("Mode", Mode.Regular);

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Send) return;

        if (event.getPacket() instanceof ChatMessageC2SPacket packet) {
            String message = packet.getChatMessage();

            if (message.startsWith(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix()) || message.startsWith("/")) return;

            String newMessage = message + getSuffix();

            if (newMessage.length() > 256) {
                newMessage = newMessage.substring(0, 256);
            }

            ((ChatMessageC2SPacketAccessor) packet).setChatMessage(newMessage);
        }
    }

    private String getSuffix() {
        switch ((Mode) mode.getValue()) {
            case Script: {
                return " \u23D0 \u1d1b\u1d07\u0274\ua731\u1d0f\u0280";
            }
            case Regular: {
                return " | " + Tensor.INSTANCE.MOD_NAME;
            }
            default:
                return "You broke something!";
        }
    }

    public enum Mode {
        Script,
        Regular
    }
}
