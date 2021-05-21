package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.CameraClip;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.misc.imp.Wrapper;
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

@Mixin(value = Camera.class, priority = Integer.MAX_VALUE)
public final class CameraMixin implements Wrapper {

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    public void clipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        CameraClip cameraClip = ModuleManager.INSTANCE.getModule(CameraClip.class);

        if (cameraClip.isEnabled()) {
            cir.setReturnValue(cameraClip.distance.getValue());
        }
    }

    @Inject(method = "isThirdPerson", at = @At("HEAD"), cancellable = true)
    public void isThirdPerson(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getFocusedEntity", at = @At("HEAD"), cancellable = true)
    public void getFocusedEntity(CallbackInfoReturnable<Entity> cir) {
        if (isNull()) return;

        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(getPlayer());
        }
    }
}
