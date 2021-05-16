package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Mixin(Entity.class)
public final class EntityMixin implements Wrapper {

    @Shadow private int entityId;

    @Redirect(method = "updateMovementInFluid(Lnet/minecraft/tag/Tag;D)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    public void updateMovementInFluid(Entity entity, Vec3d velocity) {
        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (entity != getPlayer() || !noPush.isEnabled() || noPush.isEnabled() && !noPush.water.getValue()) {
            entity.setVelocity(velocity);
        }
    }

    @Redirect(method = "pushAwayFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void pushAwayFrom(Entity entity, double deltaX, double deltaY, double deltaZ) {
        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (entity != getPlayer() || !noPush.isEnabled() || noPush.isEnabled() && !noPush.collisions.getValue()) {
            entity.addVelocity(deltaX, deltaY, deltaZ);
        }
    }

    @Inject(method = "adjustMovementForPiston", at = @At("HEAD"), cancellable = true)
    public void adjustMovementForPiston(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
        if (entityId != getPlayer().getEntityId()) return;

        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.blocks.getValue()) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }
}
