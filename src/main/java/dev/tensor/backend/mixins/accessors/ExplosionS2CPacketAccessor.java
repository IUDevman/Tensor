package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Mixin(value = ExplosionS2CPacket.class, priority = Integer.MAX_VALUE)
public interface ExplosionS2CPacketAccessor {

    @Accessor(value = "playerVelocityX")
    void setPlayerVelocityX(float playerVelocityX);

    @Accessor(value = "playerVelocityY")
    void setPlayerVelocityY(float playerVelocityY);

    @Accessor(value = "playerVelocityZ")
    void setPlayerVelocityZ(float playerVelocityZ);
}
