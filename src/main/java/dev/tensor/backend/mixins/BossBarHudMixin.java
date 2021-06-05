package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.NoOverlay;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 05-14-2021
 */

@Mixin(value = BossBarHud.class, priority = Integer.MAX_VALUE)
public final class BossBarHudMixin implements Global {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(MatrixStack matrices, CallbackInfo callbackInfo) {
        NoOverlay noOverlay = ModuleManager.INSTANCE.getModule(NoOverlay.class);

        if (noOverlay.isEnabled() && noOverlay.bossBar.getValue()) {
            callbackInfo.cancel();
        }
    }
}
