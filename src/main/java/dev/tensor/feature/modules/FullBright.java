package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.EnumSetting;
import net.minecraft.potion.Potions;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Module.Info(name = "FullBright", category = Category.Render, enabled = true)
public final class FullBright extends Module {

    public EnumSetting type = new EnumSetting("Type", Type.Potion);

    private double oldSetting = -1;

    public void onEnable() {
        oldSetting = getMinecraft().options.gamma;
    }

    public void onDisable() {
        if (oldSetting != -1) getMinecraft().options.gamma = oldSetting;

        Potions.NIGHT_VISION.getEffects().forEach(statusEffectInstance -> getPlayer().removeStatusEffect(statusEffectInstance.getEffectType()));
    }

    public void onTick() {
        switch ((Type) type.getValue()) {
            case Potion: {
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

    public enum Type {
        Potion,
        Gamma
    }
}
