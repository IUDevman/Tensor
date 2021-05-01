package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.BeginRenderTickEvent;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Mixin(RenderTickCounter.class)
public final class RenderTickCounterMixin implements Wrapper {

    @Shadow public float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J", opcode = Opcodes.PUTFIELD))
    public void beginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        BeginRenderTickEvent beginRenderTickEvent = new BeginRenderTickEvent();

        Tensor.INSTANCE.EVENT_BUS.post(beginRenderTickEvent);

        if (beginRenderTickEvent.getMultiplier() != 1.00) lastFrameDuration *= beginRenderTickEvent.getMultiplier();
    }
}
