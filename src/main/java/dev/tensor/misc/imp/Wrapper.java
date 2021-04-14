package dev.tensor.misc.imp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public interface Wrapper {

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

    default ChatHud getChatHud() {
        return getMinecraft().inGameHud.getChatHud();
    }

    default boolean isNull() {
        return getMinecraft() == null || getWorld() == null;
    }
}
