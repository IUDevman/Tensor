package dev.tensor.feature.modules;

import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.util.math.Vec3d;

/**
 * @author IUDevman
 * @since 05-28-2021
 */

@Module.Info(name = "ElytraFlight", category = Category.Movement)
public final class ElytraFlight extends Module {

    public final BooleanSetting ignoreFluids = new BooleanSetting("Ignore Fluids", true);
    public final BooleanSetting alwaysMoving = new BooleanSetting("Always Moving", false);
    public final BooleanSetting stableY = new BooleanSetting("Stable Y", false);
    public final BooleanSetting rotationY = new BooleanSetting("Rotation Y", false);
    public final NumberSetting speed = new NumberSetting("Speed", 15, 1, 100, 1);

    @Override
    public void onTick() {
        if (!this.getPlayer().isFallFlying()) return;

        this.getPlayer().getAbilities().flying = false;

        if (areButtonsDown() || alwaysMoving.getValue()) {
            this.getPlayer().setVelocity(getFlyingVelocity());

        } else {
            this.getPlayer().setVelocity(0, 0, 0);
        }

        if (!stableY.getValue() && !rotationY.getValue()) {
            if (this.getMinecraft().options.keyJump.isPressed()) {
                this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, getSpeed(), 0));
            } else if (getMinecraft().options.keySneak.isPressed()) {
                this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(0, -getSpeed(), 0));
            }
        }
    }

    private float getSpeed() {
        return (float) (this.speed.getValue() / 10);
    }

    private boolean areButtonsDown() {
        if (this.getMinecraft().options.keyForward.isPressed()) return true;
        else if (this.getMinecraft().options.keyBack.isPressed()) return true;
        else if (this.getMinecraft().options.keyLeft.isPressed()) return true;
        else if (this.getMinecraft().options.keyRight.isPressed()) return true;

        if (rotationY.getValue()) {
            if (this.getMinecraft().options.keySneak.isPressed()) return true;
            else return this.getMinecraft().options.keyJump.isPressed();
        }

        return false;
    }

    private Vec3d getFlyingVelocity() {
        double x = this.getPlayer().getRotationVector().getX() * getSpeed();
        double y = this.getPlayer().getRotationVector().getY();
        double z = this.getPlayer().getRotationVector().getZ() * getSpeed();

        if (rotationY.getValue()) y *= getSpeed();

        return new Vec3d(x, stableY.getValue() ? 0 : y, z);
    }
}
