package dev.tensor.feature.modules;

import dev.tensor.backend.mixins.accessors.MinecraftClientAccessor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import net.minecraft.item.Items;

/**
 * @author IUDevman
 * @since 05-21-2021
 */

@Module.Info(name = "NoPlaceDelay", category = Category.Player)
public final class NoPlaceDelay extends Module {

    public final BooleanSetting all = new BooleanSetting("All", true);
    public final BooleanSetting xp = new BooleanSetting("XP Bottle", false);
    public final BooleanSetting mainCrystal = new BooleanSetting("Mainhand Crystal", false);
    public final BooleanSetting offhandCrystal = new BooleanSetting("Offhand Crystal", false);

    @Override
    public void onTick() {
        if (shouldToggleDelay()) {
            ((MinecraftClientAccessor) getMinecraft()).setItemUseCooldown(0);
        }
    }

    private boolean shouldToggleDelay() {
        if (all.getValue()) return true;
        else if (xp.getValue() && getPlayer().getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE) return true;
        else if (mainCrystal.getValue() && getPlayer().getMainHandStack().getItem() == Items.END_CRYSTAL) return true;
        else return offhandCrystal.getValue() && getPlayer().getOffHandStack().getItem() == Items.END_CRYSTAL;
    }
}
