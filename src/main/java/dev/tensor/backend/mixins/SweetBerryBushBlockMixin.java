package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-09-2021
 */

@Mixin(SweetBerryBushBlock.class)
public final class SweetBerryBushBlockMixin implements Wrapper {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        if (isNull() || entity != getPlayer()) return;

        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.sticky.getValue()) callbackInfo.cancel();
    }
}
