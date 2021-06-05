package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Jesus;
import dev.tensor.misc.imp.Global;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-23-2021
 */

@Mixin(value = FluidBlock.class, priority = Integer.MAX_VALUE)
public final class FluidBlockMixin implements Global {

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (isNull() || getPlayer().isSubmergedInWater() || getPlayer().isSneaking() || getWorld().getBlockState(getPlayer().getBlockPos()).getBlock() instanceof FluidBlock) return;

        Jesus jesus = ModuleManager.INSTANCE.getModule(Jesus.class);

        if (jesus.isEnabled()) {

            if (jesus.cancelOnFall.getValue() && getPlayer().fallDistance >= 3 && !(state.getFluidState().getFluid() instanceof LavaFluid)) {
                return;
            }

            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }
}
