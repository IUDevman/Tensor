package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.misc.imp.Global;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-16-2021
 */

@Mixin(value = FluidState.class, priority = MixinPriority.VALUE)
public final class FluidStateMixin implements Global {

    @Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    public void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (this.isNull() || this.getPlayer().squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) > 3) return;

        NoPush noPush = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoPush.class);

        if (noPush != null && noPush.isEnabled() && noPush.water.getValue()) cir.setReturnValue(Vec3d.ZERO);
    }
}
