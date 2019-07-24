/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.workspace;

import java.io.File;
import java.util.stream.Stream;

/**
 * Интерфейс объекта рабочей области (директории) учёного секретаря выпускающей кафедры
 */
public interface IWorkspace {

    /**
     * Метод создаёт новую директорию в рабочёй директории
     * @param s - имя новой директории
     */
    void mkDir(String s);

    /**
     * Метод возвращает массив директорий из рабочей директории
     * @return массив директорий
     * @see #getDirStream()
     */
    File[] getDirArray();

    /**
     * Метод возвращает поток директорий из рабочей директории
     * @return поток директорий
     * @see #getDirArray()
     */
    Stream<File> getDirStream();

    /**
     * Метод возвращает массив файлов из рабочей директории
     * @return массив файлов
     * @see #getFileStream()
     */
    File[] getFileArray();

    /**
     * Метод возвращает поток файлов из рабочей директории
     * @return поток файлов
     * @see #getFileArray()
     */
    Stream<File> getFileStream();

    /**
     * Метод поиска файлов по всей рабочей директории
     * @param s - строка поиска
     * @return массив найденных файлов
     * @see #findFileStream(String)
     */
    File[] findFileArray(String s);

    /**
     * Метод поиска файлов по всей рабочей директории
     * @param s - строка поиска
     * @return поток найденных файлов
     * @see #findFileArray(String)
     */
    Stream<File> findFileStream(String s);

    /**
     * Метод возвращает корневую директорию рабочей области
     * @return корневая директория
     */
    File getRoot();

    /**
     * Метод возвращает абсолютный путь корневой директории
     * @return абсолютный путь
     */
    String getRootPath();
}
