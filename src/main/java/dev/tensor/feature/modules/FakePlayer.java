package dev.tensor.feature.modules;

import com.mojang.authlib.GameProfile;
import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;

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
        otherClientPlayerEntity = new OtherClientPlayerEntity(this.getWorld(), new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "Fit"));
        otherClientPlayerEntity.copyPositionAndRotation(this.getPlayer());
        otherClientPlayerEntity.setId(-666);
        this.getWorld().addEntity(otherClientPlayerEntity.getId(), otherClientPlayerEntity);
        this.getWorld().getPlayers().add(otherClientPlayerEntity);
    }

    @Override
    public void onDisable() {
        if (otherClientPlayerEntity == null) return;

        this.getWorld().removeEntity(otherClientPlayerEntity.getId(), Entity.RemovalReason.DISCARDED);
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onDisconnect(DisconnectEvent event) {
        this.disable();
    }
}
