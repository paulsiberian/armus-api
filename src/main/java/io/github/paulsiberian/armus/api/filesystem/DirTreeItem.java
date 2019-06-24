/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.filesystem;

import javafx.scene.control.TreeItem;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

public class DirTreeItem extends TreeItem<String> implements IFileItem {
    private File file;

    public DirTreeItem(File file) {
        super(file.getName());
        this.file = file;

        expandedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (t1) {
                setGraphic(new FontIcon(OPENED_FOLDER_ICON));
            } else {
                setGraphic(new FontIcon(CLOSED_FOLDER_ICON));
            }
        }));

        if (file.isDirectory() && !file.isHidden()) {
            setGraphic(new FontIcon(CLOSED_FOLDER_ICON));
            var files = file.listFiles();
            if (files != null) {
                for (var f : files) {
                    if (f.isDirectory() && !f.isHidden()) {
                        getChildren().add(new DirTreeItem(f));
                    }
                }
            }
        }

/*
        addEventHandler(TreeItem.branchExpandedEvent(), (EventHandler) event -> {
            var source = (DirTreeItem) event.getSource();
            var sourceFile = source.getFile();
            if (sourceFile.isDirectory() && source.isExpanded()) {
                source.setGraphic(CLOSED_FOLDER_ICON);
            }
        });

        addEventHandler(TreeItem.branchCollapsedEvent(), (EventHandler) event -> {
            var source = (DirTreeItem) event.getSource();
            var sourceFile = source.getFile();
            if (sourceFile.isDirectory() && !source.isExpanded()) {
                source.setGraphic(CLOSED_FOLDER_ICON);
            }
        });*/
    }

    @Override
    public File getFile() {
        return file;
    }

    public final static String OPENED_FOLDER_ICON = "mdi-folder-outline";
    public final static String CLOSED_FOLDER_ICON = "mdi-folder";
}
