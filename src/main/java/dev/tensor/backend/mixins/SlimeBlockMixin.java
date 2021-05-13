package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.block.SlimeBlock;
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

@Mixin(SlimeBlock.class)
public final class SlimeBlockMixin implements Wrapper {

    @Inject(method = "onSteppedOn", at = @At("HEAD"), cancellable = true)
    public void onSteppedOn(World world, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        if (entity != getPlayer()) return;

        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.blocks.getValue()) callbackInfo.cancel();
    }
}
