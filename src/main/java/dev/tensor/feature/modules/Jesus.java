package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.EnumSetting;

/**
 * @author IUDevman
 * @since 05-23-2021
 */

@Module.Info(name = "Jesus", category = Category.Movement)
public final class Jesus extends Module {

    public final EnumSetting denomination = new EnumSetting("Denomination", Denomination.Baptist);
    public final BooleanSetting cancelOnFall = new BooleanSetting("Cancel On Fall", true);
    public final BooleanSetting powderedSnow = new BooleanSetting("Powdered Snow", false);

    @SuppressWarnings("unused")
    public enum Denomination {
        Baptist,
        Methodist,
        Presbyterian,
        Orthodox,
        Catholic,
        Christian,
        Lutheran,
        Spirit,
        Reformed,
        Episcopal
    }
}
