package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.NoFog;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = BackgroundRenderer.class, priority = MixinPriority.VALUE)
public final class BackgroundRendererMixin implements Global {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo callbackInfo) {
        NoFog noFog = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoFog.class);

        if (noFog != null && noFog.isEnabled()) {
            callbackInfo.cancel();
        }
    }
}
