/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

/**
 * Класс состоит из статичных методов возвращающих информацию об оперционной системе (ОС), на которой запущено приложение
 */
public class OSUtil {

    /**
     * Метод получения архитектуры ОС, на которой запущено приложение
     * @return архитектура ОС
     */
    public static String OSArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Метод получения названия ОС, на которой запущено приложение
     * @return название ОС
     */
    public static String OSName() {
        return System.getProperty("os.name");
    }

    /**
     * Метод получения версии ОС, на которой запущено приложение
     * @return версия ОС
     */
    public static String OSVerion() {
        return System.getProperty("os.version");
    }

    /**
     * Метод проверяет, принадлежит ли ОС к семейству Mac
     * @return true, если ОС из семейства Mac / false, иначе
     */
    public static boolean isMac() {
        return OSName().toLowerCase().contains("mac");
    }

    /**
     * Метод проверяет, принадлежит ли ОС к семейству Unix
     * @return true, если ОС из семейства Unix / false, иначе
     */
    public static boolean isNix() {
        var name = OSName().toLowerCase();
        return name.contains("nix") || name.contains("nux");
    }

    /**
     * Метод проверяет, принадлежит ли ОС к семейству Windows
     * @return true, если ОС из семейства Windows / false, иначе
     */
    public static boolean isWindows() {
        return OSName().toLowerCase().contains("win");
    }
}
