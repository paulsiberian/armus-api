package io.github.paulsiberian.armus.extension;

import java.io.File;
import java.net.URL;

public interface IExtensionManager {
    void add(File file);
    void remove(File file);
    void enable(File file);
    void disable(File file);
    void download(URL url);
}
