/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.filesystem;

import javafx.scene.control.TreeItem;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

public class DirTreeItem extends TreeItem<String> implements IFileItem {
    private File file;

    public DirTreeItem(File file) {
        super(file.getName());
        this.file = file;
        var color = Paint.valueOf("#efa94a");

        expandedProperty().addListener(((observableValue, aBoolean, t1) -> {
            var icon = new FontIcon();
            icon.setIconColor(color);
            if (t1) {
                icon.setIconLiteral(OPENED_FOLDER_ICON);
                setGraphic(icon);
            } else {
                icon.setIconLiteral(CLOSED_FOLDER_ICON);
                setGraphic(icon);
            }
        }));

        if (file.isDirectory() && !file.isHidden()) {
            var icon = new FontIcon(CLOSED_FOLDER_ICON);
            icon.setIconColor(color);
            setGraphic(icon);
            var files = file.listFiles();
            if (files != null) {
                for (var f : files) {
                    if (f.isDirectory() && !f.isHidden()) {
                        getChildren().add(new DirTreeItem(f));
                    }
                }
            }
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    private final static String OPENED_FOLDER_ICON = "mdi-folder-outline";
    private final static String CLOSED_FOLDER_ICON = "mdi-folder";
}
