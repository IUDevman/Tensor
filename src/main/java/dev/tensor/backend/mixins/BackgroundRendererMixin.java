package dev.tensor.backend.mixins;

import dev.tensor.backend.events.ApplyFogEvent;
import dev.tensor.misc.event.EventHandler;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 04-14-2021
 */

@Mixin(value = BackgroundRenderer.class, priority = Integer.MAX_VALUE)
public final class BackgroundRendererMixin implements Wrapper {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo callbackInfo) {
        ApplyFogEvent applyFogEvent = new ApplyFogEvent();

        EventHandler.call(applyFogEvent);

        if (applyFogEvent.isCancelled()) callbackInfo.cancel();
    }
}
