package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Mixin(value = ExplosionS2CPacket.class, priority = MixinPriority.VALUE)
public interface ExplosionS2CPacketAccessor {

    @Mutable
    @Accessor(value = "playerVelocityX")
    void setPlayerVelocityX(float playerVelocityX);

    @Mutable
    @Accessor(value = "playerVelocityY")
    void setPlayerVelocityY(float playerVelocityY);

    @Mutable
    @Accessor(value = "playerVelocityZ")
    void setPlayerVelocityZ(float playerVelocityZ);
}
