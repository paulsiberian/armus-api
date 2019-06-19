/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.utils;

import io.github.paulsiberian.armus.extension.IExtension;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

public class ExtensionUtil {
    private static ExtensionUtil ourInstance = new ExtensionUtil();

    private List<IExtension> extensions;
    private File extensionDir;

    public static ExtensionUtil getInstance() {
        return ourInstance;
    }

    private ExtensionUtil() {
    }

    public void load(File appDir) throws IOException {
        extensions = new ArrayList<>();
        extensionDir = new File(appDir.getPath() + File.separator + "extensions");
        if (!extensionDir.exists()) {
            WorkspaceUtil.mkDir(extensionDir);
        }
        var files = extensionDir.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".jar"));
        if (files == null || files.length == 0) {
            for (var file : files) {
                try {
                    var jarURL = file.toURI().toURL();
                    var classLoader = new URLClassLoader(new URL[] {jarURL});
                    var jar = new JarFile(file);
                    var entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        var entrieName = entries.nextElement().getName();
                        if (!entrieName.endsWith(".class")) continue;
                        entrieName = entrieName.replaceAll("/", ".");
                        entrieName = entrieName.replaceAll(".class", "");
                        var c = classLoader.loadClass(entrieName);
                        var interfaces = c.getInterfaces();
                        for (var i : interfaces) {
                            if (i.getName().endsWith(".IExtension")) {
                                var constructor = classLoader.loadClass(c.getName()).getConstructor();
                                extensions.add((IExtension) constructor.newInstance());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<IExtension> getExtensions() {
        return extensions;
    }

    public File getExtensionDir() {
        return extensionDir;
    }
}
