package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.ViewModel;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = HeldItemRenderer.class, priority = MixinPriority.VALUE)
public final class HeldItemRendererMixin implements Global {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (this.isNull() || player != this.getPlayer()) return;

        ViewModel viewModel = Tensor.INSTANCE.MODULE_MANAGER.getModule(ViewModel.class);

        if (viewModel != null && viewModel.isEnabled() && hand == Hand.MAIN_HAND) {
            matrices.translate(0, viewModel.vertical.getValue(), viewModel.horizontal.getValue());
            matrices.scale(viewModel.scale.getValue().floatValue(), viewModel.scale.getValue().floatValue(), viewModel.scale.getValue().floatValue());
        }
    }
}
