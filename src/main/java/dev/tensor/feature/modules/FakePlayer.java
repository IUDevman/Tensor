package dev.tensor.feature.modules;

import com.mojang.authlib.GameProfile;
import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.client.network.OtherClientPlayerEntity;

import java.util.UUID;

/**
 * @author IUDevman
 * @since 05-18-2021
 */

@Module.Info(name = "FakePlayer", category = Category.Misc)
public final class FakePlayer extends Module {

    private OtherClientPlayerEntity otherClientPlayerEntity = null;

    @Override
    public void onEnable() {
        otherClientPlayerEntity = new OtherClientPlayerEntity(getWorld(), new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "Fit"));
        otherClientPlayerEntity.copyPositionAndRotation(getPlayer());
        otherClientPlayerEntity.setEntityId(-666);
        getWorld().addEntity(otherClientPlayerEntity.getEntityId(), otherClientPlayerEntity);
        getWorld().getPlayers().add(otherClientPlayerEntity);
    }

    @Override
    public void onDisable() {
        if (otherClientPlayerEntity == null) return;

        getWorld().removeEntity(otherClientPlayerEntity.getEntityId());
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onDisconnect(DisconnectEvent event) {
        this.disable();
    }
}
