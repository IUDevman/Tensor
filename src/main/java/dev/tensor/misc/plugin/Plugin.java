package dev.tensor.misc.plugin;

import dev.tensor.misc.imp.ClassLoader;
import dev.tensor.misc.imp.Global;
import dev.tensor.misc.imp.Methods;

import java.lang.annotation.*;

/**
 * @author IUDevman
 * @since 08-10-2021
 */

@SuppressWarnings("EmptyMethod")
public abstract class Plugin implements Global, ClassLoader, Methods {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        String version();
    }

    private Info getInfo() {
        return getClass().getAnnotation(Info.class);
    }

    private final String name = getInfo().name();
    private final String version = getInfo().version();

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public abstract void load();

    public void save() {

    }
}
