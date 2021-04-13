package dev.tensor.misc.imp;

import dev.tensor.Tensor;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

public abstract class Module implements Wrapper {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        Category category();

        boolean enabled() default false;

        boolean drawn() default true;

        boolean messages() default false;

        int bind() default GLFW.GLFW_KEY_UNKNOWN;
    }

    private Info getInfo() {
        return getClass().getAnnotation(Info.class);
    }

    private final String name = getInfo().name();
    private final Category category = getInfo().category();
    private boolean enabled = getInfo().enabled();
    private boolean drawn = getInfo().drawn();
    private boolean messages = getInfo().messages();
    private int bind = getInfo().bind();

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) enable();
        else disable();
    }

    public boolean isDrawn() {
        return this.drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean isMessages() {
        return this.messages;
    }

    public void setMessages(boolean messages) {
        this.messages = messages;
    }

    public int getBind() {
        return this.bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void enable() {
        this.enabled = true;
        onEnable();
        Tensor.EVENT_BUS.subscribe(this);
        if (isMessages()) MessageUtil.INSTANCE.sendClientMessage(Formatting.GREEN + getName() + " ENABLED!", true, true);
    }

    public void disable() {
        this.enabled = false;
        onDisable();
        Tensor.EVENT_BUS.unsubscribe(this);
        if (isMessages()) MessageUtil.INSTANCE.sendClientMessage(Formatting.RED + getName() + " DISABLED!", true, true);
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public void onTick() {

    }

    public void onRender2D() {

    }

    public void onRender3D() {

    }
}
