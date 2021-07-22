package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.FullBright;
import dev.tensor.misc.imp.Global;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author A2H#6255
 * Added with explicit permission, thanks! :D
 */

@Mixin(value = DimensionType.class, priority = Integer.MAX_VALUE)
public final class DimensionTypeMixin implements Global {

    @Inject(method = "method_28516", at = @At("HEAD"), cancellable = true)
    public void method_28516(int i, CallbackInfoReturnable<Float> cir) {
        FullBright fullBright = Tensor.INSTANCE.MODULE_MANAGER.getModule(FullBright.class);

        if (fullBright != null && fullBright.isEnabled()) {
            cir.setReturnValue(1F);
        }
    }
}
