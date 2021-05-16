package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoRender;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-15-2021
 */

@Mixin(ArmorFeatureRenderer.class)
public final class ArmorFeatureRendererMixin implements Wrapper {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public <T extends LivingEntity> void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo callbackInfo) {
        NoRender noRender = ModuleManager.INSTANCE.getModule(NoRender.class);

        if (noRender.isEnabled() && noRender.armor.getValue()) callbackInfo.cancel();
    }
}
