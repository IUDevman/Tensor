package dev.tensor.feature.modules;

import dev.tensor.backend.events.DisconnectEvent;
import dev.tensor.misc.freecam.CameraEntity;
import dev.tensor.misc.freecam.DummyInput;
import dev.tensor.backend.mixins.accessors.ClientPlayerEntityAccessor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.NumberSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.Perspective;

/**
 * @author wagyourtail#3826
 * Added with explicit permission, thanks! :D
 */

@Module.Info(name = "Freecam", category = Category.Render)
public final class Freecam extends Module {

    public final NumberSetting speed = new NumberSetting("Speed", 10, 1, 20, 1);

    private CameraEntity cameraEntity = null;
    private Perspective perspective = null;

    @SuppressWarnings({"unused", "CodeBlock2Expr"})
    @EventHandler
    private final Listener<DisconnectEvent> disconnectEventListener = new Listener<>(event -> {
        disable();
    });

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

    public void onDisable() {
        if (perspective != null) getMinecraft().options.setPerspective(perspective);
        getMinecraft().setCameraEntity(getPlayer());
        if (cameraEntity != null) cameraEntity.despawn();
        cameraEntity = null;

        if (getPlayer().input instanceof DummyInput) {
            ((ClientPlayerEntityAccessor) getPlayer()).setInput(new KeyboardInput(getMinecraft().options));
        }
    }

    public void onTick() {
        if (cameraEntity == null || getPlayer().isDead()) {
            disable();
            return;
        }

        cameraEntity.setHealth(getPlayer().getHealth());
        if (getPlayer().input instanceof KeyboardInput) ((ClientPlayerEntityAccessor) getPlayer()).setInput(new DummyInput());
        cameraEntity.tickMovement();
    }

    public float getSpeed() {
        return (float) (this.speed.getValue() / 20);
    }

    public CameraEntity getCameraEntity() {
        return this.cameraEntity;
    }
}
