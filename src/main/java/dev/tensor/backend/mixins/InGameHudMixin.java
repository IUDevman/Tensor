package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Mixin(InGameHud.class)
public final class InGameHudMixin implements Wrapper {

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    public void renderPumpkinOverlay(CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    public void renderStatusEffectOverlay(MatrixStack matrices, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    public void renderVignetteOverlay(Entity entity, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled()) callbackInfo.cancel();
    }
}
