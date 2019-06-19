package io.github.paulsiberian.armus.extension;

import io.github.paulsiberian.armus.utils.ExtensionUtil;
import io.github.paulsiberian.armus.utils.WorkspaceUtil;
import io.github.paulsiberian.armus.workspace.WorkspaceException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipFile;

public class ExtensionLoader<T extends IExtension> {
    private Set<T> extensions;
    private Set<Class<?>> classes;
    private File root;

    public ExtensionLoader(File root) {
        this.root = root;
        extensions = new HashSet<>();
        classes = new HashSet<>();
    }

    public void load() {
        if (root.exists() && root.isDirectory()) {
            var jars = root.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(".jar"));
            if (jars != null) {
                for (var jar : jars) {
                    String mainClass = null;
                    try {
                        var zipJar = new ZipFile(jar);
                        var stream = zipJar.getInputStream(zipJar.getEntry("extension.properties"));
                        var props = new Properties();
                        props.load(stream);
                        mainClass = props.getProperty(ExtensionUtil.MAIN_CLASS);
                        var loader = URLClassLoader.newInstance(
                                new URL[] { jar.toURI().toURL() },
                                getClass().getClassLoader()
                        );
                        classes.add(loader.loadClass(mainClass));

                    } catch (IOException e) {
                        System.out.println("Error while loading module file " + jar.getName());
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class not found! Wrong main defined in extension.properties?: " + jar.getName() + " class: " + mainClass);
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                WorkspaceUtil.mkDir(root);
            } catch (WorkspaceException e) {
                e.printStackTrace();
            }
        }
    }

    public void enableExtensions() {
        classes.forEach(c -> {
            try {
                var extension = (T) c.getConstructor().newInstance();
                if (extension.init()) {
                    System.out.println("Extension loaded.");
                    extensions.add(extension);
                    System.out.println("Loaded extensions: " + extensions.size());
                } else {
                    System.out.println("Extension unloaded");
                }
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


}
