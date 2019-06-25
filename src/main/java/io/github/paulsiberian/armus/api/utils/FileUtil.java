/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import io.github.paulsiberian.armus.api.SettingsManager;
import io.github.paulsiberian.armus.api.filesystem.DirTreeItem;
import io.github.paulsiberian.armus.api.filesystem.FileInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.Ikonli;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.util.ArrayList;

public class FileUtil {

    public static String getFileExtension(File file) {
        if (file.isFile()) {
            var name = file.getName().toLowerCase();
            var lastIndex = name.lastIndexOf('.');
            if (lastIndex != -1) {
                return name.substring(lastIndex + 1);
            } else {
                return "";
            }
        }
        return null;
    }

    public static FontIcon getIcon(File file, int iconSize) {
        var icon = new FontIcon("mdi-file:" + iconSize + ":#e8bc90");
        if (file.isDirectory()) {
            icon.setIconLiteral("mdi-folder:" + iconSize + ":#efa94a");
        } else if (file.isFile()) {
            var ext = getFileExtension(file);
            if (ext.contains("xls")) {
                icon.setIconLiteral("mdi-file-excel:" + iconSize + ":#54C600");
            } else if (ext.contains("doc")) {
                icon.setIconLiteral("mdi-file-word:" + iconSize + ":#008CD3");
            } else if (ext.contains("ppt")) {
                icon.setIconLiteral("mdi-file-powerpoint:" + iconSize + ":#E75F1B");
            }
            // etc
        }
        return icon;
    }

    public static ObservableList<FileInfo> getFiles(File file) {
        var list = new ArrayList<FileInfo>();
        if (file.isDirectory()) {
            var files = file.listFiles();
            if (files != null) {
                for (var f : files) {
                    list.add(new FileInfo(f));
                }
            }
        }
        return FXCollections.observableArrayList(list);
    }

    public static String getSize(File file) {
        var string = "";
        if (file.isFile()) {
            var sizeInByte = 0L;
            var path = Paths.get(file.toURI());
            try {
                sizeInByte = Files.size(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sizeInByte < 1024) {
                string = (double) sizeInByte + "B";
            } else if (sizeInByte < Math.pow(1024, 2)) {
                var sizeInKb = sizeInByte / 1024D;
                string = sizeInKb + "KB";
            } else if (sizeInByte < Math.pow(1024, 3)) {
                var sizeInMb = sizeInByte / Math.pow(1024, 2);
                string = sizeInMb + "MB";
            } else if (sizeInByte >= Math.pow(1024, 3)) {
                var sizeInGb = sizeInByte / Math.pow(1024, 3);
                string = sizeInGb + "GB";
            }
        }
        return string;
    }

    public static String getDate(FileTime fileTime) {
        var format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, SettingsManager.getInstance().getLocale());
        return format.format(fileTime.toMillis());
    }

    public static void exec(File file) {
        var ext = getFileExtension(file);
        if (OSUtil.isNix()) {
            if (ext.contains("xls")) execNix("--calc", file);
            if (ext.contains("doc")) execNix("--writer", file);
            if (ext.contains("ppt")) execNix("--impress", file);
        }
    }

    private static void execNix(String command, File file) {
        try {
            var process = new ProcessBuilder("libreoffice",  command, file.getName());
            process.directory(file.getParentFile());
            process.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void execWin(File file) {
    }

    private static void execMac(File file) {
    }

    public static void openLink(String uri) {
        var desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(uri));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
