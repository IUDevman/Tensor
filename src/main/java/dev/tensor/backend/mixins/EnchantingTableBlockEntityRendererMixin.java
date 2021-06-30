package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.NoRender;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-15-2021
 */

@Mixin(value = EnchantingTableBlockEntityRenderer.class, priority = Integer.MAX_VALUE)
public final class EnchantingTableBlockEntityRendererMixin implements Global {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(CallbackInfo callbackInfo) {
        NoRender noRender = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoRender.class);

        if (noRender.isEnabled() && noRender.enchantBooks.getValue()) callbackInfo.cancel();
    }
}
