package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Mixin(EntityVelocityUpdateS2CPacket.class)
public interface EntityVelocityUpdateS2CPacketAccessor {

    @Accessor
    void setVelocityX(int velocityX);

    @Accessor
    void setVelocityY(int velocityY);

    @Accessor
    void setVelocityZ(int velocityZ);
}