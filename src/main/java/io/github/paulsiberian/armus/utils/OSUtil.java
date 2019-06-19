/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.utils;

public class OSUtil {
    public static String OSName() {
        return System.getProperty("os.name");
    }

    public static String OSVerion() {
        return System.getProperty("os.version");
    }

    public static boolean isMac() {
        return OSName().toLowerCase().contains("mac");
    }

    public static boolean isNix() {
        return OSName().toLowerCase().contains("nix");
    }

    public static boolean isWindows() {
        return OSName().toLowerCase().contains("win");
    }
}
