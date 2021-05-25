package dev.tensor.backend.events;

import dev.darkmagician6.eventapi.imp.EventCancellable;
import net.minecraft.client.sound.SoundInstance;

/**
 * @author IUDevman
 * @since 05-25-2021
 */

public final class PlaySoundEvent extends EventCancellable {

    private final SoundInstance soundInstance;

    public PlaySoundEvent(SoundInstance soundInstance) {
        this.soundInstance = soundInstance;
    }

    public SoundInstance getSoundInstance() {
        return this.soundInstance;
    }
}
