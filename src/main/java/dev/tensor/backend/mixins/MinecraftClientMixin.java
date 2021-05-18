package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.feature.managers.ConfigManager;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Mixin(MinecraftClient.class)
public final class MinecraftClientMixin implements Wrapper {

    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    public void getWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Tensor.INSTANCE.MOD_NAME + " " + Tensor.INSTANCE.MOD_VERSION + " (" + cir.getReturnValue() + ")");
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V", ordinal = 0, shift = At.Shift.AFTER))
    public void tick(CallbackInfo callbackInfo) {
        if (isNull()) return;

        Tensor.INSTANCE.EVENT_BUS.post(new ClientTickEvent());
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void stop(CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.save();
    }

    @Inject(method = "cleanUpAfterCrash", at = @At("HEAD"))
    public void cleanUpAfterCrash(CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.save();
    }

    @Inject(method = "disconnect()V", at = @At("HEAD"))
    public void disconnect(CallbackInfo callbackInfo) {
        Tensor.INSTANCE.EVENT_BUS.post(new DisconnectEvent());
    }
}
