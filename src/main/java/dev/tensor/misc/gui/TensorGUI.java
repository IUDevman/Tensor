package dev.tensor.misc.gui;

import dev.tensor.Tensor;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.managers.SettingManager;
import dev.tensor.misc.gui.elements.CategoryElement;
import dev.tensor.misc.gui.elements.ModuleElement;
import dev.tensor.misc.gui.elements.PropertyElement;
import dev.tensor.misc.gui.elements.SettingElement;
import dev.tensor.misc.gui.elements.settings.*;
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
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

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

    private final NumberSetting x = new NumberSetting("X", 265, 0, 1000, 0);
    private final NumberSetting y = new NumberSetting("Y", 135, 0, 1000, 0);
    private final NumberSetting mScrollY = new NumberSetting("Module Scroll Y", 0, -1000, 1000, 0);
    private final NumberSetting sScrollY = new NumberSetting("Setting Scroll Y", 0, -1000, 1000, 0);

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
                        settingY.getAndAdd(colorElement.getHeight());
                    }
                });

                KeybindElement keybindElement = new KeybindElement(module, x, y, sScrollY, 184, settingY.get());
                moduleElement.addSettingElement(keybindElement);
                settingY.getAndAdd(keybindElement.getHeight());
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
        });

        this.guiHeight = guiHeight.get();

        int posX = this.x.getValue().intValue() + 62;
        int posY = this.y.getValue().intValue() + 22;
        int width1 = this.guiWidth - 62;
        int height1 = this.guiHeight - 22;

        int windowHeight = getMinecraft().getWindow().getHeight();
        double scale = getMinecraft().getWindow().getScaleFactor();
        int widthScaled = (int) (scale * width1);
        int heightScaled = (int) (scale * height1);
        int xScaled = (int) (posX * scale);
        int yScaled = (int) (posY * scale);

        GL11.glPushMatrix();
        GL11.glScissor(xScaled, windowHeight - yScaled - heightScaled, widthScaled, heightScaled);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        this.categoryElements.forEach(categoryElement -> {
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

        if (moduleHeight.get() + this.mScrollY.getValue().intValue() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + this.mScrollY.getValue().intValue() + moduleHeight.get(), this.x.getValue().intValue() + 182, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB());
        }

        if (settingHeight.get() + this.sScrollY.getValue().intValue() < guiHeight.get()) {
            DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + this.sScrollY.getValue().intValue() + settingHeight.get(), this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + guiHeight.get(), new Color(0, 0, 0, 150).getRGB());
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 60, this.y.getValue().intValue(), this.x.getValue().intValue() + 62, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 182, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + 184, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue() + 20, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + 22, new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue() - 2, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue(), new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue() + guiHeight.get(), this.x.getValue().intValue() + guiWidth + 2, this.y.getValue().intValue() + guiHeight.get() + 2, new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() - 2, this.y.getValue().intValue(), this.x.getValue().intValue(), this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() - 2, this.x.getValue().intValue() + guiWidth + 2, this.y.getValue().intValue() + guiHeight.get(), new Color(62, 11, 10, 200).getRGB());
        DrawableHelper.fill(matrixStack, this.x.getValue().intValue() + 62, this.y.getValue().intValue(), this.x.getValue().intValue() + guiWidth, this.y.getValue().intValue() + 20, new Color(0, 0, 0, 150).getRGB());

        String name = Tensor.INSTANCE.MOD_NAME + " (" + Formatting.YELLOW + Tensor.INSTANCE.MOD_VERSION + Formatting.RESET + ")";
        DrawableHelper.drawCenteredString(matrixStack, getMinecraft().textRenderer, name, this.x.getValue().intValue() + 62 + ((guiWidth - 62) / 2), this.y.getValue().intValue() + 2 + ((18 - getMinecraft().textRenderer.fontHeight) / 2), new Color(255, 255, 255, 255).getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {

            this.categoryElements.forEach(categoryElement -> {

                if (isHovered(categoryElement, mouseX, mouseY)) {
                    categoryElement.onClick(mouseX, mouseY);
                    triggerHovered(categoryElement);
                    return;
                }

                if (categoryElement.isSelected()) {

                    if (mouseY < this.y.getValue().intValue() + 22 || mouseY > this.y.getValue().intValue() + this.guiHeight) return;

                    categoryElement.getModuleElements().forEach(moduleElement -> {
                        if (isHovered(moduleElement, mouseX, mouseY)) {
                            moduleElement.onClick(mouseX, mouseY);
                            triggerView(moduleElement, categoryElement);
                            return;
                        }

                        if (moduleElement.isViewed()) {
                            if (isHovered(moduleElement.getPropertyElement(), mouseX, mouseY)) {
                                moduleElement.getPropertyElement().onClick(mouseX, mouseY);
                                triggerSetting(null);
                                return;
                            }

                            moduleElement.getSettingElements().forEach(settingElement -> {
                                if (isHovered(settingElement, mouseX, mouseY)) {
                                    settingElement.onClick(mouseX, mouseY);
                                    triggerSetting(settingElement);
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
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) return true;

        if (isHovered(this.x.getValue().intValue() + 60, this.y.getValue().intValue() - 2, this.x.getValue().intValue() + this.guiWidth, this.y.getValue().intValue() + 22, mouseX, mouseY)) {
            double newX = this.x.getValue() + deltaX;
            double newY = this.y.getValue() + deltaY;

            this.x.setValue(newX);
            this.y.setValue(newY);
        }

        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (!super.mouseScrolled(mouseX, mouseY, scroll)) {
            if (scroll == 0) return true;

            int scrollSpeed = scroll > 0 ? -10 : 10;

            if (isHovered(this.x.getValue().intValue() + 62, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + 182, this.y.getValue().intValue() + this.guiHeight, mouseX, mouseY)) {
                if (shouldScroll(true, scrollSpeed)) return true;
                handleScroll(this.mScrollY, scrollSpeed);
                return true;
            }

            if (isHovered(this.x.getValue().intValue() + 184, this.y.getValue().intValue() + 22, this.x.getValue().intValue() + this.guiWidth, this.y.getValue().intValue() + this.guiHeight, mouseX, mouseY)) {
                if (shouldScroll(false, scrollSpeed)) return true;
                handleScroll(this.sScrollY, scrollSpeed);
                return true;
            }
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!super.keyPressed(keyCode, scanCode, modifiers)) {
            this.categoryElements.forEach(categoryElement -> {

                if (categoryElement.isSelected()) {
                    categoryElement.getModuleElements().forEach(moduleElement -> {

                        if (moduleElement.isViewed()) {
                            moduleElement.getSettingElements().forEach(settingElement -> {

                                if (settingElement instanceof KeybindElement) {
                                    ((KeybindElement) settingElement).onKeyPressed(keyCode);
                                } else if (settingElement instanceof NumberElement) {
                                    ((NumberElement) settingElement).onKeyPressed(keyCode);
                                } else if (settingElement instanceof ColorElement) {
                                    ((ColorElement) settingElement).onKeyPressed(keyCode);
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
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        resetScroll(true);
        triggerSetting(null);
        super.onClose();
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
        triggerSetting(null);
    }

    private void triggerView(ModuleElement moduleElement, CategoryElement categoryElement) {
        resetScroll(false);
        categoryElement.getModuleElements().forEach(moduleElement1 -> {
            if (!moduleElement1.equals(moduleElement)) moduleElement1.setViewed(false);
        });
        triggerSetting(null);
    }

    private void triggerSetting(SettingElement settingElement) {
        this.categoryElements.forEach(categoryElement -> categoryElement.getModuleElements().forEach(moduleElement -> moduleElement.getSettingElements().forEach(settingElement1 -> {
            if (settingElement1 instanceof KeybindElement && !settingElement1.equals(settingElement)) {
                ((KeybindElement) settingElement1).setSearching(false);
            } else if (settingElement1 instanceof NumberElement && !settingElement1.equals(settingElement)) {
                ((NumberElement) settingElement1).setSearching(false);
            } else if (settingElement1 instanceof ColorElement && !settingElement1.equals(settingElement)) {
                ((ColorElement) settingElement1).clearSearching();
            }
        })));
    }

    private void resetScroll(boolean category) {
        if (category) this.mScrollY.setValue(0.0);
        this.sScrollY.setValue(0.0);
    }

    private void handleScroll(NumberSetting numberSetting, int scrollSpeed) {
        if (numberSetting.getValue() > -10 && scrollSpeed > 0) return;
        numberSetting.setValue(numberSetting.getValue() + scrollSpeed);
    }

    private boolean shouldScroll(boolean module, int scrollSpeed) {
        AtomicInteger moduleLength = new AtomicInteger(0);
        AtomicInteger settingLength = new AtomicInteger(0);

        this.categoryElements.forEach(categoryElement -> {
            if (categoryElement.isSelected()) {
                categoryElement.getModuleElements().forEach(moduleElement -> {
                    moduleLength.getAndAdd(moduleElement.getHeight());

                    if (!module && moduleElement.isViewed()) {
                        settingLength.getAndAdd(moduleElement.getPropertyElement().getHeight());

                        moduleElement.getSettingElements().forEach(settingElement -> settingLength.getAndAdd(settingElement.getHeight()));
                    }
                });
            }
        });

        return module ? (moduleLength.get() + (scrollSpeed < 0 ? this.mScrollY.getValue().intValue() : 0) <= this.guiHeight - 22) : (settingLength.get() + (scrollSpeed < 0 ? this.sScrollY.getValue().intValue() : 0) <= this.guiHeight - 22);
    }

    public int[] getAcceptedKeys() {
        return new int[]{
                GLFW.GLFW_KEY_0,
                GLFW.GLFW_KEY_1,
                GLFW.GLFW_KEY_2,
                GLFW.GLFW_KEY_3,
                GLFW.GLFW_KEY_4,
                GLFW.GLFW_KEY_5,
                GLFW.GLFW_KEY_6,
                GLFW.GLFW_KEY_7,
                GLFW.GLFW_KEY_8,
                GLFW.GLFW_KEY_9,
                GLFW.GLFW_KEY_PERIOD,
                GLFW.GLFW_KEY_BACKSPACE
        };
    }
}
