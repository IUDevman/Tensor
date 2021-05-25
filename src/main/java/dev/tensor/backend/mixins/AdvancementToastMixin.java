package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.toast.AdvancementToast;
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

@Mixin(value = AdvancementToast.class, priority = Integer.MAX_VALUE)
public final class AdvancementToastMixin implements Wrapper {

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    public void draw(MatrixStack matrices, ToastManager manager, long startTime, CallbackInfoReturnable<Toast.Visibility> cir) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled() && noOverlay.toast.getValue()) {
            cir.setReturnValue(Toast.Visibility.HIDE);
        }
    }
}
