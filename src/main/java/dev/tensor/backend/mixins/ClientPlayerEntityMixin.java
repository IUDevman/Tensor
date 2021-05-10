package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoPortalEffect;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(ClientPlayerEntity.class)
public final class ClientPlayerEntityMixin implements Wrapper {

    @Inject(method = "updateNausea", at = @At("HEAD"), cancellable = true)
    public void updateNausea(CallbackInfo ci) {
        NoPortalEffect noPortalEffect = ModuleManager.INSTANCE.getModule(NoPortalEffect.class);

        if (noPortalEffect.isEnabled()) ci.cancel();
    }
}
