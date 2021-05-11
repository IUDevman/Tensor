package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.CameraClip;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Mixin(Camera.class)
public final class CameraMixin implements Wrapper {

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    public void clipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        CameraClip cameraClip = ModuleManager.INSTANCE.getModule(CameraClip.class);

        if (cameraClip.isEnabled()) {
            cir.setReturnValue(cameraClip.distance.getValue());
            cir.cancel();
        }
    }
}
