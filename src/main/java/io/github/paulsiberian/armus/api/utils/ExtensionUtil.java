/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipFile;

/**
 * Класс состоит из ститачных методов для работы с расширениями
 * @author paulsiberian
 */
public class ExtensionUtil {
    private static final String EXT = ".properties";
    private static final String EXTENSION = "extension";
    private static final String EXTENSION_LIST_FILE_NAME = EXTENSION + "_list" + EXT;
    private static final String EXTENSION_PROPERTIES_FILE_NAME = EXTENSION + EXT;

    /**
     * Метод получает полное имя главного класса из свойств расширения по ключу {@link #MAIN_CLASS}
     * @param p - свойства расширения
     * @return полное имя главного класса расширения
     */
    private static String getMainClass(Properties p) {
        return p.getProperty(MAIN_CLASS);
    }

    /**
     * Метод получает свойства расширения
     * @param jar - Jar-файл расширения
     * @return свойства расширения
     */
    public static Properties getExtensionProperties(File jar) {
        try {
            var zipJar = new ZipFile(jar);
            var stream = zipJar.getInputStream(zipJar.getEntry(EXTENSION_PROPERTIES_FILE_NAME));
            var properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            System.out.println("Ошибка во время загрузки файла " + jar.getName());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Метод получает массив Jar-файлов из директории с расширениями
     * @param root - директория с расширениями
     * @return массив Jar-файлов
     */
    public static File[] getJarFiles(File root) {
        return root.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".jar"));
    }

    /**
     * Метод активации расширения
     * @param p - свойства расширения
     * @param b - true/false
     * @param f - файл свойств со списком расширений
     */
    public static void activate(Properties p, boolean b, File f) {
        var mainClass = getMainClass(p);
        var properties = new Properties();
        properties.setProperty(mainClass, String.valueOf(b));
        try {
            properties.store(WorkspaceUtil.mkFile(f), p.getProperty(NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод проверяет, активировано ли расширение
     * @param p - свойства расширения
     * @param root - директория с Jar-файлами расширений
     * @return true/false
     */
    public static boolean isActivated(Properties p, File root) {
        var mainClass = getMainClass(p);
        var file = new File(root.getPath() + File.separator + EXTENSION_LIST_FILE_NAME);
        var properties = new Properties();
        if (!file.exists()) {
            try {
                properties.setProperty(mainClass, "true");
                properties.store(WorkspaceUtil.mkFile(file), p.getProperty(NAME));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.isFile()) {
            try {
                properties.load(new FileInputStream(file));
                return Boolean.valueOf(properties.getProperty(mainClass));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Метод получает главный класс расширения и его свойства
     * @param jar - Jar-файл расширения
     * @param loaderClass - загрузчик классов
     * @return ключ - главный класс расширения, значение - свойства расширения
     */
    public static Map<Class<?>, Properties> loadMainClass(File jar, Class<?> loaderClass) {
        var map = new HashMap<Class<?>, Properties>();
        String mainClass = null;
        try {
            var properties = getExtensionProperties(jar);
            if (properties != null) {
                mainClass = getMainClass(properties);
                var loader = URLClassLoader.newInstance(
                        new URL[] { jar.toURI().toURL() },
                        loaderClass.getClassLoader()
                );
                map.put(loader.loadClass(mainClass), properties);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(
                    "Класс не найден! Возможно он неправильно определён в " +
                    EXTENSION_PROPERTIES_FILE_NAME + ": " +
                    jar.getName() + ' ' + MAIN_CLASS + '=' + mainClass
            );
            e.printStackTrace();
        }
        return map;
    }

    /* Ключи свойств расширения */
    /** Ключ свойства {@value #NAME} - название расширения */
    public static final String NAME = EXTENSION + ".name";
    /** Ключ свойства {@value #DESCRIPTION} - описание расширения */
    public static final String DESCRIPTION = EXTENSION + ".description";
    /** Ключ свойства {@value #URL} - ссылка на страницу расширения */
    public static final String URL = EXTENSION + ".url";
    /** Ключ свойства {@value #AUTHOR_NAME} - автор расширения */
    public static final String AUTHOR_NAME = EXTENSION + ".author.name";
    /** Ключ свойства {@value #AUTHOR_EMAIL} - e-mail автора расширения */
    public static final String AUTHOR_EMAIL = EXTENSION + ".author.email";
    /** Ключ свойства {@value #MAIN_CLASS} - полное имя главного класса расширения */
    public static final String MAIN_CLASS = EXTENSION + ".main-class";
    /** Ключ свойства {@value #VERSION} - версия расширения */
    public static final String VERSION = EXTENSION + ".version";
}
