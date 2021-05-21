package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.backend.events.KeyPressedEvent;
import dev.tensor.feature.managers.FriendManager;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
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
    @EventHandler
    private final Listener<KeyPressedEvent> keyPressedEventListener = new Listener<>(event -> {
        if (event.getBind() != GLFW.GLFW_MOUSE_BUTTON_MIDDLE) return;
        if (Objects.requireNonNull(getMinecraft().crosshairTarget).getType() != HitResult.Type.ENTITY) return;

        EntityHitResult entityHitResult = (EntityHitResult) getMinecraft().crosshairTarget;
        if (!(entityHitResult.getEntity() instanceof PlayerEntity)) return;

        String name = entityHitResult.getEntity().getEntityName();

        if (FriendManager.INSTANCE.isFriend(name)) {
            FriendManager.INSTANCE.removeFriend(name);
            this.sendReplaceableClientMessage("Removed " + Formatting.RED + name + Formatting.GRAY + " from the friends list!", 1000, true);
        } else {
            FriendManager.INSTANCE.addFriend(name);
            this.sendReplaceableClientMessage("Added " + Formatting.GREEN + name + Formatting.GRAY + " to the friends list!", 1000, true);
        }
    });
}
