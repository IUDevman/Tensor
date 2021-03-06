package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-25-2021
 */

@Mixin(value = RecipeToast.class, priority = MixinPriority.VALUE)
public final class RecipeToastMixin implements Global {

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    public void draw(MatrixStack matrices, ToastManager manager, long startTime, CallbackInfoReturnable<Toast.Visibility> cir) {
        NoOverlay noOverlay = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoOverlay.class);

        if (noOverlay != null && noOverlay.isEnabled() && noOverlay.toast.getValue()) {
            cir.setReturnValue(Toast.Visibility.HIDE);
        }
    }
}
