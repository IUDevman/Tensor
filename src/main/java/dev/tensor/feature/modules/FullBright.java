package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.Setting;
import dev.tensor.misc.imp.settings.StringSetting;
import net.minecraft.potion.Potions;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Module.Info(name = "FullBright", category = Category.Render, enabled = true)
public final class FullBright extends Module {

    @StringSetting(setting = @Setting(name = "Type"), values = {"Gamma", "Potion"})
    public String value = "Gamma";

    private double oldSetting = -1;

    public void onEnable() {
        oldSetting = getMinecraft().options.gamma;
    }

    public void onDisable() {
        if (oldSetting != -1) getMinecraft().options.gamma = oldSetting;

        Potions.NIGHT_VISION.getEffects().forEach(statusEffectInstance -> getPlayer().removeStatusEffect(statusEffectInstance.getEffectType()));
    }

    public void onTick() {
        switch (value) {
            case "Potion": {
                Potions.NIGHT_VISION.getEffects().forEach(statusEffectInstance -> getPlayer().applyStatusEffect(statusEffectInstance));
                break;
            }
            default: {
                if (getMinecraft().options.gamma < 100) {
                    getMinecraft().options.gamma++;
                }
                break;
            }
        }
    }
}
