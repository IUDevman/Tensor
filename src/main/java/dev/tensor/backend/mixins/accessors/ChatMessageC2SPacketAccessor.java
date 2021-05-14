package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(ChatMessageC2SPacket.class)
public interface ChatMessageC2SPacketAccessor {

    @Accessor
    void setChatMessage(String chatMessage);
}
