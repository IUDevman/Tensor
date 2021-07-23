package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Mixin(value = InGameHud.class, priority = MixinPriority.VALUE)
public final class InGameHudMixin implements Global {

    @Shadow
    @Final
    private static Identifier PUMPKIN_BLUR;

    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE;

    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    public void render(Identifier texture, float opacity, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoOverlay.class);

        if (noOverlay != null && noOverlay.isEnabled()) {
            if ((noOverlay.pumpkin.getValue() && texture == PUMPKIN_BLUR) || (noOverlay.powderedSnow.getValue() && texture == POWDER_SNOW_OUTLINE)) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    public void renderStatusEffectOverlay(MatrixStack matrices, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoOverlay.class);

        if (noOverlay != null && noOverlay.isEnabled() && noOverlay.status.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    public void renderVignetteOverlay(Entity entity, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = Tensor.INSTANCE.MODULE_MANAGER.getModule(NoOverlay.class);

        if (noOverlay != null && noOverlay.isEnabled() && noOverlay.vignette.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    public void getCameraPlayer(CallbackInfoReturnable<PlayerEntity> cir) {
        if (this.isNull()) return;

        Freecam freecam = Tensor.INSTANCE.MODULE_MANAGER.getModule(Freecam.class);

        if (freecam != null && freecam.isEnabled()) {
            cir.setReturnValue(this.getPlayer());
        }
    }
}
