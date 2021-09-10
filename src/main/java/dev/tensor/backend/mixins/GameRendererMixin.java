package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.backend.events.ClientRenderEvent;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoViewBob;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = GameRenderer.class, priority = MixinPriority.VALUE)
public final class GameRendererMixin implements Global {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void render(CallbackInfo ci) {
        if (this.isNull()) return;

        Tensor.INSTANCE.EVENT_HANDLER.call(new ClientRenderEvent(ClientRenderEvent.Type.HUD));
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    public void renderHand(CallbackInfo ci) {
        if (this.isNull()) return;

        Tensor.INSTANCE.EVENT_HANDLER.call(new ClientRenderEvent(ClientRenderEvent.Type.World));
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    public void bobView(MatrixStack matrixStack, float f, CallbackInfo ci) {
        NoViewBob noViewBob = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoViewBob.class);

        if (noViewBob != null && noViewBob.isEnabled() && !noViewBob.hurtOnly.getValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo ci) {
        NoViewBob noViewBob = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoViewBob.class);

        if (noViewBob != null && noViewBob.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "shouldRenderBlockOutline", at = @At("HEAD"), cancellable = true)
    public void shouldRenderBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        Freecam freecam = Tensor.INSTANCE.MODULE_MANAGER.getModule(Freecam.class);

        if (freecam != null && freecam.isEnabled() && freecam.hideBlockOutline.getValue()) cir.setReturnValue(false);
    }
}
