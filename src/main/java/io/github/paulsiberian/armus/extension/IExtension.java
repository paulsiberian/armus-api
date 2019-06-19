/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.extension;

public interface IExtension {
    boolean init();
    boolean isActive();
    void active(boolean b);
    String getName();
    String getVersion();
    String getAuthor();
    String getDescription();
}
