package dev.tensor.misc.gui;

import dev.tensor.Tensor;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.managers.SettingManager;
import dev.tensor.misc.gui.elements.CategoryElement;
import dev.tensor.misc.gui.elements.ModuleElement;
import dev.tensor.misc.gui.elements.PropertyElement;
import dev.tensor.misc.gui.elements.settings.BooleanElement;
import dev.tensor.misc.gui.elements.settings.ColorElement;
import dev.tensor.misc.gui.elements.settings.EnumElement;
import dev.tensor.misc.gui.elements.settings.NumberElement;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Wrapper;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.EnumSetting;
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
    private final NumberSetting mScrollY = new NumberSetting("Module Scroll Y", 0, -1000, 1000, 0);
    private final NumberSetting sScrollY = new NumberSetting("Setting Scroll Y", 0, -1000, 1000, 0);

    @SuppressWarnings("FieldCanBeLocal")
    private final int guiWidth = 388;
    private int guiHeight = 0;

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
                ModuleElement moduleElement = new ModuleElement(module, x, y, mScrollY, 62, moduleY.get());
                categoryElement.addModuleElement(moduleElement);
                moduleY.getAndAdd(moduleElement.getHeight());

                if (categoryElement.getModuleElements().size() == 1) moduleElement.setViewed(true);

                final AtomicInteger settingY = new AtomicInteger(22);

                PropertyElement propertyElement = new PropertyElement(module, x, y, sScrollY, 184, settingY.get());
                moduleElement.setPropertyElement(propertyElement);
                settingY.getAndAdd(propertyElement.getHeight());

                SettingManager.INSTANCE.getSettingsForModule(module).forEach(setting -> {
                    if (setting instanceof BooleanSetting) {
                        BooleanElement booleanElement = new BooleanElement((BooleanSetting) setting, x, y, sScrollY, 184, settingY.get());
                        moduleElement.addSettingElement(booleanElement);
                        settingY.getAndAdd(booleanElement.getHeight());

                    } else if (setting instanceof NumberSetting) {
                        NumberElement numberElement = new NumberElement((NumberSetting) setting, x, y, sScrollY, 184, settingY.get());
                        moduleElement.addSettingElement(numberElement);
                        settingY.getAndAdd(numberElement.getHeight());

                    } else if (setting instanceof EnumSetting) {
                        EnumElement enumElement = new EnumElement((EnumSetting) setting, x, y, sScrollY, 184, settingY.get());
                        moduleElement.addSettingElement(enumElement);
                        settingY.getAndAdd(enumElement.getHeight());

                    } else if (setting instanceof ColorSetting) {
                        ColorElement colorElement = new ColorElement((ColorSetting) setting, x, y, sScrollY, 184, settingY.get());
                        moduleElement.addSettingElement(colorElement);
                        settingY.getAndAdd(categoryElement.getHeight());
                    }
                });
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

                        moduleElement.getSettingElements().forEach(settingElement -> {
                            settingElement.render(matrixStack, settingElement.getPosX(), settingElement.getPosY());
                            settingHeight.getAndAdd(settingElement.getHeight());
                        });
                    }
                });
            }
        });

        this.guiHeight = guiHeight.get();

        if (this.mScrollY.getValue().intValue() > 0) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + 182, this.y.getValue().intValue() + this.mScrollY.getValue().intValue() + 22, new Color(0, 0, 0, 150).getRGB());
        }

        if (moduleHeight.get() + this.mScrollY.getValue().intValue() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + this.mScrollY.getValue().intValue() + moduleHeight.get(), this.x.getValue().intValue() + 182, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB());
        }

        if (this.sScrollY.getValue().intValue() > 0) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + this.sScrollY.getValue().intValue() + 22, new Color(0, 0, 0, 150).getRGB());
        }

        if (settingHeight.get() + this.sScrollY.getValue().intValue() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + this.sScrollY.getValue().intValue() + settingHeight.get(), this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB()); //setting fill
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
                    categoryElement.onClick(mouseX, mouseY);
                    triggerHovered(categoryElement);
                    return;
                }

                if (categoryElement.isSelected()) {
                    categoryElement.getModuleElements().forEach(moduleElement -> {
                        if (isHovered(moduleElement, mouseX, mouseY)) {
                            moduleElement.onClick(mouseX, mouseY);
                            triggerView(moduleElement, categoryElement);
                            return;
                        }

                        if (moduleElement.isViewed()) {
                            if (isHovered(moduleElement.getPropertyElement(), mouseX, mouseY)) {
                                moduleElement.getPropertyElement().onClick(mouseX, mouseY);
                                return;
                            }

                            moduleElement.getSettingElements().forEach(settingElement -> {
                                if (isHovered(settingElement, mouseX, mouseY)) {
                                    settingElement.onClick(mouseX, mouseY);
                                }
                            });
                        }
                    });
                }
            });
        }

        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (!super.mouseScrolled(mouseX, mouseY, scroll)) {
            if (scroll == 0) return true;

            int scrollSpeed = scroll > 0 ? -10 : 10;

            if (isHovered(this.x.getValue().intValue() + 62, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + 182, this.y.getValue().intValue() + this.guiHeight, mouseX, mouseY)) {
                handleScroll(this.mScrollY, scrollSpeed);
                return true;
            }

            if (isHovered(this.x.getValue().intValue() + 184, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + this.guiWidth, this.y.getValue().intValue() + this.guiHeight, mouseX, mouseY)) {
                handleScroll(this.sScrollY, scrollSpeed);
                return true;
            }
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
        resetScroll(true);
        this.categoryElements.forEach(categoryElement1 -> {
            if (!categoryElement1.equals(categoryElement)) categoryElement1.setSelected(false);
        });
    }

    private void triggerView(ModuleElement moduleElement, CategoryElement categoryElement) {
        resetScroll(false);
        categoryElement.getModuleElements().forEach(moduleElement1 -> {
            if (!moduleElement1.equals(moduleElement)) moduleElement1.setViewed(false);
        });
    }

    private void resetScroll(boolean category) {
        if (category) this.mScrollY.setValue(0.0);
        this.sScrollY.setValue(0.0);
    }

    private void handleScroll(NumberSetting numberSetting, int scrollSpeed) {
        if (numberSetting.getValue() > -10 && scrollSpeed > 0) return;
        numberSetting.setValue(numberSetting.getValue() + scrollSpeed);
    }
}
