package dev.tensor.feature.managers;

import dev.tensor.Tensor;
import dev.tensor.feature.modules.*;
import dev.tensor.misc.imp.Category;
import dev.tensor.misc.imp.Manager;
import dev.tensor.misc.imp.Module;
import dev.tensor.misc.plugin.PluginEntryPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public final class ModuleManager implements Manager {

    private final ArrayList<Module> modules = new ArrayList<>();

    @Override
    public void load() {
        Tensor.INSTANCE.LOGGER.info("ModuleManager");

        this.addModule(new AntiNarrator());
        this.addModule(new AutoRespawn());
        this.addModule(new AutoTool());
        this.addModule(new AutoWalk());
        this.addModule(new CameraClip());
        this.addModule(new Capes());
        this.addModule(new ChatSuffix());
        this.addModule(new ClickGUI());
        this.addModule(new Commands());
        this.addModule(new Coordinates());
        this.addModule(new Criticals());
        this.addModule(new DeathDebug());
        this.addModule(new DebugReport());
        this.addModule(new Disconnect());
        this.addModule(new ElytraFlight());
        this.addModule(new FakePlayer());
        this.addModule(new Flight());
        this.addModule(new Freecam());
        this.addModule(new FullBright());
        this.addModule(new InventoryMove());
        this.addModule(new Jesus());
        this.addModule(new LiquidInteract());
        this.addModule(new MiddleClickFriend());
        this.addModule(new MiddleClickPearl());
        this.addModule(new NoBreakDelay());
        this.addModule(new NoFog());
        this.addModule(new NoGlitchBlock());
        this.addModule(new NoOverlay());
        this.addModule(new NoParticles());
        this.addModule(new NoPlaceDelay());
        this.addModule(new NoPortalEffect());
        this.addModule(new NoPush());
        this.addModule(new NoRender());
        this.addModule(new NoSlow());
        this.addModule(new NoViewBob());
        this.addModule(new NoWeather());
        this.addModule(new OnGround());
        this.addModule(new SafeWalk());
        this.addModule(new Sneak());
        this.addModule(new Spammer());
        this.addModule(new Sprint());
        this.addModule(new Timer());
        this.addModule(new UnfocusedCPU());
        this.addModule(new VanillaSpoof());
        this.addModule(new Velocity());
        this.addModule(new ViewModel());
        this.addModule(new VisualRange());
        this.addModule(new Watermark());
        this.addModule(new XCarry());

        this.postSortModules();
    }

    public void postSortModules() {
        this.modules.sort(Comparator.comparing(Module::getName));
    }

    @PluginEntryPoint
    public void addModule(Module module) {
        if (!module.getClass().isAnnotationPresent(Module.Info.class)) {
            return;
        }

        this.modules.add(module);
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public ArrayList<Module> getModulesInCategory(Category category) {
        return this.modules.stream().filter(module -> module.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Module> getEnabledModules() {
        return this.modules.stream().filter(Module::isEnabled).collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> aClass) {
        return (T) this.modules.stream().filter(module -> module.getClass().equals(aClass)).findFirst().orElse(null);
    }

    public Module getModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
