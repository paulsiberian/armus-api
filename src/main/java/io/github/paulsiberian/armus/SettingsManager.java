/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus;

import io.github.paulsiberian.armus.utils.OSUtil;
import io.github.paulsiberian.armus.utils.WorkspaceUtil;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

public class SettingsManager {
    private static final String EXT = ".properties";
    private static final String WINDOW = "window";
    private static final String WORKSPACE = "workspace";
    private static final String WORKSPACE_SETTINGS_FILE_NAME = WORKSPACE + EXT;
    private static final String WINDOW_SETTINGS_FILE_NAME = WINDOW + EXT;

    private static final SettingsManager ourInstance = new SettingsManager();

    public static final String WORKSPACE_PATH = WORKSPACE + ".path";
    public static final String WINDOW_WIDTH = WINDOW + ".width";
    public static final String WINDOW_HEIGHT = WINDOW + ".height";
    public static final String WINDOW_MAXIMIZED = WINDOW + ".maximized";

    private Properties workspaceProps;
    private Properties windowProps;
    private File appDir;
    private Property<Locale> locale;

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        return ourInstance;
    }

    private void createDefaultSettings(File dir) throws IOException {
        workspaceProps.setProperty(WORKSPACE_PATH, dir.getPath() + File.separator + "Workspace");
        windowProps.setProperty(WINDOW_WIDTH, "600");
        windowProps.setProperty(WINDOW_HEIGHT, "400");
        windowProps.setProperty(WINDOW_MAXIMIZED, "false");
        writeSettings(workspaceProps, WORKSPACE_SETTINGS_FILE_NAME, dir);
        writeSettings(windowProps, WINDOW_SETTINGS_FILE_NAME, dir);
    }

    private void writeSettings(Properties props, String name, File dir) throws IOException {
        props.store(WorkspaceUtil.mkFile(name, dir), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    private void readSettings(Properties props, File file) throws IOException {
        props.load(new FileInputStream(file));
    }

    private String appDataDir() {
        if (OSUtil.isMac()) {
            return  "Library";
        }
        if (OSUtil.isNix()) {
            return  ".config";
        }
        if (OSUtil.isWindows()) {
            return  "AppData" + File.separator + "Roaming";
        }
        return "Applications";
    }

    public void init(Class c) throws IOException {
        locale = new SimpleObjectProperty<>(Locale.getDefault());
        workspaceProps = new Properties();
        windowProps = new Properties();
        var userHomeDir = System.getProperty("user.home");
        var packageName = c.getPackageName();
        appDir = new File(userHomeDir + File.separator + appDataDir() + File.separator + packageName);
        if (!appDir.exists()) {
            WorkspaceUtil.mkDir(appDir);
        }
        var workspaceFile = new File(appDir.getPath() + WORKSPACE_SETTINGS_FILE_NAME);
        if (!workspaceFile.exists()) {
            createDefaultSettings(appDir);
        } else {
            readSettings(workspaceProps, workspaceFile);
        }
        var windowFile = new File(appDir.getPath() + WINDOW_SETTINGS_FILE_NAME);
        if (!windowFile.exists()) {
            createDefaultSettings(appDir);
        } else {
            readSettings(windowProps, windowFile);
        }
    }

    public String getWorkspaceProperty(String key) {
        return workspaceProps.getProperty(key);
    }

    public String getWindowProperty(String key) {
        return windowProps.getProperty(key);
    }

    public File getAppDir() {
        return appDir;
    }

    public Locale getLocale() {
        return locale.getValue();
    }

    public Property<Locale> localeProperty() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale.setValue(locale);
    }
}
