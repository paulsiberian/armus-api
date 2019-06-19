/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.utils;

import io.github.paulsiberian.armus.extension.IExtension;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class ExtensionUtil {
    private static ExtensionUtil ourInstance = new ExtensionUtil();

    private List<IExtension> extensions;
    private File root;

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String VERSION = "version";
    public static final String AUTHOR = "author";
    public static final String EMAIL = "email";
    public static final String SITE = "site";

    private ExtensionUtil() {
    }

    private Properties loadProperties(Class c) {
        try (var stream = c.getResourceAsStream("info.properties")) {
            var props = new Properties();
            props.load(stream);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ExtensionUtil getInstance() {
        return ourInstance;
    }

    public void load(File appDir) throws IOException {
        extensions = new ArrayList<>();
        root = new File(appDir.getPath() + File.separator + "extensions");
        if (!root.exists()) {
            WorkspaceUtil.mkDir(root);
        }
        var files = root.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".jar"));
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
                                var cl = classLoader.loadClass(c.getName());
                                var constructor = cl.getConstructor();
                                var main = (IExtension) constructor.newInstance();
                                main.setProperties(loadProperties(cl));
                                extensions.add(main);
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

    public File getRoot() {
        return root;
    }
}
