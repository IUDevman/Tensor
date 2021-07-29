package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.Jesus;
import dev.tensor.misc.imp.Global;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 07-29-2021
 */

@Mixin(value = PowderSnowBlock.class, priority = MixinPriority.VALUE)
public final class PowderSnowBlockMixin implements Global {

    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient minecraft = MinecraftClient.getInstance();

        if (minecraft == null || minecraft.player == null || minecraft.player != entity) return;

        Jesus jesus = Tensor.INSTANCE.MODULE_MANAGER.getModule(Jesus.class);

        if (jesus != null && jesus.isEnabled() && jesus.powderedSnow.getValue()) {

            if (jesus.cancelOnFall.getValue() && minecraft.player.fallDistance >= 3) {
                return;
            }

            cir.setReturnValue(true);
        }
    }
}
