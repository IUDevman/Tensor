package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.backend.events.ClientTickEvent;
import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.feature.modules.UnfocusedCPU;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Mixin(value = MinecraftClient.class, priority = MixinPriority.VALUE)
public abstract class MinecraftClientMixin implements Global {

    @Shadow
    private boolean windowFocused;

    @Shadow
    public abstract Profiler getProfiler();

    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true)
    public void getFramerateLimit(CallbackInfoReturnable<Integer> cir) {
        UnfocusedCPU unfocusedCPU = Tensor.INSTANCE.MODULE_MANAGER.getModule(UnfocusedCPU.class);

        if (unfocusedCPU != null && unfocusedCPU.isEnabled() && !this.windowFocused) {
            cir.setReturnValue(1);
        }
    }

    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    public void getWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(Tensor.INSTANCE.MOD_NAME + " " + Tensor.INSTANCE.MOD_VERSION + " (" + cir.getReturnValue() + ")");
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo callbackInfo) {
        if (this.isNull()) return;

        this.getProfiler().push(Tensor.INSTANCE.MOD_NAME);
        Tensor.INSTANCE.EVENT_HANDLER.call(new ClientTickEvent());
        this.getProfiler().pop();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void stop(CallbackInfo callbackInfo) {
        Tensor.INSTANCE.CONFIG_MANAGER.save();
    }

    @Inject(method = "cleanUpAfterCrash", at = @At("HEAD"))
    public void cleanUpAfterCrash(CallbackInfo callbackInfo) {
        Tensor.INSTANCE.CONFIG_MANAGER.save();
    }

    @Inject(method = "disconnect()V", at = @At("HEAD"))
    public void disconnect(CallbackInfo callbackInfo) {
        Tensor.INSTANCE.EVENT_HANDLER.call(new DisconnectEvent());
    }
}
