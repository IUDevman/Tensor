package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.backend.mixins.accessors.KeyBindingAccessor;
import dev.tensor.feature.modules.AutoWalk;
import dev.tensor.feature.modules.InventoryMove;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IUDevman
 * @since 07-21-2021
 */

@Mixin(value = KeyboardInput.class, priority = MixinPriority.VALUE)
public final class KeyboardInputMixin extends Input implements Global {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickHead(boolean slowDown, CallbackInfo callbackInfo) {
        if (this.isNull()) return;

        AutoWalk autoWalk = Tensor.INSTANCE.MODULE_MANAGER.getModule(AutoWalk.class);
        InventoryMove inventoryMove = Tensor.INSTANCE.MODULE_MANAGER.getModule(InventoryMove.class);

        if (inventoryMove != null && inventoryMove.isEnabled() && this.getMinecraft().currentScreen != null) {

            if (this.getMinecraft().currentScreen instanceof ChatScreen && !inventoryMove.chatScreen.getValue()) return;

            this.pressingForward = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keyForward).getBoundKey().getCode());
            this.pressingBack = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keyBack).getBoundKey().getCode());
            this.pressingLeft = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keyLeft).getBoundKey().getCode());
            this.pressingRight = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keyRight).getBoundKey().getCode());

            if (autoWalk != null && autoWalk.isEnabled()) {
                this.pressingForward = true;
            }

            this.movementForward = this.pressingForward == this.pressingBack ? 0.0F : (this.pressingForward ? 1.0F : -1.0F);
            this.movementSideways = this.pressingLeft == this.pressingRight ? 0.0F : (this.pressingLeft ? 1.0F : -1.0F);

            if (inventoryMove.jump.getValue()) {
                this.jumping = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keyJump).getBoundKey().getCode());
            }

            if (inventoryMove.sneak.getValue()) {
                this.sneaking = InputUtil.isKeyPressed(this.getMinecraft().getWindow().getHandle(), ((KeyBindingAccessor) this.getMinecraft().options.keySneak).getBoundKey().getCode());
            }

            if (slowDown) {
                this.movementForward = (float) ((double) this.movementForward * 0.3D);
                this.movementSideways = (float) ((double) this.movementSideways * 0.3D);
            }

            callbackInfo.cancel();
        }
    }

    @Inject(method = "tick", at = @At("RETURN"), cancellable = true)
    public void tickReturn(boolean slowDown, CallbackInfo callbackInfo) {
        AutoWalk autoWalk = Tensor.INSTANCE.MODULE_MANAGER.getModule(AutoWalk.class);

        if (autoWalk != null && autoWalk.isEnabled()) {
            this.pressingForward = true;

            this.movementForward = this.pressingBack ? 0.0F : 1.0F;

            if (slowDown) {
                this.movementForward = (float) ((double) this.movementForward * 0.3D);
            }
        }
    }
}
