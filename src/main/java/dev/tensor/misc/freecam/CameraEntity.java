package dev.tensor.misc.freecam;

import com.mojang.authlib.GameProfile;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

/**
 * @author wagyourtail#3826
 * @since 06-02-2020
 */

@SuppressWarnings("EntityConstructor")
public final class CameraEntity extends OtherClientPlayerEntity implements Global {

    private final Input input;
    private final Freecam freecam;

    public CameraEntity(ClientWorld clientWorld, GameProfile gameProfile, Freecam freecam) {
        super(clientWorld, gameProfile);

        this.input = new KeyboardInput(this.getMinecraft().options);
        this.freecam = freecam;
    }

    public void spawn() {
        this.getWorld().addEntity(this.getId(), this);
    }

    public void despawn() {
        this.getWorld().removeEntity(this.getId(), RemovalReason.DISCARDED);
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return false;
    }

    @Override
    public boolean shouldRender(double distance) {
        return false;
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public void tickMovement() {
        this.setVelocity(0, 0, 0);

        this.input.tick(false);

        float upDown = (this.input.sneaking ? -this.freecam.getSpeed() : 0) + (this.input.jumping ? this.freecam.getSpeed() : 0);

        Vec3d forward = new Vec3d(0, 0, this.freecam.getSpeed() * 2.5).rotateY((float) -Math.toRadians(this.headYaw));
        Vec3d strafe = forward.rotateY((float) Math.toRadians(90));
        Vec3d motion = this.getVelocity();

        motion = motion.add(0, 2 * upDown, 0);
        motion = motion.add(strafe.getX() * this.input.movementSideways, 0, strafe.getZ() * this.input.movementSideways);
        motion = motion.add(forward.getX() * this.input.movementForward, 0, forward.getZ() * this.input.movementForward);

        this.setPos(this.getX() + motion.getX(), this.getY() + motion.getY(), this.getZ() + motion.getZ());
    }
}
