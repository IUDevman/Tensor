package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.*;
import dev.tensor.misc.imp.Global;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Mixin(value = Entity.class, priority = Integer.MAX_VALUE)
public abstract class EntityMixin implements Global {

    @Shadow
    private int entityId;

    @Shadow
    public abstract Vec3d getCameraPosVec(float tickDelta);

    @Shadow
    public abstract Vec3d getRotationVec(float tickDelta);

    @Shadow
    public abstract Text getName();

    @Shadow
    protected boolean firstUpdate;

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
        }
    }

    @Inject(method = "raycast", at = @At("HEAD"), cancellable = true)
    public void raycast(double maxDistance, float tickDelta, boolean includeFluids, CallbackInfoReturnable<HitResult> cir) {
        if (isNull() || entityId != Objects.requireNonNull(getMinecraft().getCameraEntity()).getEntityId() || getPlayer().isSubmergedInWater()) return;

        LiquidInteract liquidInteract = ModuleManager.INSTANCE.getModule(LiquidInteract.class);

        if (liquidInteract.isEnabled()) {
            Vec3d vec3d = this.getCameraPosVec(tickDelta);
            Vec3d vec3d2 = this.getRotationVec(tickDelta);
            Vec3d vec3d3 = vec3d.add(vec3d2.getX() * maxDistance, vec3d2.getY() * maxDistance, vec3d2.getZ() * maxDistance);
            cir.setReturnValue(getWorld().raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, getMinecraft().getCameraEntity())));
        }
    }

    @Inject(method = "isTouchingWater", at = @At("HEAD"), cancellable = true)
    public void isTouchingWater(CallbackInfoReturnable<Boolean> cir) {
        if (isNull() || entityId != getPlayer().getEntityId()) return;

        Flight flight = ModuleManager.INSTANCE.getModule(Flight.class);
        ElytraFlight elytraFlight = ModuleManager.INSTANCE.getModule(ElytraFlight.class);

        if ((flight.isEnabled() && flight.ignoreFluids.getValue()) || (elytraFlight.isEnabled() && elytraFlight.ignoreFluids.getValue() && getPlayer().isFallFlying())) {
            cir.setReturnValue(firstUpdate);
        }
    }

    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
    public void isInLava(CallbackInfoReturnable<Boolean> cir) {
        if (isNull() || entityId != getPlayer().getEntityId()) return;

        Flight flight = ModuleManager.INSTANCE.getModule(Flight.class);
        ElytraFlight elytraFlight = ModuleManager.INSTANCE.getModule(ElytraFlight.class);

        if ((flight.isEnabled() && flight.ignoreFluids.getValue()) || (elytraFlight.isEnabled() && elytraFlight.ignoreFluids.getValue() && getPlayer().isFallFlying())) {
            cir.setReturnValue(firstUpdate);
        }
    }

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo callbackInfo) {
        if (isNull() || entityId != getPlayer().getEntityId()) return;

        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled() && freecam.getCameraEntity() != null) {
            freecam.getCameraEntity().changeLookDirection(cursorDeltaX, cursorDeltaY);
            freecam.getCameraEntity().setHeadYaw(freecam.getCameraEntity().yaw);
            callbackInfo.cancel();
        }
    }
}
