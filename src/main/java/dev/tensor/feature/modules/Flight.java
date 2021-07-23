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

    @Override
    public void onTick() {
        if (elytraCheck.getValue() && this.getPlayer().isFallFlying()) return;

        this.getPlayer().getAbilities().flying = false;

        this.getPlayer().flyingSpeed = getSpeed();
        this.getPlayer().setVelocity(new Vec3d(0, 0, 0));

        if (this.getMinecraft().options.keyJump.isPressed()) {
            this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, getSpeed(), 0));
        } else if (this.getMinecraft().options.keySneak.isPressed()) {
            this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, -getSpeed(), 0));
        }
    }

    private float getSpeed() {
        return (float) (this.speed.getValue() / 10);
    }
}
