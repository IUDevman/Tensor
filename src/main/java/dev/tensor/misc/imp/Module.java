package dev.tensor.misc.imp;

import dev.tensor.Tensor;
import dev.tensor.misc.util.MessageUtil;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TextColor;
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
    private KeyBinding keyBinding = new KeyBinding(name, InputUtil.Type.KEYSYM, getInfo().bind(), Tensor.MOD_NAME);

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
        this.enabled = enabled;

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

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public void setKeyBinding(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public void setKeyBinding(int keyBinding) {
        this.keyBinding = new KeyBinding(name, InputUtil.Type.KEYSYM, keyBinding, Tensor.MOD_NAME);
    }

    private void enable() {
        onEnable();
        if (isMessages()) MessageUtil.INSTANCE.sendClientMessage(TextColor.fromFormatting(Formatting.GREEN) + getName() + " ENABLED!", true, true);
    }

    private void disable() {
        onDisable();
        if (isMessages()) MessageUtil.INSTANCE.sendClientMessage(TextColor.fromFormatting(Formatting.RED) + getName() + " DISABLED!", true, true);
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
