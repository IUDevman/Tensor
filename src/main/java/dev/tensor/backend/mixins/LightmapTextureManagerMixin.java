package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.FullBright;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Mixin(value = LightmapTextureManager.class, priority = Integer.MAX_VALUE)
public final class LightmapTextureManagerMixin implements Global {

    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    public void getBrightness(World world, int i, CallbackInfoReturnable<Float> cir) {
        FullBright fullBright = Tensor.INSTANCE.MODULE_MANAGER.getModule(FullBright.class);

        if (fullBright != null && fullBright.isEnabled()) {
            cir.setReturnValue(100F);
        }
    }
}
