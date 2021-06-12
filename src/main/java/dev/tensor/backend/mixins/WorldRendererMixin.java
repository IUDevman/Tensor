package dev.tensor.backend.mixins;

import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoParticles;
import dev.tensor.feature.modules.NoWeather;
import dev.tensor.misc.imp.Global;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-11-2021
 */

@Mixin(value = WorldRenderer.class, priority = Integer.MAX_VALUE)
public final class WorldRendererMixin implements Global {

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    public void renderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo callbackInfo) {
        NoWeather noWeather = ModuleManager.INSTANCE.getModule(NoWeather.class);

        if (noWeather.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
    public void tickRainSplashing(Camera camera, CallbackInfo callbackInfo) {
        NoWeather noWeather = ModuleManager.INSTANCE.getModule(NoWeather.class);

        if (noWeather.isEnabled()) callbackInfo.cancel();
    }

    @Inject(method = "spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    public void spawnParticle(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (this.isNull()) return;

        NoWeather noWeather = ModuleManager.INSTANCE.getModule(NoWeather.class);

        if (noWeather.isEnabled() && (this.getWorld().isRaining() || this.getWorld().isThundering()) && parameters.getType().equals(ParticleTypes.DRIPPING_WATER)) cir.cancel();

        NoParticles noParticles = ModuleManager.INSTANCE.getModule(NoParticles.class);

        if (noParticles.isEnabled()) {
            if (noParticles.all.getValue()) cir.cancel();
            else if (noParticles.ash.getValue() && (parameters.getType().equals(ParticleTypes.ASH) || parameters.getType().equals(ParticleTypes.WHITE_ASH))) cir.cancel();
            else if (noParticles.spore.getValue() && (parameters.getType().equals(ParticleTypes.CRIMSON_SPORE) || parameters.getType().equals(ParticleTypes.WARPED_SPORE) || parameters.getType().equals(ParticleTypes.MYCELIUM))) cir.cancel();
            else if (noParticles.explosion.getValue() && parameters.getType().equals(ParticleTypes.EXPLOSION)) cir.cancel();
            else if (noParticles.underWater.getValue() && parameters.getType().equals(ParticleTypes.UNDERWATER)) cir.cancel();
            else if (noParticles.lava.getValue() && parameters.getType().equals(ParticleTypes.LAVA)) cir.cancel();
            else if (noParticles.portal.getValue() && parameters.getType().equals(ParticleTypes.PORTAL)) cir.cancel();
            else if (noParticles.eating.getValue() && parameters.getType().equals(ParticleTypes.ITEM)) cir.cancel();
            else if (noParticles.potions.getValue() && parameters.getType().equals(ParticleTypes.ENTITY_EFFECT) && getPlayer().squaredDistanceTo(x, y, z) < 4) cir.cancel();
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index = 4)
    public boolean isSpectator(boolean spectator) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        return spectator || freecam.isEnabled();
    }
}
