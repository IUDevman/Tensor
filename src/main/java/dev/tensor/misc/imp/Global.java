package dev.tensor.misc.imp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerInventory;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Global {

    default MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    default ClientPlayerEntity getPlayer() {
        return getMinecraft().player;
    }

    default ClientWorld getWorld() {
        return getMinecraft().world;
    }

    default ClientPlayNetworkHandler getNetwork() {
        return getPlayer().networkHandler;
    }

    default PlayerInventory getInventory() {
        return getPlayer().inventory;
    }

    default ChatHud getChatHud() {
        return getMinecraft().inGameHud.getChatHud();
    }

    default boolean isNull() {
        return getPlayer() == null || getWorld() == null;
    }
}
