package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Mixin(value = InGameHud.class, priority = Integer.MAX_VALUE)
public final class InGameHudMixin implements Global {

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    public void renderPumpkinOverlay(CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled() && noOverlay.pumpkin.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    public void renderStatusEffectOverlay(MatrixStack matrices, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled() && noOverlay.status.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    public void renderVignetteOverlay(Entity entity, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled() && noOverlay.vignette.getValue()) callbackInfo.cancel();
    }

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    public void getCameraPlayer(CallbackInfoReturnable<PlayerEntity> cir) {
        if (this.isNull()) return;

        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (freecam.isEnabled()) {
            cir.setReturnValue(this.getPlayer());
        }
    }
}
