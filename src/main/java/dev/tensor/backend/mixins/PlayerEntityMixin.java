package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.SafeWalk;
import dev.tensor.misc.imp.Global;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 07-20-2021
 */

@Mixin(value = PlayerEntity.class, priority = MixinPriority.VALUE)
public final class PlayerEntityMixin implements Global {

    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    public void clipAtLedge(CallbackInfoReturnable<Boolean> cir) {
        if (this.isNull()) return;

        SafeWalk safeWalk = Tensor.INSTANCE.MODULE_MANAGER.getModule(SafeWalk.class);

        if (safeWalk != null && safeWalk.isEnabled()) {

            if (!safeWalk.fluids.getValue() && (this.getPlayer().isInLava() || this.getPlayer().isTouchingWater())) return;

            cir.setReturnValue(true);
        }
    }
}
