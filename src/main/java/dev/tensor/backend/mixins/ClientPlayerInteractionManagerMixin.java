package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.events.BlockInteractEvent;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

@Mixin(ClientPlayerInteractionManager.class)
public final class ClientPlayerInteractionManagerMixin implements Wrapper {

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"))
    public void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Tensor.INSTANCE.EVENT_BUS.post(new BlockInteractEvent(BlockInteractEvent.Type.Damage, pos));
    }

    @Inject(method = "breakBlock", at = @At("HEAD"))
    public void breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Tensor.INSTANCE.EVENT_BUS.post(new BlockInteractEvent(BlockInteractEvent.Type.Break, pos));
    }
}
