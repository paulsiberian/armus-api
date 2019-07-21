/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.extension;

import io.github.paulsiberian.armus.api.utils.ExtensionUtil;
import io.github.paulsiberian.armus.api.utils.WorkspaceUtil;
import io.github.paulsiberian.armus.api.workspace.WorkspaceException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Класс предназначенный для загрузки расширений
 * @param <T> - потомок {@link IExtension}
 */
public class ExtensionLoader<T extends IExtension> {
    /** Неупорядоченная коллекция расширений */
    private Set<T> extensions;
    /** Главные классы расширений (ключ - главный класс, значение - свойства) */
    private Map<Class<?>, Properties> classes;
    /** Корневая директория с расширениями */
    private File root;

    /**
     * Конструктор
     * @param root - корневая директория с расширениями
     */
    public ExtensionLoader(File root) {
        this.root = root;
        extensions = new HashSet<>();
        classes = new HashMap<>();
    }

    /**
     * Метод загрузки расширений из корневой директории с расширениями
     */
    public void load() {
        if (root.exists() && root.isDirectory()) {
            var jars = ExtensionUtil.getJarFiles(root);
            if (jars != null) {
                for (var jar : jars) {
                    classes.putAll(ExtensionUtil.loadMainClass(jar, getClass()));
                }
            }
        } else {
            try {
                WorkspaceUtil.mkDir(root);
            } catch (WorkspaceException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод инициализации активных загруженных расширений
     */
    public void enableExtensions() {
        classes.forEach((c, p) -> {
            if (ExtensionUtil.isActivated(p, root)) {
                try {
                    var extension = (T) c.getConstructor().newInstance();
                    if (extension.init()) {
                        System.out.println("Расширение \"" + p.getProperty(ExtensionUtil.NAME) + "\" инициализировано.");
                        extensions.add(extension);
                    } else {
                        System.out.println("Расширение \"" + p.getProperty(ExtensionUtil.NAME) + "\" не удалось инициализировать.");
                    }
                } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Установлено расширений: " + classes.size());
        System.out.println("Загружено расширений: " + extensions.size());
    }

    /**
     * Метод получения расширений
     * @return неупорядоченная коллекция расширений
     */
    public Set<T> getExtensions() {
        return extensions;
    }

    /**
     * Метод получения главных классов расширений
     * @return ключ - главный класс, значение - свойства
     */
    public Map<Class<?>, Properties> getClasses() {
        return classes;
    }

    /**
     * Метод получения корневой директории с расширениями
     * @return корневая директория с расширениями
     */
    public File getRoot() {
        return root;
    }
}
