package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoPortalEffect;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Wrapper;
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

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements Wrapper {

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
}
