package dev.tensor.backend.mixins;

import com.mojang.authlib.GameProfile;
import dev.tensor.feature.managers.CapeManager;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Capes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-26-2021
 */

@Mixin(value = AbstractClientPlayerEntity.class, priority = Integer.MAX_VALUE)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    public void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
        Capes capes = ModuleManager.INSTANCE.getModule(Capes.class);

        if (capes.isEnabled()) {
            cir.setReturnValue(CapeManager.INSTANCE.getCape(this.getEntityName()));
        }
    }
}
