package dev.tensor.misc.gui;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class TensorGUI extends Screen {

    private final ArrayList<CategoryButton> categoryButtons = new ArrayList<>();
    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    private final NumberSetting x = new NumberSetting("X", 100, 0, 1000, 0);
    private final NumberSetting y = new NumberSetting("Y", 100, 0, 1000, 0);

    private final int guiWidth = 328;

    public TensorGUI() {
        super(new LiteralText("Tensor GUI"));

        final AtomicInteger categoryY = new AtomicInteger(0);

        Arrays.stream(Category.values()).forEach(category -> {
            CategoryButton categoryButton = new CategoryButton(category, x, y, 0, categoryY.get());
            this.categoryButtons.add(categoryButton);
            categoryY.getAndAdd(categoryButton.getHeight());

            //temp addition to test
            if (category.equals(Category.Player)) {
                categoryButton.setSelected(true);
            }

            final AtomicInteger moduleY = new AtomicInteger(22);

            ModuleManager.INSTANCE.getModulesInCategory(category).forEach(module -> {
                ModuleButton moduleButton = new ModuleButton(module, x, y, 62, moduleY.get());
                this.moduleButtons.add(moduleButton);
                moduleY.getAndAdd(moduleButton.getHeight());
            });
        });
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        AtomicInteger guiHeight = new AtomicInteger(0);

        this.categoryButtons.forEach(categoryButton -> {
            categoryButton.render(matrixStack, categoryButton.getPosX(), categoryButton.getPosY());
            guiHeight.getAndAdd(categoryButton.getHeight());

            if (categoryButton.isSelected()) {
                this.moduleButtons.forEach(moduleButton -> {
                    if (moduleButton.getModule().getCategory().equals(categoryButton.getCategory())) {
                        moduleButton.render(matrixStack, moduleButton.getPosX(), moduleButton.getPosY());
                    }
                });
            }
        });

        //DrawableHelper.fill(matrixStack, this.x.getValue() - 2, this.y.getValue() - 2, this.x.getValue() + );
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue() -2, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue(), new Color(30, 30, 200, 200).getRGB());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
