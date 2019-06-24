/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api;

import io.github.paulsiberian.armus.api.utils.WorkspaceUtil;
import io.github.paulsiberian.armus.api.workspace.WorkspaceException;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class WorkspaceManager {
    private File rootDir;
    private Property<File> currentDir;
    private DirectoryChooser chooser;

    private static WorkspaceManager ourInstance = new WorkspaceManager();

    private WorkspaceManager() {
    }

    public static WorkspaceManager getInstance() {
        return ourInstance;
    }

    public void init(File rootDir) {
        this.rootDir = rootDir;
        if (!rootDir.exists()) {
            try {
                WorkspaceUtil.mkDir(rootDir);
            } catch (WorkspaceException e) {
                e.printStackTrace();
            }
        }
        currentDir = new SimpleObjectProperty<>(rootDir);
    }

    public File getCurrentDir() {
        return currentDir.getValue();
    }

    public Property<File> currentDirProperty() {
        return currentDir;
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir.setValue(currentDir);
    }

    public File getRootDir() {
        return rootDir;
    }

    public void setRootDir(File rootDir) {
        this.rootDir = rootDir;
    }

    public DirectoryChooser getChooser() {
        return chooser;
    }

    public void setChooser(DirectoryChooser chooser) {
        this.chooser = chooser;
    }
}
