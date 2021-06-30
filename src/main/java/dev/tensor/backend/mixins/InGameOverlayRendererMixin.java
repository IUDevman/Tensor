package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Mixin(value = InGameOverlayRenderer.class, priority = Integer.MAX_VALUE)
public final class InGameOverlayRendererMixin implements Global {

    @Inject(method = "renderOverlays", at = @At("HEAD"), cancellable = true)
    private static void renderOverlays(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoOverlay.class);

        if (noOverlay != null && noOverlay.isEnabled()) callbackInfo.cancel();
    }
}
