package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoRender;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-15-2021
 */

@Mixin(EnchantingTableBlockEntityRenderer.class)
public final class EnchantingTableBlockEntityRendererMixin implements Wrapper {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(CallbackInfo callbackInfo) {
        NoRender noRender = ModuleManager.INSTANCE.getModule(NoRender.class);

        if (noRender.isEnabled() && noRender.enchantBooks.getValue()) callbackInfo.cancel();
    }
}
