package dev.tensor.feature.modules;

import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.backend.mixins.accessors.ClientPlayerEntityAccessor;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.freecam.CameraEntity;
import dev.tensor.misc.freecam.DummyInput;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import dev.tensor.misc.imp.settings.NumberSetting;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.Perspective;

/**
 * @author wagyourtail#3826
 * Added with explicit permission, thanks! :D
 */

@Module.Info(name = "Freecam", category = Category.Render)
public final class Freecam extends Module {

    public final BooleanSetting hideBlockOutline = new BooleanSetting("Hide Block Outline", true);
    public final NumberSetting speed = new NumberSetting("Speed", 10, 1, 20, 1);

    private CameraEntity cameraEntity = null;
    private Perspective perspective = null;

    @Override
    public void onEnable() {
        cameraEntity = new CameraEntity(this.getWorld(), this.getPlayer().getGameProfile(), this);
        cameraEntity.copyPositionAndRotation(this.getPlayer());
        cameraEntity.setHeadYaw(this.getPlayer().headYaw);
        cameraEntity.spawn();

        perspective = this.getMinecraft().options.getPerspective();
        this.getMinecraft().options.setPerspective(Perspective.FIRST_PERSON);
        this.getMinecraft().setCameraEntity(cameraEntity);

        if (this.getPlayer().input instanceof KeyboardInput) {
            ((ClientPlayerEntityAccessor) this.getPlayer()).setInput(new DummyInput());
        }
    }

    @Override
    public void onDisable() {
        if (perspective != null) this.getMinecraft().options.setPerspective(perspective);
        this.getMinecraft().setCameraEntity(this.getPlayer());
        if (cameraEntity != null) cameraEntity.despawn();
        cameraEntity = null;

        if (this.getPlayer().input instanceof DummyInput) {
            ((ClientPlayerEntityAccessor) this.getPlayer()).setInput(new KeyboardInput(this.getMinecraft().options));
        }
    }

    @Override
    public void onTick() {
        if (cameraEntity == null || this.getPlayer().isDead()) {
            this.disable();
            return;
        }

        cameraEntity.setHealth(this.getPlayer().getHealth());

        if (this.getPlayer().input instanceof KeyboardInput) {
            ((ClientPlayerEntityAccessor) this.getPlayer()).setInput(new DummyInput());
        }

        cameraEntity.tickMovement();
    }

    @SuppressWarnings("unused")
    @EventTarget
    public void onDisconnect(DisconnectEvent event) {
        this.disable();
    }

    public float getSpeed() {
        return (float) (this.speed.getValue() / 20);
    }

    public CameraEntity getCameraEntity() {
        return this.cameraEntity;
    }
}
