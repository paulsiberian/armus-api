/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import io.github.paulsiberian.armus.api.workspace.WorkspaceException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс состоит из статичных методов для работы с файлами и директориями рабочей директории учёного секретаря выпускающей кафедры
 */
public class WorkspaceUtil {

    /**
     * Метод поиска файлов по имени
     * @param s - строка для поиска
     * @param dir - директория, где ведётся поиск
     * @return поток путей найденных файлов
     * @throws IOException если произошла ошибка при переборе файлов в директории
     */
    private static Stream<Path> findPathStream(String s, File dir) throws IOException {
        var result = new ArrayList<File>();
        var path = Paths.get(dir.getPath());
        return Files.find(path, 100, (p, attr) -> {
            var file = p.toFile();
            return !file.isDirectory() && file.getName().contains(s);
        });
    }

    /**
     * Метод поиска файлов по имени
     * @param s - строка для поиска
     * @param dir - директория, где ведётся поиск
     * @return список найденных файлов
     * @throws IOException если произошла ошибка при переборе файлов в директории
     */
    private static List<File> findFileList(String s, File dir) throws IOException {
        var result = new ArrayList<File>();
        findPathStream(s, dir).forEach(p -> result.add(p.toFile()));
        return result;
    }

    /**
     * Метод получения списка файлов или директорий из родительской директории
     * @param dir - родительская директория
     * @param isDirs - true, если необходимо получить список директорий / false, если необходимо получить список файлов
     * @return список файлов или директорий
     * @throws IOException если произошла ошибка получении списка файлов
     * @see #getFileList(File)
     * @see #getDirList(File)
     */
    private static List<File> getFileList(File dir, boolean isDirs) throws IOException {
        if (dir.exists()) {
            var result = new ArrayList<File>();
            var files = dir.listFiles();
            for (var file : files) {
                if (file.isDirectory() == isDirs) {
                    result.add(file);
                } else if (file.isFile()) {
                    result.add(file);
                }
            }
            return result;
        } else {
            throw new WorkspaceException(dir);
        }
    }

    /**
     * Метод получения списка файлов из родительской директории
     * @param dir - родительская директория
     * @return список файлов
     * @throws IOException если произошла ошибка получении списка файлов
     * @see #findFileList(String, File)
     */
    private static List<File> getFileList(File dir) throws IOException {
        return getFileList(dir, false);
    }

    /**
     * Метод получения списка директорий из родительской директории
     * @param dir - родительская директория
     * @return список директорий
     * @throws IOException если произошла ошибка получении списка директорий
     * @see #findFileList(String, File)
     */
    private static List<File> getDirList(File dir) throws IOException {
        return getFileList(dir, true);
    }

    /**
     * Метод создающий файл
     * @param f - файл
     * @return поток вывода файла
     * @throws IOException если произошла ошибка при создании файла
     */
    private static FileOutputStream createFile(File f) throws IOException {
        if (f.createNewFile()) {
            System.out.println("Файл " + f.getPath() + " создан.");
            return new FileOutputStream(f);
        } else {
            throw new WorkspaceException("Файл " + f.getPath() + " не создан.", f);
        }
    }

    /**
     * Метод создающий копию директории
     * @param src - исходная директория
     * @param dest - новая директория
     * @throws IOException если произошла ошибка при копировании
     */
    private static void cpDir(File src, File dest) throws IOException {
        Files.walk(src.toPath()).forEach(s -> {
            try {
                Files.copy(s, dest.toPath().resolve(src.toPath().relativize(s)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Метод поиска файлов по имени
     * @param s - строка для поиска
     * @param dir - директория, где ведётся поиск
     * @return массив найденных файлов
     * @throws IOException если произошла ошибка при переборе файлов
     * @see #findFileStream(String, File)
     */
    public static File[] findFileArray(String s, File dir) throws IOException {
        return findFileList(s, dir).toArray(new File[0]);
    }

    /**
     * Метод поиска файлов по имени
     * @param s - строка для поиска
     * @param dir - директория, где ведётся поиск
     * @return поток найденных файлов
     * @throws IOException если произошла ошибка при переборе файлов
     * @see #findFileArray(String, File)
     */
    public static Stream<File> findFileStream(String s, File dir) throws IOException {
        return findFileList(s, dir).stream();
    }

    /**
     * Метод получения файлов из родительской директории
     * @param dir - родительская директория
     * @return массив файлов
     * @throws IOException если произошла ошибка при получении списка файлов
     * @see #getFileStream(File)
     */
    public static File[] getFileArray(File dir) throws IOException {
        return getFileList(dir).toArray(new File[0]);
    }

    /**
     * Метод получения файлов из родительской директории
     * @param dir - родительская директория
     * @return поток файлов
     * @throws IOException если произошла ошибка при получении списка файлов
     * @see #getFileArray(File)
     */
    public static Stream<File> getFileStream(File dir) throws IOException {
        return getFileList(dir).stream();
    }

    /**
     * Метод получения директорий из родительской директории
     * @param dir - родительская директория
     * @return массив директорий
     * @throws IOException если произошла ошибка при получении списка директорий
     * @see #getDirStream(File)
     */
    public static File[] getDirArray(File dir) throws IOException {
        return getDirList(dir).toArray(new File[0]);
    }

    /**
     * Метод получения директорий из родительской директории
     * @param dir - родительская директория
     * @return поток директорий
     * @throws IOException если произошла ошибка при получении списка директорий
     * @see #getDirArray(File)
     */
    public static Stream<File> getDirStream(File dir) throws IOException {
        return getDirList(dir).stream();
    }

    /**
     * Метод создающий директорию
     * @param dir - новая директория
     * @throws WorkspaceException если произошла ошибка при создании новой директории
     * @see #mkDir(String, File)
     */
    public static void mkDir(File dir) throws WorkspaceException {
        if (dir.mkdirs()) {
            System.out.println("Директория " + dir.getPath() + " создана.");
        } else {
            throw new WorkspaceException("Директория " + dir.getPath() + " не создана.", dir);
        }
    }

    /**
     * Метод создающий директорию в родительской директории
     * @param name - имя новой директории
     * @param dir - родительская директория
     * @throws WorkspaceException если произошла ошибка при создании новой директории
     * @see #mkDir(File)
     */
    public static void mkDir(String name, File dir) throws WorkspaceException {
        var d = new File(dir.getPath() + File.separator + name);
        if (!d.exists()) {
            mkDir(d);
        } else {
            System.out.println("Директория " + d.getPath() + " уже существует.");
        }
    }

    /**
     * Метод создающий файл по полному пути
     * @param namePath - полный путь нового файла
     * @return поток вывода нового файла
     * @throws IOException если произошла ошибка при создании нового файла
     * @see #mkFile(String, File)
     * @see #mkFile(File)
     */
    public static FileOutputStream mkFile(String namePath) throws IOException {
        return mkFile(new File(namePath));
    }

    /**
     * Метод создающий файл в родительской директории
     * @param name - имя нового файла
     * @param dir - родительская директория
     * @return поток вывода нового файла
     * @throws IOException если произошла ошибка при создании нового файла
     * @see #mkFile(String)
     * @see #mkFile(File)
     */
    public static FileOutputStream mkFile(String name, File dir) throws IOException {
        return mkFile(dir.getPath() + File.separator + name);
    }

    /**
     * Метод создающий файл
     * @param f - новый файл
     * @return поток вывода нового файла
     * @throws IOException если произошла ошибка при создании нового файла
     * @see #mkFile(String)
     * @see #mkFile(String, File)
     */
    public static FileOutputStream mkFile(File f) throws IOException {
        if (!f.exists()) {
            return createFile(f);
        } else if (f.isFile()) {
            System.out.println("Файл " + f.getPath() + " уже существует.");
            return new FileOutputStream(f);
        } else {
            return createFile(f);
        }
    }

    /**
     * Метод копирующий файл или рекурсивно директорию
     * @param src - исходный файл или директория
     * @param dest - новый файл или директория
     * @throws IOException если возникает ошибка при копировании
     */
    public static void cp(File src, File dest) throws IOException {
        if (src.exists()) {
            if (src.isFile()) {
                if (dest.exists() && dest.isFile()) {
                    System.out.println("Файл уже существует");
                } else {
                    mkDir(new File(dest.getParentFile().getPath()));
                    Files.copy(src.toPath(), dest.toPath());
                }
            } else if (src.isDirectory()) {
                cpDir(src, dest);
            }
        }
    }
}
