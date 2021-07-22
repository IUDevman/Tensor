package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = PlayerMoveC2SPacket.class, priority = MixinPriority.VALUE)
public interface PlayerMoveC2SPacketAccessor {

    @Accessor(value = "onGround")
    void setOnGround(boolean onGround);
}
