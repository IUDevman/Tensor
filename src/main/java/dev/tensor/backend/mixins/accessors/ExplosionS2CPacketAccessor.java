package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(ExplosionS2CPacket.class)
public interface ExplosionS2CPacketAccessor {

    @Accessor
    void setPlayerVelocityX(float playerVelocityX);

    @Accessor
    void setPlayerVelocityY(float playerVelocityY);

    @Accessor
    void setPlayerVelocityZ(float playerVelocityZ);
}
