/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.extension;

import java.util.Properties;

public interface IExtension {
    boolean init();
    void start();
    void setProperties(Properties props);
    String getProperty(String key);
}
