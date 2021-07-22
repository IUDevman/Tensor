package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = ChatMessageC2SPacket.class, priority = MixinPriority.VALUE)
public interface ChatMessageC2SPacketAccessor {

    @Accessor(value = "chatMessage")
    void setChatMessage(String chatMessage);
}
