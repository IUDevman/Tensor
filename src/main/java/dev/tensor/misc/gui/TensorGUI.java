package dev.tensor.misc.gui;

import dev.tensor.Tensor;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.misc.gui.elements.CategoryElement;
import dev.tensor.misc.gui.elements.ModuleElement;
import dev.tensor.misc.gui.elements.PropertyElement;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Wrapper;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IUDevman
 * @since 05-03-2021
 */

public final class TensorGUI extends Screen implements Wrapper {

    private final ArrayList<CategoryElement> categoryElements = new ArrayList<>();

    private final NumberSetting x = new NumberSetting("X", 100, 0, 1000, 0);
    private final NumberSetting y = new NumberSetting("Y", 100, 0, 1000, 0);

    private final int guiWidth = 388;

    public TensorGUI() {
        super(new LiteralText("Tensor GUI"));

        final AtomicInteger categoryY = new AtomicInteger(0);

        Arrays.stream(Category.values()).forEach(category -> {
            CategoryElement categoryElement = new CategoryElement(category, x, y, 0, categoryY.get());
            this.categoryElements.add(categoryElement);
            categoryY.getAndAdd(categoryElement.getHeight());

            if (this.categoryElements.size() == 1) categoryElement.setSelected(true);

            final AtomicInteger moduleY = new AtomicInteger(22);

            ModuleManager.INSTANCE.getModulesInCategory(category).forEach(module -> {
                ModuleElement moduleElement = new ModuleElement(module, x, y, 62, moduleY.get());
                categoryElement.addModuleElement(moduleElement);
                moduleY.getAndAdd(moduleElement.getHeight());

                if (categoryElement.getModuleElements().size() == 1) moduleElement.setViewed(true);

                PropertyElement propertyElement = new PropertyElement(module, x, y, 184, 22);
                moduleElement.addPropertyElement(propertyElement);
            });
        });
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float delta) {
        final AtomicInteger guiHeight = new AtomicInteger(0);
        final AtomicInteger moduleHeight = new AtomicInteger(22);
        final AtomicInteger settingHeight = new AtomicInteger(22);

        this.categoryElements.forEach(categoryElement -> {
            categoryElement.render(matrixStack, categoryElement.getPosX(), categoryElement.getPosY());
            guiHeight.getAndAdd(categoryElement.getHeight());

            if (categoryElement.isSelected()) {
                categoryElement.getModuleElements().forEach(moduleElement -> {
                    moduleElement.render(matrixStack, moduleElement.getPosX(), moduleElement.getPosY());
                    moduleHeight.getAndAdd(moduleElement.getHeight());

                    if (moduleElement.isViewed()) {
                        PropertyElement propertyElement = moduleElement.getPropertyElement();
                        propertyElement.render(matrixStack, propertyElement.getPosX(), propertyElement.getPosY());
                        settingHeight.getAndAdd(propertyElement.getHeight());
                    }
                });
            }
        });

        if (moduleHeight.get() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + moduleHeight.get(), this.x.getValue().intValue() + 182, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB());
        }

        if (settingHeight.get() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + settingHeight.get(), this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB()); //setting fill
        }

        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 60, this.y.getValue().intValue(), this.x.getValue().intValue() + 62, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB()); //c-m line
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 182, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB()); //m-s line
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + 20, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + 22, new Color(62, 11, 10, 200).getRGB()); //name bottom line
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue() -2, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue(), new Color(62, 11, 10, 200).getRGB()); //top
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue() + guiHeight.get(), this.x.getValue().intValue() + guiWidth + 2, this.y.getValue().intValue() + guiHeight.get() + 2, new Color(62, 11, 10, 200).getRGB()); //bottom
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue(), this.x.getValue().intValue(), this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB()); //left
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() - 2, this.x.getValue().intValue() + guiWidth + 2, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB()); //right
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue(), this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + 20, new Color(0, 0, 0, 150).getRGB());

        String name = Tensor.INSTANCE.MOD_NAME + " (" + Formatting.YELLOW + Tensor.INSTANCE.MOD_VERSION + Formatting.RESET + ")";
        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, name, this.x.getValue().intValue() + 62 + ((guiWidth - 62) / 2), this.y.getValue().intValue() + 2 + ((18 - getMinecraft().textRenderer.fontHeight) / 2), new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (clickedButton == 0) {

            this.categoryElements.forEach(categoryElement -> {

                if (isHovered(categoryElement, mouseX, mouseY)) {
                    categoryElement.setSelected(true);
                    triggerHovered(categoryElement);
                    return;
                }

                categoryElement.getModuleElements().forEach(moduleElement -> {
                    if (isHovered(moduleElement, mouseX, mouseY)) {
                        moduleElement.setViewed(true);
                        triggerView(moduleElement, categoryElement);
                    }
                });
            });
        }

        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean isHovered(Element element, double mouseX, double mouseY) {
       return isHovered(element.getPosX(), element.getPosY(), element.getPosX() + element.getWidth(), element.getPosY() + element.getHeight(), mouseX, mouseY);
    }

    private boolean isHovered(int minX, int minY, int maxX, int maxY, double mouseX, double mouseY) {
        if (mouseX < minX || mouseX > maxX) return false;
        else return !(mouseY < minY) && !(mouseY > maxY);
    }

    private void triggerHovered(CategoryElement categoryElement) {
        this.categoryElements.forEach(categoryElement1 -> {
            if (!categoryElement1.equals(categoryElement)) categoryElement1.setSelected(false);
        });
    }

    private void triggerView(ModuleElement moduleElement, CategoryElement categoryElement) {
        categoryElement.getModuleElements().forEach(moduleElement1 -> {
            if (!moduleElement1.equals(moduleElement)) moduleElement1.setViewed(false);
        });
    }
}
