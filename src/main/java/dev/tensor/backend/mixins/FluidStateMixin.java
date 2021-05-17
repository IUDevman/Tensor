package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoPush;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidState.class)
public final class FluidStateMixin implements Wrapper {

    @Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    public void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        if (isNull() || getPlayer().squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) > 1) return;

        NoPush noPush = ModuleManager.INSTANCE.getModule(NoPush.class);

        if (noPush.isEnabled() && noPush.water.getValue()) cir.setReturnValue(Vec3d.ZERO);
    }
}
