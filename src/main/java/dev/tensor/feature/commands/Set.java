package dev.tensor.feature.commands;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Command;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.ColorSetting;
import dev.tensor.misc.imp.settings.EnumSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author IUDevman
 * @since 05-20-2021
 */

public final class Set implements Command {

    @Override
    public String getName() {
        return "Set";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{name} [module] [setting_name] [value] (r_g_b for color settings)";
    }

    @Override
    public int getID() {
        return 676;
    }

    @Override
    public void onCommand(String[] message) {
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No module inputted!", this.getID(), true);
            return;
        }

        String moduleName = message[1];

        Module module = Tensor.INSTANCE.MODULE_MANAGER.getModule(moduleName);

        if (module == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid module (" + Formatting.YELLOW + moduleName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        if (message.length < 3) {
            this.sendReplaceableClientMessage(this.getMarker() + "No setting inputted!", this.getID(), true);
            return;
        }

        String settingName = message[2].replace("_", " ");

        Setting<?> setting = Tensor.INSTANCE.SETTING_MANAGER.getSettingsForModule(module).stream().filter(setting1 -> setting1.getName().equalsIgnoreCase(settingName)).findFirst().orElse(null);

        if (setting == null) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid setting (" + Formatting.YELLOW + settingName + Formatting.GRAY + ")!", this.getID(), true);
            return;
        }

        if (message.length < 4) {
            this.sendReplaceableClientMessage(this.getMarker() + "No value inputted!", this.getID(), true);
            return;
        }

        String value = message[3];

        setValueDetailed(setting, value);
    }

    private void setValueDetailed(Setting<?> setting, String value) {
        if (setting instanceof BooleanSetting) {
            if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid boolean value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
                return;
            }

            ((BooleanSetting) setting).setValue(Boolean.parseBoolean(value));
            this.sendReplaceableClientMessage(this.getMarker() + "Set boolean value (" + setting.getName() + ") to " + Formatting.GREEN + value + Formatting.GRAY + "!", this.getID(), true);

        } else if (setting instanceof NumberSetting) {
            try {
                double value1 = Double.parseDouble(value);

                if (value1 > ((NumberSetting) setting).getMax()) value1 = ((NumberSetting) setting).getMax();
                else if (value1 < ((NumberSetting) setting).getMin()) value1 = ((NumberSetting) setting).getMin();

                String decimalFix = this.adjustForDecimals((NumberSetting) setting, value1);

                ((NumberSetting) setting).setValue(Double.parseDouble(decimalFix));
                this.sendReplaceableClientMessage(this.getMarker() + "Set number value (" + setting.getName() + ") to " + Formatting.GREEN + decimalFix + Formatting.GRAY + "!", this.getID(), true);

            } catch (NumberFormatException ignored) {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid number value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
            }

        } else if (setting instanceof EnumSetting) {
            Enum<?>[] array = ((EnumSetting) setting).getValue().getDeclaringClass().getEnumConstants();

            AtomicBoolean found = new AtomicBoolean(false);

            Arrays.stream(array).forEach(anEnum -> {
                if (!found.get() && anEnum.name().equalsIgnoreCase(value)) {
                    found.set(true);
                    ((EnumSetting) setting).setValue(anEnum);
                    this.sendReplaceableClientMessage(this.getMarker() + "Set enum value (" + setting.getName() + ") to " + Formatting.GREEN + value + Formatting.GRAY + "!", this.getID(), true);
                }
            });

            if (!found.get()) {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid enum value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
            }

        } else if (setting instanceof ColorSetting) {
            String[] values = value.split("_");

            if (values.length < 3) {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid color value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
                return;
            }

            try {
                int red = adjustForColor(Integer.parseInt(values[0]));
                int green = adjustForColor(Integer.parseInt(values[1]));
                int blue = adjustForColor(Integer.parseInt(values[2]));

                ((ColorSetting) setting).setValue(new Color(red, green, blue));
                this.sendReplaceableClientMessage(this.getMarker() + "Set color value (" + setting.getName() + ") to " + Formatting.GREEN + red + "_" + green + "_" + blue + Formatting.GRAY + "!", this.getID(), true);

            } catch (NumberFormatException ignored) {
                this.sendReplaceableClientMessage(this.getMarker() + "Invalid color value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
            }
        }
    }

    private String adjustForDecimals(NumberSetting setting, double value) {
        return String.format("%." + setting.getDecimal() + "f", value);
    }

    private int adjustForColor(int value) {
        if (value > 255) return 255;
        else return Math.max(value, 0);
    }
}
