package dev.tensor.backend.mixins;

import dev.tensor.Tensor;
import dev.tensor.backend.MixinPriority;
import dev.tensor.feature.modules.DebugReport;
import dev.tensor.misc.imp.Global;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.List;

/**
 * @author IUDevman
 * @since 09-05-2021
 */

@Mixin(value = CrashReport.class, priority = MixinPriority.VALUE)
public final class CrashReportMixin implements Global {

    @Shadow
    @Final
    private List<CrashReportSection> otherSections;

    @Inject(method = "writeToFile", at = @At("HEAD"))
    public void writeToFile(File file, CallbackInfoReturnable<Boolean> cir) {
        DebugReport debugReport = Tensor.INSTANCE.MODULE_MANAGER.getModule(DebugReport.class);

        if (debugReport != null && debugReport.isEnabled()) {
            this.otherSections.add(debugReport.getTensorCrashReportSection());
            Tensor.INSTANCE.LOGGER.info("Added debug information to crash report!");
        }
    }
}
