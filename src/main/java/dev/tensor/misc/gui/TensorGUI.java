package dev.tensor.misc.gui;

import dev.tensor.misc.imp.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class TensorGUI extends Screen {

    private final ArrayList<CategoryButton> categoryButtons = new ArrayList<>();

    private AtomicInteger posX = new AtomicInteger(100);
    private AtomicInteger posY = new AtomicInteger(100);

    public TensorGUI() {
        super(new LiteralText("Tensor GUI"));

        Arrays.stream(Category.values()).forEach(category -> {
            CategoryButton categoryButton = new CategoryButton(category, posX.get(), posY.get());
            this.categoryButtons.add(categoryButton);
            posY.getAndAdd(categoryButton.getHeight());
        });
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        this.categoryButtons.forEach(categoryButton -> {
            categoryButton.render(matrixStack, categoryButton.getPosX(), categoryButton.getPosY());
        });
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
