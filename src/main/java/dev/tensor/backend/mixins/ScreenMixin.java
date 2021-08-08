package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.DeathDebug;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 08-07-2021
 */

@Mixin(value = Screen.class, priority = MixinPriority.VALUE)
public final class ScreenMixin implements Global {

    @Inject(method = "keyPressed", at = @At("HEAD"))
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this.isNull() || !this.getPlayer().isDead() || keyCode != InputUtil.GLFW_KEY_F3) return;

        DeathDebug deathDebug = Tensor.INSTANCE.MODULE_MANAGER.getModule(DeathDebug.class);

        if (deathDebug != null && deathDebug.isEnabled()) {
            this.getMinecraft().options.debugEnabled = !this.getMinecraft().options.debugEnabled;
        }
    }
}
