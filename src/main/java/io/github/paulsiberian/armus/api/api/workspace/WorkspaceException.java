/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.api.workspace;

import java.io.File;
import java.io.IOException;

public class WorkspaceException extends IOException {
    private File file;
    /**
     * Constructs an {@code WorkspaceException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     *
     * @param file The detail message (which is saved for later retrieval
     *                by the {@link #getFile()} method)
     */
    public WorkspaceException(String message, File file) {
        super(message);
        this.file = file;
    }

    /**
     * Constructs an {@code IOException} with {@code null}
     * as its error detail message.
     */
    public WorkspaceException(File file) {
        super("По пути " + file.getPath() + " ничего не найдено.");
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
