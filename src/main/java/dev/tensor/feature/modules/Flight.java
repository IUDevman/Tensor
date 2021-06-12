package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.util.math.Vec3d;

/**
 * @author IUDevman
 * @since 05-18-2021
 */

@Module.Info(name = "Flight", category = Category.Movement)
public final class Flight extends Module {

    public final BooleanSetting ignoreFluids = new BooleanSetting("Ignore Fluids", true);
    public final BooleanSetting elytraCheck = new BooleanSetting("Elytra Check", true);
    public final NumberSetting speed = new NumberSetting("Speed", 20, 1, 50, 1);
    public final BooleanSetting toggleTimer = new BooleanSetting("Toggle Timer", false);
    public final NumberSetting toggleTicks = new NumberSetting("Toggle Ticks", 10, 1, 40, 0);

    private int delayCount = 0;

    @Override
    public void onDisable() {
        delayCount = 0;
    }

    @Override
    public void onTick() {
        if (elytraCheck.getValue() && this.getPlayer().isFallFlying()) return;

        this.getPlayer().abilities.flying = false;

        if (!toggleTimer.getValue() || delayCount >= toggleTicks.getValue()) {
            delayCount = 0;
            this.getPlayer().flyingSpeed = getSpeed();
            this.getPlayer().setVelocity(new Vec3d(0, 0, 0));

            if (this.getMinecraft().options.keyJump.isPressed()) {
                this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, getSpeed(), 0));
            } else if (this.getMinecraft().options.keySneak.isPressed()) {
                this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, -getSpeed(), 0));
            }
        }
        delayCount++;
    }

    private float getSpeed() {
        return (float) (this.speed.getValue() / 10);
    }
}
