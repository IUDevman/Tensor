package dev.tensor.backend.mixins;

import dev.darkmagician6.eventapi.EventHandler;
import dev.tensor.backend.events.BlockInteractEvent;
import dev.tensor.feature.managers.ModuleManager;
import dev.tensor.feature.modules.Freecam;
import dev.tensor.feature.modules.NoBreakDelay;
import dev.tensor.feature.modules.Reach;
import dev.tensor.misc.imp.Wrapper;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author IUDevman
 * @since 05-01-2021
 */

@Mixin(value = ClientPlayerInteractionManager.class, priority = Integer.MAX_VALUE)
public final class ClientPlayerInteractionManagerMixin implements Wrapper {

    @Shadow
    private int blockBreakingCooldown;

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"))
    public void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        EventHandler.call(new BlockInteractEvent(BlockInteractEvent.Type.Damage, pos));

        NoBreakDelay noBreakDelay = ModuleManager.INSTANCE.getModule(NoBreakDelay.class);

        if (noBreakDelay.isEnabled()) blockBreakingCooldown = 0;
    }

    @Inject(method = "breakBlock", at = @At("HEAD"))
    public void breakBlockHead(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        EventHandler.call(new BlockInteractEvent(BlockInteractEvent.Type.Break, pos));
    }

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir) {
        Reach reach = ModuleManager.INSTANCE.getModule(Reach.class);

        if (reach.isEnabled()) cir.setReturnValue(reach.distance.getValue().floatValue());
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    public void attackEntity(PlayerEntity player, Entity target, CallbackInfo callbackInfo) {
        Freecam freecam = ModuleManager.INSTANCE.getModule(Freecam.class);

        if (target.equals(player) || target.equals(freecam.getCameraEntity())) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
    public void interactEntity(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity.equals(player)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
