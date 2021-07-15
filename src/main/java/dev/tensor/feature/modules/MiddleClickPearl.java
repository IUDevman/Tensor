package dev.tensor.feature.modules;

import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

/**
 * @author IUDevman
 * @since 05-13-2021
 */

@Module.Info(name = "MiddleClickPearl", category = Category.Player)
public final class MiddleClickPearl extends Module {

    @SuppressWarnings("unused")
    @EventTarget
    public void onKeyPressed(KeyPressedEvent event) {
        if (event.getBind() != GLFW.GLFW_MOUSE_BUTTON_MIDDLE) return;
        if (Objects.requireNonNull(this.getMinecraft().crosshairTarget).getType() != HitResult.Type.MISS) return;

        int oldSlot = this.getInventory().selectedSlot;
        int newSlot = this.findItem(Items.ENDER_PEARL);

        if (newSlot != -1) {
            this.swap(newSlot);
            Objects.requireNonNull(this.getMinecraft().interactionManager).interactItem(this.getPlayer(), this.getWorld(), Hand.MAIN_HAND);
            this.swap(oldSlot);
        }
    }
}
