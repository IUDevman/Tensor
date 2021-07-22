package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.CameraClip;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Mixin(value = Camera.class, priority = MixinPriority.VALUE)
public final class CameraMixin implements Global {

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    public void clipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        CameraClip cameraClip = Tensor.INSTANCE.MODULE_MANAGER.getModule(CameraClip.class);

        if (cameraClip != null && cameraClip.isEnabled()) {
            cir.setReturnValue(cameraClip.distance.getValue());
        }
    }

    @Inject(method = "isThirdPerson", at = @At("HEAD"), cancellable = true)
    public void isThirdPerson(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = Tensor.INSTANCE.MODULE_MANAGER.getModule(Freecam.class);

        if (freecam != null && freecam.isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getFocusedEntity", at = @At("HEAD"), cancellable = true)
    public void getFocusedEntity(CallbackInfoReturnable<Entity> cir) {
        if (this.isNull()) return;

        Freecam freecam = Tensor.INSTANCE.MODULE_MANAGER.getModule(Freecam.class);

        if (freecam != null && freecam.isEnabled()) {
            cir.setReturnValue(getPlayer());
        }
    }
}
