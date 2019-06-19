package io.github.paulsiberian.armus.extension;

import io.github.paulsiberian.armus.utils.ExtensionUtil;
import io.github.paulsiberian.armus.utils.WorkspaceUtil;
import io.github.paulsiberian.armus.workspace.WorkspaceException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipFile;

public class ExtensionLoader<T extends IExtension> {
    private Set<T> extensions;
    private Map<Class<?>, Properties> classes;
    private File root;

    public ExtensionLoader(File root) {
        this.root = root;
        extensions = new HashSet<>();
        classes = new HashMap<>();
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
                        classes.put(loader.loadClass(mainClass), props);

                    } catch (IOException e) {
                        System.out.println("Ошибка во время загрузки файла " + jar.getName());
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Класс не найден! Возможно он неправильно определён в extension.properties: " + jar.getName() + ' ' + ExtensionUtil.MAIN_CLASS + '=' + mainClass);
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
        classes.forEach((c, p) -> {
            try {
                var extension = (T) c.getConstructor().newInstance();
                if (extension.init()) {
                    System.out.println("Расширение \"" + p.getProperty(ExtensionUtil.NAME) + "\" инициализировано.");
                    extensions.add(extension);
                } else {
                    System.out.println("Расширение \"" + p.getProperty(ExtensionUtil.NAME) + "\" не удалось инициализировать.");
                }
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Загружено расширений: " + extensions.size());
    }

    public Set<T> getExtensions() {
        return extensions;
    }

    public Map<Class<?>, Properties> getClasses() {
        return classes;
    }

    public File getRoot() {
        return root;
    }
}
