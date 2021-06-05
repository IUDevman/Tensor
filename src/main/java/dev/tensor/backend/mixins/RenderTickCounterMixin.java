package dev.tensor.backend.mixins;

import dev.tensor.backend.events.BeginRenderTickEvent;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = RenderTickCounter.class, priority = Integer.MAX_VALUE)
public final class RenderTickCounterMixin implements Global {

    @Shadow
    public float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J", opcode = Opcodes.PUTFIELD))
    public void beginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        BeginRenderTickEvent beginRenderTickEvent = new BeginRenderTickEvent();

        EventHandler.call(beginRenderTickEvent);

        if (beginRenderTickEvent.getMultiplier() != 1.00) lastFrameDuration *= beginRenderTickEvent.getMultiplier();
    }
}
