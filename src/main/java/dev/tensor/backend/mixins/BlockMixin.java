package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(value = Block.class, priority = Integer.MAX_VALUE)
public final class BlockMixin implements Wrapper {

    @Inject(method = "getVelocityMultiplier()F", at = @At("HEAD"), cancellable = true)
    public void getVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.blocks.getValue() && cir.getReturnValueF() < 1F) cir.setReturnValue(1F);
    }
}
