package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.ViewModel;
import dev.tensor.misc.imp.Wrapper;
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

@Mixin(value = HeldItemRenderer.class, priority = Integer.MAX_VALUE)
public final class HeldItemRendererMixin implements Wrapper {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo callbackInfo) {
        if (isNull() || player != getPlayer()) return;

        ViewModel viewModel = ModuleManager.INSTANCE.getModule(ViewModel.class);

        if (viewModel.isEnabled() && hand == Hand.MAIN_HAND) {
            matrices.translate(0, viewModel.vertical.getValue(), viewModel.horizontal.getValue());
            matrices.scale(viewModel.scale.getValue().floatValue(), viewModel.scale.getValue().floatValue(), viewModel.scale.getValue().floatValue());
        }
    }
}
