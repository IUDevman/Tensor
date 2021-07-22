package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.AntiNarrator;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.options.NarratorMode;
import net.minecraft.client.util.NarratorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 07-21-2021
 */

@Mixin(value = NarratorManager.class, priority = Integer.MAX_VALUE)
public final class NarratorManagerMixin implements Global {

    @Inject(method = "getNarratorOption", at = @At("HEAD"), cancellable = true)
    private static void getNarratorOption(CallbackInfoReturnable<NarratorMode> cir) {
        AntiNarrator antiNarrator = Tensor.INSTANCE.MODULE_MANAGER.getModule(AntiNarrator.class);

        if (antiNarrator != null && antiNarrator.isEnabled()) {
            cir.setReturnValue(NarratorMode.OFF);
        }
    }
}
