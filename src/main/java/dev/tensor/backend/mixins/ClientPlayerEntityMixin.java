package dev.tensor.backend.mixins;

import com.mojang.authlib.GameProfile;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.*;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements Wrapper {

    public ClientPlayerEntityMixin(ClientWorld clientWorld, GameProfile gameProfile) {
        super(clientWorld, gameProfile);
    }

    @Inject(method = "updateNausea", at = @At("HEAD"), cancellable = true)
    public void updateNausea(CallbackInfo callbackInfo) {
        NoPortalEffect noPortalEffect = ModuleManager.INSTANCE.getModule(NoPortalEffect.class);

        if (noPortalEffect.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    public void shouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.sneaking.getValue()) cir.setReturnValue(false);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    public void tickMovement(CallbackInfo callbackInfo) {
        if (isNull()) return;

        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.items.getValue() && getPlayer().isUsingItem()) {
            getPlayer().input.movementForward /= 0.2F;
            getPlayer().input.movementSideways /= 0.2F;
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double d, CallbackInfo callbackInfo) {
        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.blocks.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "isCamera", at = @At("HEAD"), cancellable = true)
    public void isCamera(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (!isNull() && this.equals(getPlayer()) && freecam.isEnabled() && freecam.getCameraEntity() != null) {
            freecam.getCameraEntity().changeLookDirection(cursorDeltaX, cursorDeltaY);
            freecam.getCameraEntity().setHeadYaw(freecam.getCameraEntity().yaw);

        } else {
            super.changeLookDirection(cursorDeltaX, cursorDeltaY);
        }
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean isTouchingWater() {
        Flight flight = ModuleManager.INSTANCE.getModule(Flight.class);

        if (!isNull() && this.equals(getPlayer()) && flight.isEnabled() && flight.ignoreFluids.getValue()) {
            return false;
        } else {
            return super.isTouchingWater();
        }
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Override
    public boolean isInLava() {
        Flight flight = ModuleManager.INSTANCE.getModule(Flight.class);

        if (!isNull() && this.equals(getPlayer()) && flight.isEnabled() && flight.ignoreFluids.getValue()) {
            return false;
        } else {
            return super.isInLava();
        }
    }
}
