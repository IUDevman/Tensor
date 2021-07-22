package dev.tensor.backend.mixins.accessors;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Mixin(value = EntityVelocityUpdateS2CPacket.class, priority = Integer.MAX_VALUE)
public interface EntityVelocityUpdateS2CPacketAccessor {

    @Accessor(value = "velocityX")
    void setVelocityX(int velocityX);

    @Accessor(value = "velocityY")
    void setVelocityY(int velocityY);

    @Accessor(value = "velocityZ")
    void setVelocityZ(int velocityZ);
}
