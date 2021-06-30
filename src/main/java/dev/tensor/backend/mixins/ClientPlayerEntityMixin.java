package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoPortalEffect;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(value = ClientPlayerEntity.class, priority = Integer.MAX_VALUE)
public final class ClientPlayerEntityMixin implements Global {

    @Inject(method = "updateNausea", at = @At("HEAD"), cancellable = true)
    public void updateNausea(CallbackInfo callbackInfo) {
        NoPortalEffect noPortalEffect = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoPortalEffect.class);

        if (noPortalEffect.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    public void shouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        NoSlow noSlow = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.sneaking.getValue()) cir.setReturnValue(false);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    public void tickMovement(CallbackInfo callbackInfo) {
        if (this.isNull()) return;

        NoSlow noSlow = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.items.getValue() && this.getPlayer().isUsingItem()) {
            this.getPlayer().input.movementForward /= 0.2F;
            this.getPlayer().input.movementSideways /= 0.2F;
        }
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double d, CallbackInfo callbackInfo) {
        NoPush noPush = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.blocks.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "isCamera", at = @At("HEAD"), cancellable = true)
    public void isCamera(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = Tensor.INSTANCE.MODULE_MANAGER.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(true);
        }
    }
}
