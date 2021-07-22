package dev.tensor.backend.mixins.accessors;

import dev.tensor.backend.MixinPriority;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Mixin(value = CloseHandledScreenC2SPacket.class, priority = MixinPriority.VALUE)
public interface CloseHandledScreenC2SAccessor {

    @Accessor(value = "syncId")
    int getSyncId();
}
