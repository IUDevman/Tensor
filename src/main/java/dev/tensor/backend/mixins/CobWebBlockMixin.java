package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Global;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-12-2021
 */

@Mixin(value = CobwebBlock.class, priority = MixinPriority.VALUE)
public final class CobWebBlockMixin implements Global {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (this.isNull() || entity != this.getPlayer()) return;

        NoSlow noSlow = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoSlow.class);

        if (noSlow != null && noSlow.isEnabled() && noSlow.sticky.getValue()) ci.cancel();
    }
}
