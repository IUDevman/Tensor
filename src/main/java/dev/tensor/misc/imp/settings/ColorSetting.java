package dev.tensor.misc.imp.settings;

import dev.tensor.misc.imp.Setting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author IUDevman
 * @since 04-12-2021
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColorSetting {
    Setting setting();
}
