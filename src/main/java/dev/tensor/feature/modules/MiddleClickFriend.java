package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

/**
 * @author IUDevman
 * @since 05-20-2021
 */

@Module.Info(name = "MiddleClickFriend", category = Category.Misc)
public final class MiddleClickFriend extends Module {

    @SuppressWarnings("unused")
    @EventTarget
    public void onKeyPressed(KeyPressedEvent event) {
        if (event.getBind() != GLFW.GLFW_MOUSE_BUTTON_MIDDLE) return;
        if (Objects.requireNonNull(this.getMinecraft().crosshairTarget).getType() != HitResult.Type.ENTITY) return;

        EntityHitResult entityHitResult = (EntityHitResult) this.getMinecraft().crosshairTarget;
        if (!(entityHitResult.getEntity() instanceof PlayerEntity)) return;

        String name = entityHitResult.getEntity().getEntityName();

        if (Tensor.INSTANCE.FRIEND_MANAGER.isFriend(name)) {
            Tensor.INSTANCE.FRIEND_MANAGER.removeFriend(name);
            this.sendReplaceableClientMessage("Removed " + Formatting.RED + name + Formatting.GRAY + " from the friends list!", 1000, true);
        } else {
            Tensor.INSTANCE.FRIEND_MANAGER.addFriend(name);
            this.sendReplaceableClientMessage("Added " + Formatting.GREEN + name + Formatting.GRAY + " to the friends list!", 1000, true);
        }
    }
}
