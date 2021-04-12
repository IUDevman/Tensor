package dev.tensor.misc.util;

import dev.tensor.Tensor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author IUDevman
 * @author lukflug
 * @since 04-06-2021
 */

public final class ClassUtil {

    private static final boolean debug = false;

    public static ArrayList<Class<?>> findClassesForPath(String path) {
        if (debug) Tensor.LOGGER.info("Loading classes from " + path + " ...");

        final ArrayList<Class<?>> foundClasses = new ArrayList<>();
        String resource = Objects.requireNonNull(ClassUtil.class.getClassLoader().getResource(path.replace(".", "/"))).getPath();

        if (resource.contains("!")) {

            try {
                ZipInputStream file = new ZipInputStream(new URL(resource.substring(0, resource.lastIndexOf('!'))).openStream());

                ZipEntry entry;
                while ((entry = file.getNextEntry()) != null) {
                    String name = entry.getName();

                    if (name.startsWith(path.replace(".", "/") + "/") && name.endsWith(".class")) {

                        try {
                            Class<?> clazz = Class.forName(name.substring(0, name.length() - 6).replace("/", "."));
                            foundClasses.add(clazz);
                            if (debug) Tensor.LOGGER.info("Loaded " + clazz.getName() + "!");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {

                URL classPathURL = ClassUtil.class.getClassLoader().getResource(path.replace(".", "/"));

                if (classPathURL != null) {

                    File file = new File(classPathURL.getFile());

                    if (file.exists()) {
                        String[] classNamesFound = file.list();

                        if (classNamesFound != null) {

                            for (String className : classNamesFound) {

                                if (className.endsWith(".class")) {
                                    foundClasses.add(Class.forName(path + "." + className.substring(0, className.length() - 6)));
                                    if (debug) Tensor.LOGGER.info("Loaded " + className + "!");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        foundClasses.sort(Comparator.comparing(Class::getName));
        return foundClasses;
    }
}