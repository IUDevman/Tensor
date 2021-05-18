package dev.tensor.misc.freecam;

import com.mojang.authlib.GameProfile;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;

/**
 * @author wagyourtail#3826
 * Added with explicit permission, thanks! :D
 */

@SuppressWarnings("EntityConstructor")
public final class CameraEntity extends OtherClientPlayerEntity implements Wrapper {

    private final Input input;
    private final Freecam freecam;

    public CameraEntity(ClientWorld clientWorld, GameProfile gameProfile, Freecam freecam) {
        super(clientWorld, gameProfile);

        this.input = new KeyboardInput(getMinecraft().options);
        this.freecam = freecam;
    }

    public void spawn() {
        getWorld().addEntity(this.getEntityId(), this);
    }

    public void despawn() {
        getWorld().removeEntity(this.getEntityId());
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return false;
    }

    public boolean shouldRender(double distance) {
        return false;
    }

    public boolean shouldRenderName() {
        return false;
    }

    public void tickMovement() {
        this.setVelocity(0, 0, 0);

        input.tick(false);

        float upDown = (this.input.sneaking ? -freecam.getSpeed() : 0) + (this.input.jumping ? freecam.getSpeed() : 0);

        Vec3d vec3d = new Vec3d(0, 0, freecam.getSpeed() * 2.5).rotateY((float) -Math.toRadians(this.headYaw));
        Vec3d strafe = vec3d.rotateY((float) Math.toRadians(90));
        Vec3d motion = this.getVelocity();

        motion = motion.add(0, 2 * upDown, 0);
        motion = motion.add(strafe.getX() * input.movementSideways, 0, strafe.getZ() * input.movementSideways);
        motion = motion.add(vec3d.getX() * input.movementForward, 0, vec3d.getZ() * input.movementForward);

        this.setPos(this.getX() + motion.getX(), this.getY() + motion.getY(), this.getZ() + motion.getZ());
    }
}
