package dev.tensor.misc.imp;

import java.lang.annotation.*;

/**
 * @author IUDevman
 * @since 08-10-2021
 */

public abstract class Plugin implements Global, ClassLoader, Methods {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        String author() default "none";

        String version() default "none";
    }

    public Info getInfo() {
        return getClass().getAnnotation(Info.class);
    }

    private final String name = getInfo().name();
    private final String author = getInfo().author();
    private final String version = getInfo().version();

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getVersion() {
        return this.version;
    }

    public abstract void load();

    public void save() {

    }
}
