package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Mixin(Entity.class)
public final class EntityMixin implements Wrapper {

    @Shadow private int entityId;

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    public void pushAwayFrom(Entity entity, CallbackInfo callbackInfo) {
        if (isNull() || entityId != getPlayer().getEntityId()) return;

        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.collisions.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "adjustMovementForPiston", at = @At("HEAD"), cancellable = true)
    public void adjustMovementForPiston(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
        if (isNull() || entityId != getPlayer().getEntityId()) return;

        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.blocks.getValue()) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }

    @Inject(method = "shouldRender(D)Z", at = @At("HEAD"), cancellable = true)
    public void shouldRender(double distance, CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
