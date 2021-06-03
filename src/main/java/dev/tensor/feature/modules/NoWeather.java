package dev.tensor.feature.modules;

import dev.tensor.backend.events.PacketEvent;
import dev.tensor.misc.event.EventTarget;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Module.Info(name = "NoWeather", category = Category.Render)
public final class NoWeather extends Module {

    @SuppressWarnings("unused")
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getType() != PacketEvent.Type.Receive) return;

        if (event.getPacket() instanceof PlaySoundS2CPacket) {
            PlaySoundS2CPacket packet = (PlaySoundS2CPacket) event.getPacket();

            if (packet.getSound().equals(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER)) event.setCancelled(true);
            else if (packet.getSound().equals(SoundEvents.WEATHER_RAIN) || packet.getSound().equals(SoundEvents.WEATHER_RAIN_ABOVE)) event.setCancelled(true);
        }
    }
}
