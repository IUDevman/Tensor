package dev.tensor.feature.modules;

import dev.darkmagician6.eventapi.EventTarget;
import dev.tensor.backend.events.PlaySoundEvent;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.imp.settings.BooleanSetting;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

/**
 * @author IUDevman
 * @since 05-10-2021
 */

@Module.Info(name = "NoOverlay", category = Category.Render)
public final class NoOverlay extends Module {

    public final BooleanSetting vignette = new BooleanSetting("Vignette", true);
    public final BooleanSetting pumpkin = new BooleanSetting("Pumpkin", true);
    public final BooleanSetting bossBar = new BooleanSetting("Boss Bar", true);
    public final BooleanSetting status = new BooleanSetting("Status", false);
    public final BooleanSetting toast = new BooleanSetting("Toast", false);

    @SuppressWarnings("unused")
    @EventTarget
    public void onPlaySound(PlaySoundEvent event) {
        if (!toast.getValue() || !(event.getSoundInstance() instanceof PositionedSoundInstance)) return;

        PositionedSoundInstance instance = (PositionedSoundInstance) event.getSoundInstance();
        Identifier identifier = instance.getId();

        if (identifier == SoundEvents.UI_TOAST_IN.getId() || identifier == SoundEvents.UI_TOAST_OUT.getId() || identifier == SoundEvents.UI_TOAST_CHALLENGE_COMPLETE.getId()) {
            event.setCancelled(true);
        }
    }
}
