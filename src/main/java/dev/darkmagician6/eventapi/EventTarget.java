package dev.darkmagician6.eventapi;

import dev.darkmagician6.eventapi.imp.Priority;

import java.lang.annotation.*;

/**
 * @author DarkMagician6
 * @since 07-30-2013
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {

    byte value() default Priority.MEDIUM;
}
