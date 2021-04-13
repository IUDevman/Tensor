package dev.tensor.misc.util;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public enum MessageUtil implements Wrapper {

    INSTANCE;

    private final String clientPrefix = "[" + Tensor.MOD_NAME + "]";

    public void sendChatMessage(String message, boolean nullCheck) {

    }

    public void sendClientMessage(String message, boolean prefix, boolean nullCheck) {
        if (nullCheck && isNull()) return;

        getChatHud().addMessage(new LiteralText((prefix ? clientPrefix + " " : "") + TextColor.fromFormatting(Formatting.GRAY) + message));
    }
}
