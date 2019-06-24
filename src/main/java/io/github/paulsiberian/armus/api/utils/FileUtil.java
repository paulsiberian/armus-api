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
import org.apache.commons.io.FilenameUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.util.ArrayList;

public class FileUtil {

    public static FontIcon getIcon(File file, int iconSize) {
        var icon = new FontIcon();
        icon.setIconSize(iconSize);
        if (file.isDirectory()) {
            return new FontIcon(DirTreeItem.CLOSED_FOLDER_ICON);
        } else if (file.isFile()) {
            var ext = FilenameUtils.getExtension(file.getName());
            if (ext.contains("xls")) {
                icon.setIconLiteral("mdi-file-excel");
                icon.setIconColor(Paint.valueOf("#54C600"));
            } else if (ext.contains("doc")) {
                icon.setIconLiteral("mdi-file-word");
                icon.setIconColor(Paint.valueOf("#008CD3"));
            } else if (ext.contains("ppt")) {
                icon.setIconLiteral("mdi-file-powerpoint");
                icon.setIconColor(Paint.valueOf("#E75F1B"));
            } else {
                icon.setIconLiteral("mdi-note-outline");
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
        var sizeInByte = 0L;
        var path = Paths.get(file.toURI());
        try {
            sizeInByte = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var string = "";
        if (sizeInByte < 1024) {
            string = sizeInByte + "B";
            return string;
        } else if (sizeInByte < Math.pow(1024, 2)) {
            var sizeInKb = (long) sizeInByte / 1024;
            string = sizeInKb + "KB";
            return string;
        } else if (sizeInByte < Math.pow(1024, 3)) {
            var sizeInMb = (long) (sizeInByte / Math.pow(1024, 2));
            string = sizeInMb + "MB";
            return string;
        } else if (sizeInByte >= Math.pow(1024, 3)) {
            var sizeInGb = (long) (sizeInByte / Math.pow(1024, 3));
            string = sizeInGb + "GB";
            return string;
        }
        return string;
    }

    public static String getDate(FileTime fileTime) {
        var format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, SettingsManager.getInstance().getLocale());
        return format.format(fileTime.toMillis());
    }

    public static void exec(File file) {
        var ext = FilenameUtils.getExtension(file.getName());
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
