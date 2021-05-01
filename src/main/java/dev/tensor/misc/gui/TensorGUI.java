package dev.tensor.misc.gui;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.mc16.MinecraftGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.FixedDescription;
import com.lukflug.panelstudio.theme.SettingsColorScheme;
import com.lukflug.panelstudio.theme.Theme;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.managers.SettingManager;
import dev.tensor.feature.modules.ClickGui;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;
import dev.tensor.misc.imp.Wrapper;

import java.awt.*;

/**
 * @author IUDevman
 * @since 04-15-2021
 */

public final class TensorGUI extends MinecraftGUI implements Wrapper {

    private final ClickGui clickGui = ModuleManager.INSTANCE.getModule(ClickGui.class);

    private final GUIInterface guiInterface = new GUIInterface(true) {

        @Override
        protected String getResourcePrefix() {
            return "tensor:gui/";
        }

        @Override
        public void drawString(Point pos, String string, Color color) {
            if (matrixStack == null) return;
            end();
            getMinecraft().textRenderer.draw(matrixStack, string, pos.x + 2, pos.y + 2, color.getRGB());
            begin();
        }

        @Override
        public int getFontWidth(String string) {
            return getMinecraft().textRenderer.getWidth(string) + 4;
        }

        @Override
        public int getFontHeight() {
            return getMinecraft().textRenderer.fontHeight + 4;
        }
    };

    private final ClickGUI clickGUI = new ClickGUI(guiInterface, new FixedDescription(new Point(0, 0)));

    public TensorGUI() {
        Theme theme = new TensorTheme(new SettingsColorScheme(clickGui.enabledColor, clickGui.disabledColor, clickGui.buttonColor, clickGui.categoryColor, clickGui.fontColor, clickGui.opacity));
        Toggleable toggleable = new SimpleToggleable(false);

        int width = 100;
        int posX = 10;

        for (Category category : Category.values()) {
            DraggableContainer draggableContainer = new DraggableContainer(category.name(), null, theme.getPanelRenderer(), new SimpleToggleable(false), new SettingsAnimation(clickGui.animationSpeed), null, new Point(posX, 10), width);
            clickGUI.addComponent(draggableContainer);

            for (Module module : ModuleManager.INSTANCE.getModulesInCategory(category)) {
                CollapsibleContainer collapsibleContainer = new CollapsibleContainer(module.getName(), null, theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(clickGui.animationSpeed), module);
                draggableContainer.addComponent(collapsibleContainer);

                for (Setting<?> setting : SettingManager.INSTANCE.getSettingsForModule(module)) {
                    if (setting instanceof Toggleable) {
                        collapsibleContainer.addComponent(new BooleanComponent(setting.getName(), null, theme.getComponentRenderer(), (Toggleable) setting));
                    } else if (setting instanceof NumberSetting) {
                        collapsibleContainer.addComponent(new NumberComponent(setting.getName(), null, theme.getComponentRenderer(), (NumberSetting) setting, ((NumberSetting) setting).getMinimumValue(), ((NumberSetting) setting).getMaximumValue()));
                    } else if (setting instanceof EnumSetting) {
                        collapsibleContainer.addComponent(new EnumComponent(setting.getName(), null, theme.getComponentRenderer(), (EnumSetting) setting));
                    } else if (setting instanceof ColorSetting) {
                        collapsibleContainer.addComponent(new ColorComponent(setting.getName(), null, theme.getContainerRenderer(), new SettingsAnimation(clickGui.animationSpeed), theme.getComponentRenderer(), (ColorSetting) setting, false, false, toggleable));
                    }
                }
                collapsibleContainer.addComponent(new KeybindComponent(theme.getComponentRenderer(), module));
            }

            posX += width + 10;
        }
    }

    @Override
    protected ClickGUI getGUI() {
        return this.clickGUI;
    }

    @Override
    protected GUIInterface getInterface() {
        return this.guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return this.clickGui.scrollSpeed.getValue().intValue();
    }
}
