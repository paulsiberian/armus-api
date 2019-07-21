/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.extension;

/**
 * Интерфейс, от которого наследуются главные классы расширений
 */
public interface IExtension {
    /**
     * Метод содержащий код расширения
     */
    void start();

    /**
     * Метод инициализации расширения с реализацией по умолчанию. Использует метод {@link #start()}
     * @return true - расширение инициализировано / false - расширение неинициализировано
     */
    default boolean init() {
        try {
            start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
