package dev.tensor.feature.modules;

import dev.tensor.Tensor;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Module;
import net.minecraft.util.crash.CrashReportSection;

import java.util.ArrayList;

/**
 * @author IUDevman
 * @since 09-05-2021
 */

@Module.Info(name = "DebugReport", category = Category.Client, drawn = false, enabled = true)
public final class DebugReport extends Module {

    public CrashReportSection getTensorCrashReportSection() {
        CrashReportSection crashReportSection = new CrashReportSection("Tensor Debug Report");

        ArrayList<String> moduleNameArray = new ArrayList<>();
        ArrayList<String> pluginNameArray = new ArrayList<>();

        Tensor.INSTANCE.MODULE_MANAGER.getEnabledModules().forEach(module -> moduleNameArray.add(module.getName()));
        Tensor.INSTANCE.PLUGIN_MANAGER.getPlugins().forEach(plugin -> pluginNameArray.add(plugin.getName() + " (" + plugin.getVersion() + ")"));

        crashReportSection.add("Name", Tensor.INSTANCE.MOD_NAME);
        crashReportSection.add("Version", Tensor.INSTANCE.MOD_VERSION);
        crashReportSection.add("Enabled Modules", "[" + Tensor.INSTANCE.MODULE_MANAGER.getEnabledModules().size() + "/" + Tensor.INSTANCE.MODULE_MANAGER.getModules().size() + "] " + moduleNameArray);
        crashReportSection.add("Commands", Tensor.INSTANCE.COMMAND_MANAGER.getCommands().size());
        crashReportSection.add("Plugins", "[" + Tensor.INSTANCE.PLUGIN_MANAGER.getPlugins().size() + "] " + pluginNameArray);

        return crashReportSection;
    }
}
