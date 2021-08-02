package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.VanillaSpoof;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 08-02-2021
 */

@Mixin(value = ClientBrandRetriever.class, priority = MixinPriority.VALUE)
public final class ClientBrandRetrieverMixin implements Global {

    @Inject(method = "getClientModName", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getClientModName(CallbackInfoReturnable<String> cir) {
        VanillaSpoof vanillaSpoof = Tensor.INSTANCE.MODULE_MANAGER.getModule(VanillaSpoof.class);

        if (vanillaSpoof != null && vanillaSpoof.isEnabled()) {
            cir.setReturnValue("vanilla");
        }
    }
}
