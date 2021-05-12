package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.EnumSetting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Module.Info(name = "FullBright", category = Category.Render)
public final class FullBright extends Module {

    public final EnumSetting type = new EnumSetting("Type", Type.Potion);

    private double oldSetting = -1;

    public void onEnable() {
        oldSetting = getMinecraft().options.gamma;
    }

    public void onDisable() {
        if (oldSetting != -1) getMinecraft().options.gamma = oldSetting;

        getPlayer().removeStatusEffect(StatusEffects.NIGHT_VISION);
    }

    public void onTick() {
        switch ((Type) type.getValue()) {
            case Potion: {
                getPlayer().applyStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000));
                if (oldSetting != -1) getMinecraft().options.gamma = oldSetting;
                break;
            }
            case Gamma: {
                getMinecraft().options.gamma = 100;
                getPlayer().removeStatusEffect(StatusEffects.NIGHT_VISION);
                break;
            }
            default:
                break;
        }
    }

    public enum Type {
        Potion,
        Gamma,
    }
}
