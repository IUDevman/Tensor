package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.Client2DRenderEvent;
import dev.tensor.backend.events.Client3DRenderEvent;
import dev.tensor.imp.Wrapper;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Mixin(GameRenderer.class)
public final class GameRendererMixin implements Wrapper {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    public void render(CallbackInfo callbackInfo) {
        Tensor.EVENT_BUS.post(new Client2DRenderEvent());
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    public void renderHand(CallbackInfo callbackInfo) {
        Tensor.EVENT_BUS.post(new Client3DRenderEvent());
    }
}
