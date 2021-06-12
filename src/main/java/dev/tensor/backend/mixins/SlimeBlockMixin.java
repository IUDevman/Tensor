package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoSlow;
import dev.tensor.misc.imp.Global;
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

@Mixin(value = SlimeBlock.class, priority = Integer.MAX_VALUE)
public final class SlimeBlockMixin implements Global {

    @Inject(method = "onSteppedOn", at = @At("HEAD"), cancellable = true)
    public void onSteppedOn(World world, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        if (this.isNull() || entity != this.getPlayer()) return;

        NoSlow noSlow = ModuleManager.INSTANCE.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.blocks.getValue()) callbackInfo.cancel();
    }
}
