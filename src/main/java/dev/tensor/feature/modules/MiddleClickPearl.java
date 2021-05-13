package dev.tensor.feature.modules;

import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.util.InventoryUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
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
    @EventHandler
    private final Listener<KeyPressedEvent> keyPressedEventListener = new Listener<>(event -> {
        if (event.getBind() != GLFW.GLFW_MOUSE_BUTTON_MIDDLE) return;
        if (Objects.requireNonNull(getMinecraft().crosshairTarget).getType() != HitResult.Type.MISS) return;

        final int oldSlot = getInventory().selectedSlot;
        final int newSlot = InventoryUtil.INSTANCE.findItem(Items.ENDER_PEARL);

        if (newSlot != -1) {
            InventoryUtil.INSTANCE.swap(newSlot);
            Objects.requireNonNull(getMinecraft().interactionManager).interactItem(getPlayer(), getWorld(), Hand.MAIN_HAND);
            InventoryUtil.INSTANCE.swap(oldSlot);
        }
    });
}
