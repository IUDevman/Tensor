package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.NoGlitchBlock;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Global;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(value = Block.class, priority = MixinPriority.VALUE)
public final class BlockMixin implements Global {

    @Inject(method = "getVelocityMultiplier()F", at = @At("HEAD"), cancellable = true)
    public void getVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        NoSlow noSlow = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoSlow.class);

        if (noSlow != null && noSlow.isEnabled() && noSlow.blocks.getValue() && cir.getReturnValueF() < 1F) {
            cir.setReturnValue(1F);
        }
    }

    @Inject(method = "onBroken", at = @At("RETURN"))
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo callbackInfo) {
        NoGlitchBlock noGlitchBlock = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoGlitchBlock.class);

        if (noGlitchBlock != null && noGlitchBlock.isEnabled()) {
            getNetwork().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));
        }
    }
}
