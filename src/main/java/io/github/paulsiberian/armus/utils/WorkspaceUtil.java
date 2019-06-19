/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.utils;

import io.github.paulsiberian.armus.workspace.WorkspaceException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkspaceUtil {

    private static Stream<Path> findPathStream(String s, File dir) throws IOException {
        var result = new ArrayList<File>();
        var path = Paths.get(dir.getPath());
        return Files.find(path, 100, (p, attr) -> {
            var file = p.toFile();
            return !file.isDirectory() && file.getName().contains(s);
        });
    }

    private static List<File> findFileList(String s, File dir) throws IOException {
        var result = new ArrayList<File>();
        findPathStream(s, dir).forEach(p -> result.add(p.toFile()));
        return result;
    }

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

    private static List<File> getFileList(File dir) throws IOException {
        return getFileList(dir, false);
    }

    private static List<File> getDirList(File dir) throws IOException {
        return getFileList(dir, true);
    }

    public static File[] findFileArray(String s, File dir) throws IOException {
        return findFileList(s, dir).toArray(new File[0]);
    }

    public static Stream<File> findFileStream(String s, File dir) throws IOException {
        return findFileList(s, dir).stream();
    }

    public static File[] getFileArray(File dir) throws IOException {
        return getFileList(dir).toArray(new File[0]);
    }

    public static Stream<File> getFileStream(File dir) throws IOException {
        return getFileList(dir).stream();
    }

    public static File[] getDirArray(File dir) throws IOException {
        return getDirList(dir).toArray(new File[0]);
    }

    public static Stream<File> getDirStream(File dir) throws IOException {
        return getDirList(dir).stream();
    }

    public static void mkDir(File dir) throws WorkspaceException {
        if (dir.mkdirs()) {
            System.out.println("Директория " + dir.getPath() + " создана.");
        } else {
            throw new WorkspaceException("Директория " + dir.getPath() + " не создана.", dir);
        }
    }

    public static void mkDir(String name, File dir) throws WorkspaceException {
        var d = new File(dir.getPath() + File.separator + name);
        if (!d.exists()) {
            mkDir(d);
        } else {
            System.out.println("Директория " + d.getPath() + " уже существует.");
        }
    }

    public static FileOutputStream mkFile(String namePath) throws IOException {
        var f = new File(namePath);
        if (!f.exists()) {
            if (f.createNewFile()) {
                System.out.println("Файл " + f.getPath() + " создан.");
                return new FileOutputStream(f);
            } else {
                throw new WorkspaceException("Файл " + f.getPath() + " не создан.", f);
            }
        } else {
            System.out.println("Файл " + f.getPath() + " уже существует.");
            return new FileOutputStream(f);
        }
    }

    public static FileOutputStream mkFile(String name, File dir) throws IOException {
        return mkFile(dir.getPath() + File.separator + name);
    }
}
