package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Mixin(value = EntityVelocityUpdateS2CPacket.class, priority = MixinPriority.VALUE)
public interface EntityVelocityUpdateS2CPacketAccessor {

    @Mutable
    @Accessor(value = "velocityX")
    void setVelocityX(int velocityX);

    @Mutable
    @Accessor(value = "velocityY")
    void setVelocityY(int velocityY);

    @Mutable
    @Accessor(value = "velocityZ")
    void setVelocityZ(int velocityZ);
}
