package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatMessageC2SPacket.class)
public interface ChatMessageC2SPacketAccessor {

    @Accessor
    void setChatMessage(String chatMessage);
}
