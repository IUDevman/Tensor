package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.ViewModel;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = PlayerEntityRenderer.class, priority = Integer.MAX_VALUE)
public final class PlayerEntityRendererMixin implements Global {

    @Inject(method = "renderRightArm", at = @At("HEAD"), cancellable = true)
    public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo callbackInfo) {
        if (this.isNull() || player != this.getPlayer()) return;

        ViewModel viewModel = Tensor.INSTANCE.MODULE_MANAGER.getModule(ViewModel.class);

        if (viewModel != null && viewModel.isEnabled() && !viewModel.renderEmptyMainHand.getValue()) {
            callbackInfo.cancel();
        }
    }
}
