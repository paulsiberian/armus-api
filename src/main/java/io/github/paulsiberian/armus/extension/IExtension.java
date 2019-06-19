/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.extension;

public interface IExtension {
    void start();
    default boolean init() {
        try {
            start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
