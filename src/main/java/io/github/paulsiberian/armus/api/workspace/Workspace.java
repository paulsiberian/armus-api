/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.workspace;

import io.github.paulsiberian.armus.api.utils.WorkspaceUtil;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class Workspace implements IWorkspace {
    private File root;

    public Workspace(File root) throws WorkspaceException {
        if (root.exists()) {
            if (root.isDirectory()) {
                this.root = root;
            } else {
                throw new WorkspaceException("Файл " + getRootPath() + " не является директорией и не может использоваться в качестве корневой директории.", root);
            }
        } else {
            WorkspaceUtil.mkDir(root);
        }
    }

    public Workspace(String path) throws WorkspaceException {
        this(new File(path));
    }

    @Override
    public void mkDir(String name) {
        try {
            WorkspaceUtil.mkDir(name, root);
        } catch (WorkspaceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File[] getDirArray() {
        try {
            return WorkspaceUtil.getDirArray(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Stream<File> getDirStream() {
        try {
            return WorkspaceUtil.getDirStream(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File[] getFileArray() {
        try {
            return WorkspaceUtil.getFileArray(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Stream<File> getFileStream() {
        try {
            return WorkspaceUtil.getFileStream(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File[] findFileArray(String s) {
        try {
            return WorkspaceUtil.findFileArray(s, root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Stream<File> findFileStream(String s) {
        try {
            return WorkspaceUtil.findFileStream(s, root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getRoot() {
        return root;
    }

    @Override
    public String getRootPath() {
        return root.getPath();
    }
}
