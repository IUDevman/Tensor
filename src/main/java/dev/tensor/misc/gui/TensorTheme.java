package dev.tensor.misc.gui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.RendererBase;
import com.lukflug.panelstudio.theme.Theme;
import net.minecraft.util.Formatting;

import java.awt.*;

/**
 * @author lukflug
 */

public final class TensorTheme implements Theme {

    protected final Renderer componentRenderer;
    protected final Renderer containerRenderer;
    protected final Renderer panelRenderer;

    public TensorTheme() {
        panelRenderer = new ComponentRenderer(0);
        containerRenderer = new ComponentRenderer(1);
        componentRenderer = new ComponentRenderer(2);
    }

    @Override
    public Renderer getPanelRenderer() {
        return panelRenderer;
    }

    @Override
    public Renderer getContainerRenderer() {
        return containerRenderer;
    }

    @Override
    public Renderer getComponentRenderer() {
        return componentRenderer;
    }


    protected static final class ComponentRenderer extends RendererBase {

        protected final int level;

        public ComponentRenderer(int level) {
            super(level == 0 ? 15 : 13, 0, level == 0 ? 2 : 0, 0, 0);
            this.level = level;
        }

        @Override
        public void renderTitle(Context context, String text, boolean focus) {
            super.renderTitle(context, text, focus, false);
        }

        @Override
        public void renderTitle(Context context, String text, boolean focus, boolean active) {
            if (level == 2) {

                Color overlayColor;

                if (context.isHovered()) {
                    overlayColor = new Color(255, 255, 255, 64);
                } else {
                    overlayColor = new Color(255, 255, 255, 0);
                }

                context.getInterface().fillRect(context.getRect(), overlayColor, overlayColor, overlayColor, overlayColor);
                Point stringPos = new Point(context.getRect().getLocation());
                stringPos.translate(level == 2 ? 0 : context.getSize().width / 2 - context.getInterface().getFontWidth(text) / 2, level == 0 ? 2 : 1);

                String string;

                if (active) {
                    string = text + Formatting.GRAY + " (" + Formatting.GREEN + "true" + Formatting.GRAY + ")";
                } else {
                    string = text + Formatting.GRAY + " (" + Formatting.RED + "false" + Formatting.GRAY + ")";
                }

                context.getInterface().drawString(stringPos, string, level == 1 && active ? getMainColor(focus, true) : getFontColor(focus));

            } else super.renderTitle(context, text, focus, active);
        }

        @Override
        public void renderRect(Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
            if (level == 0) {
                Color color = getMainColor(focus, active);
                context.getInterface().fillRect(rectangle, color, color, color, color);
            }

            if (overlay) {

                Color overlayColor;
                if (context.isHovered()) {
                    overlayColor = new Color(255, 255, 255, 64);
                } else {
                    overlayColor = new Color(255, 255, 255, 0);
                }

                context.getInterface().fillRect(context.getRect(), overlayColor, overlayColor, overlayColor, overlayColor);
            }

            Point stringPos = new Point(rectangle.getLocation());
            stringPos.translate(level == 2 ? 0 : context.getSize().width / 2 - context.getInterface().getFontWidth(text) / 2, level == 0 ? 2 : 1);
            context.getInterface().drawString(stringPos, text, level == 1 && active ? getMainColor(focus, true) : getFontColor(focus));

            if (level == 1 || text.startsWith("Keybind")) {
                Color color = getColorScheme().getOutlineColor();
                Rectangle rect = new Rectangle(context.getPos().x, context.getPos().y + context.getSize().height - 1, context.getSize().width, 1);
                context.getInterface().fillRect(rect, color, color, color, color);
            }
        }

        @Override
        public void renderBackground(Context context, boolean focus) {
            Color color = getBackgroundColor(focus);
            Rectangle rect = new Rectangle(context.getPos().x + 2, context.getPos().y, context.getSize().width - 4, context.getSize().height);
            context.getInterface().fillRect(rect, color, color, color, color);
        }

        @Override
        public void renderBorder(Context context, boolean focus, boolean active, boolean open) {

        }

        @Override
        public Color getMainColor(boolean focus, boolean active) {
            if (level == 0) return getColorScheme().getOutlineColor();
            else if (active) return getColorScheme().getActiveColor();
            else if (level == 1) return getColorScheme().getBackgroundColor();
            else return getColorScheme().getInactiveColor();
        }

        @Override
        public Color getBackgroundColor(boolean focus) {
            return getColorScheme().getBackgroundColor();
        }

        @Override
        public Color getFontColor(boolean focus) {
            if (level == 2) {

                Color color = getColorScheme().getFontColor();
                return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());

            } else return getColorScheme().getFontColor();
        }

        @Override
        public ColorScheme getDefaultColorScheme() {
            return null;
        }
    }
}
