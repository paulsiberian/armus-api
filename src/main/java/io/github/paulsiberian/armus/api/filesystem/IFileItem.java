/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.filesystem;

import java.io.File;

/**
 * Интерфейс элемента GUI содержащего файл или директорию
 */
public interface IFileItem {

    /**
     * Метод получения файла или директории
     * @return файл или директория
     */
    File getFile();
}
