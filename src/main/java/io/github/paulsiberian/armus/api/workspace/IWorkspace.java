/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.workspace;

import java.io.File;
import java.util.stream.Stream;

public interface IWorkspace {
    void mkDir(String s);
    File[] getDirArray();
    Stream<File> getDirStream();
    File[] getFileArray();
    Stream<File> getFileStream();
    File[] findFileArray(String s);
    Stream<File> findFileStream(String s);
    File getRoot();
    String getRootPath();
}
