package dev.tensor.misc.util;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum MessageUtil implements Wrapper {

    INSTANCE;

    private final String clientPrefix = Formatting.DARK_GRAY + "[" + Formatting.DARK_RED + Tensor.MOD_NAME + Formatting.DARK_GRAY + "]";

    public void sendChatMessage(String message, boolean nullCheck) {
        if (nullCheck && isNull()) return;

        getPlayer().sendChatMessage(message);
    }

    public void sendClientMessage(String message, boolean prefix, boolean nullCheck) {
        if (nullCheck && isNull()) return;

        getChatHud().addMessage(new LiteralText((prefix ? clientPrefix + " " : "") + Formatting.GRAY + message));
    }
}
