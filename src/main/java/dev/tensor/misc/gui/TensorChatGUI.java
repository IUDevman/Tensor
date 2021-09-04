package dev.tensor.misc.gui;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author IUDevman
 * @since 09-03-2021
 */

public final class TensorChatGUI implements Global {

    private final Color color = new Color(62, 11, 10, 200);
    private final Color textColor = new Color(255, 255, 255, 255);

    public void render(MatrixStack matrixStack, String message, int x1, int y1, int x2, int y2) {
        if (message.startsWith(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix())) {
            DrawableHelper.fill(matrixStack, x1, y1 - 1, x2, y1, this.color.getRGB());
            DrawableHelper.fill(matrixStack, x1, y2, x2, y2 + 1, this.color.getRGB());
            DrawableHelper.fill(matrixStack, x1 - 1, y1 - 1, x1, y2 + 1, this.color.getRGB());
            DrawableHelper.fill(matrixStack, x2, y1 - 1, x2 + 1, y2 + 1, this.color.getRGB());

            String parsedMessage = message.replaceFirst(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix(), "");

            if (parsedMessage.equalsIgnoreCase("")) return;

            String[] words = parsedMessage.split(" ");

            if (words.length < 1) return;

            Command command = Tensor.INSTANCE.COMMAND_MANAGER.getCommand(words[0]);

            if (words.length == 1 && command == null) {
                ArrayList<Command> parsedCommands = Tensor.INSTANCE.COMMAND_MANAGER.getCommands().stream().filter(command1 -> command1.getName().toLowerCase(Locale.ROOT).startsWith(parsedMessage)).collect(Collectors.toCollection(ArrayList::new));

                if (parsedCommands.size() == 0) return;

                AtomicReference<String> commandList = new AtomicReference<>("");

                parsedCommands.forEach(command1 -> commandList.getAndSet(commandList.get() + command1.getName() + ", "));

                if (commandList.get().length() > 2) {
                    commandList.getAndSet(commandList.get().substring(0, commandList.get().length() - 2));
                }

                int startX = x1 + this.getMinecraft().textRenderer.getWidth(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix());
                int startY = y1 - this.getMinecraft().textRenderer.fontHeight - 3;

                DrawableHelper.fill(matrixStack, startX - 2, startY - 2, startX + this.getMinecraft().textRenderer.getWidth(commandList.get()) + 2, y1 - 1, this.getMinecraft().options.getTextBackgroundColor(-2147483648));
                DrawableHelper.fill(matrixStack, startX - 3, startY - 3, startX + this.getMinecraft().textRenderer.getWidth(commandList.get()) + 3, startY - 2, this.color.getRGB());
                DrawableHelper.fill(matrixStack, startX - 3, startY - 2, startX - 2, y1 - 1, this.color.getRGB());
                DrawableHelper.fill(matrixStack, startX + this.getMinecraft().textRenderer.getWidth(commandList.get()) + 2, startY - 2, startX + this.getMinecraft().textRenderer.getWidth(commandList.get()) + 3, y1 - 1, this.color.getRGB());

                DrawableHelper.drawTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText(commandList.get()), startX, startY, this.textColor.getRGB());

            } else if (command != null) {
                String commandSyntax = command.getSyntax().replace("{name}", Formatting.YELLOW + command.getName() + Formatting.RESET);

                int startX = x1 + this.getMinecraft().textRenderer.getWidth(Tensor.INSTANCE.COMMAND_MANAGER.getPrefix());
                int startY = y1 - this.getMinecraft().textRenderer.fontHeight - 3;

                DrawableHelper.fill(matrixStack, startX - 2, startY - 2, startX + this.getMinecraft().textRenderer.getWidth(commandSyntax) + 2, y1 - 1, this.getMinecraft().options.getTextBackgroundColor(-2147483648));
                DrawableHelper.fill(matrixStack, startX - 3, startY - 3, startX + this.getMinecraft().textRenderer.getWidth(commandSyntax) + 3, startY - 2, this.color.getRGB());
                DrawableHelper.fill(matrixStack, startX - 3, startY - 2, startX - 2, y1 - 1, this.color.getRGB());
                DrawableHelper.fill(matrixStack, startX + this.getMinecraft().textRenderer.getWidth(commandSyntax) + 2, startY - 2, startX + this.getMinecraft().textRenderer.getWidth(commandSyntax) + 3, y1 - 1, this.color.getRGB());

                DrawableHelper.drawTextWithShadow(matrixStack, this.getMinecraft().textRenderer, new LiteralText(commandSyntax), startX, startY, this.textColor.getRGB());
            }
        }
    }
}
