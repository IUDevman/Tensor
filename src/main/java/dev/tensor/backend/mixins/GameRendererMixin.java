package dev.tensor.backend.mixins;

import dev.darkmagician6.eventapi.EventHandler;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoViewBob;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Mixin(value = GameRenderer.class, priority = Integer.MAX_VALUE)
public final class GameRendererMixin implements Wrapper {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void render(CallbackInfo callbackInfo) {
        if (isNull()) return;

        EventHandler.call(new ClientRenderEvent(ClientRenderEvent.Type.HUD));
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    public void renderHand(CallbackInfo callbackInfo) {
        if (isNull()) return;

        EventHandler.call(new ClientRenderEvent(ClientRenderEvent.Type.World));
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    public void bobView(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
        NoViewBob noViewBob = ModuleManager.INSTANCE.getModule(NoViewBob.class);

        if (noViewBob.isEnabled() && !noViewBob.hurtOnly.getValue()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
        NoViewBob noViewBob = ModuleManager.INSTANCE.getModule(NoViewBob.class);

        if (noViewBob.isEnabled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "shouldRenderBlockOutline", at = @At("HEAD"), cancellable = true)
    public void shouldRenderBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled() && freecam.hideBlockOutline.getValue()) cir.setReturnValue(false);
    }
}
