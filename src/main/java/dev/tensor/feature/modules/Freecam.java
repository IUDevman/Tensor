package dev.tensor.feature.modules;

import dev.darkmagician6.eventapi.EventTarget;
import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.backend.mixins.accessors.ClientPlayerEntityAccessor;
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
        cameraEntity = new CameraEntity(getWorld(), getPlayer().getGameProfile(), this);
        cameraEntity.copyPositionAndRotation(getPlayer());
        cameraEntity.setHeadYaw(getPlayer().headYaw);
        cameraEntity.spawn();

        perspective = getMinecraft().options.getPerspective();
        getMinecraft().options.setPerspective(Perspective.FIRST_PERSON);
        getMinecraft().setCameraEntity(cameraEntity);

        if (getPlayer().input instanceof KeyboardInput) {
            ((ClientPlayerEntityAccessor) getPlayer()).setInput(new DummyInput());
        }
    }

    @Override
    public void onDisable() {
        if (perspective != null) getMinecraft().options.setPerspective(perspective);
        getMinecraft().setCameraEntity(getPlayer());
        if (cameraEntity != null) cameraEntity.despawn();
        cameraEntity = null;

        if (getPlayer().input instanceof DummyInput) {
            ((ClientPlayerEntityAccessor) getPlayer()).setInput(new KeyboardInput(getMinecraft().options));
        }
    }

    @Override
    public void onTick() {
        if (cameraEntity == null || getPlayer().isDead()) {
            this.disable();
            return;
        }

        cameraEntity.setHealth(getPlayer().getHealth());

        if (getPlayer().input instanceof KeyboardInput) {
            ((ClientPlayerEntityAccessor) getPlayer()).setInput(new DummyInput());
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
