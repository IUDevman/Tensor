package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = PlayerMoveC2SPacket.class, priority = Integer.MAX_VALUE)
public interface PlayerMoveC2SPacketAccessor {

    @Accessor
    void setOnGround(boolean onGround);
}
