package dev.tensor.misc.imp;

import dev.tensor.Tensor;
import me.zero.alpine.listener.Listenable;
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

@SuppressWarnings("EmptyMethod")
public abstract class Module implements Wrapper, Listenable, Utilities {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        Category category();

        int bind() default GLFW.GLFW_KEY_UNKNOWN;

        boolean drawn() default true;

        boolean messages() default false;

        boolean enabled() default false;
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

    public int getBind() {
        return this.bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
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

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) enable();
        else disable();
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    protected void enable() {
        this.enabled = true;
        Tensor.INSTANCE.EVENT_BUS.subscribe(this);
        if (!isNull()) {
            onEnable();
            if (isMessages()) this.sendReplaceableClientMessage(Formatting.GREEN + getName() + " ENABLED!", 666, true);
        }
    }

    protected void disable() {
        this.enabled = false;
        Tensor.INSTANCE.EVENT_BUS.unsubscribe(this);
        if (!isNull()) {
            onDisable();
            if (isMessages()) this.sendReplaceableClientMessage(Formatting.RED + getName() + " DISABLED!", 666, true);
        }
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
