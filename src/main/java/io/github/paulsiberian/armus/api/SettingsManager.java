/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api;

import com.github.rjeschke.txtmark.Processor;
import io.github.paulsiberian.armus.api.utils.OSUtil;
import io.github.paulsiberian.armus.api.utils.WorkspaceUtil;
import io.github.paulsiberian.armus.api.workspace.WorkspaceException;
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

    private Properties workspace;
    private Properties window;
    private File appDir;
    private File workspaceFile;
    private File windowFile;
    private Property<Locale> locale;
    private String apiInfo;

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        return ourInstance;
    }

    private void createDefault(File file) throws IOException {
        if (file.getName().equals(WORKSPACE_SETTINGS_FILE_NAME)) {
            workspace.setProperty(WORKSPACE_PATH, file.getPath() + File.separator + "Workspace");
            write(workspace, file);
        }
        if (file.getName().equals(WINDOW_SETTINGS_FILE_NAME)) {
            window.setProperty(WINDOW_WIDTH, "600");
            window.setProperty(WINDOW_HEIGHT, "400");
            window.setProperty(WINDOW_MAXIMIZED, "false");
            write(window, file);
        }
    }

    private void readOrCreate(Properties properties, File file) {
        if (!file.exists()) {
            try {
                createDefault(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                read(properties, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(Properties properties, File file) throws IOException {
        properties.store(WorkspaceUtil.mkFile(file), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    private void read(Properties properties, File file) throws IOException {
        properties.load(new FileInputStream(file));
    }

    private String appDataDir() {
        if (OSUtil.isMac()) {
            return  "Library";
        }
        if (OSUtil.isNix()) {
            return  ".local" + File.separator + "share";
        }
        if (OSUtil.isWindows()) {
            return  "AppData" + File.separator + "Roaming";
        }
        return "Applications";
    }

    public final void init(final Class c) {
        locale = new SimpleObjectProperty<>(Locale.getDefault());
        workspace = new Properties();
        window = new Properties();
        var userHomeDir = System.getProperty("user.home");
        var packageName = c.getPackageName();
        appDir = new File(userHomeDir + File.separator + appDataDir() + File.separator + packageName);
        if (!appDir.exists()) {
            try {
                WorkspaceUtil.mkDir(appDir);
            } catch (WorkspaceException e) {
                e.printStackTrace();
            }
        }
        workspaceFile = new File(appDir.getPath() + File.separator + WORKSPACE_SETTINGS_FILE_NAME);
        readOrCreate(workspace, workspaceFile);
        windowFile = new File(appDir.getPath() + File.separator + WINDOW_SETTINGS_FILE_NAME);
        readOrCreate(window, windowFile);
        try {
            apiInfo = Processor.process(SettingsManager.class.getResourceAsStream("info.md"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void save() {
        try {
            write(workspace, workspaceFile);
            write(window, windowFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final String getWorkspaceProperty(String key) {
        return workspace.getProperty(key);
    }

    public final void setWorkspaceProperty(String key, String value) {
        workspace.setProperty(key, value);
    }

    public final String getWindowProperty(String key) {
        return window.getProperty(key);
    }

    public final void setWindowProperty(String key, String value) {
        window.setProperty(key, value);
    }

    public final File getAppDir() {
        return appDir;
    }

    public final Locale getLocale() {
        return locale.getValue();
    }

    public final Property<Locale> localeProperty() {
        return locale;
    }

    public final void setLocale(Locale locale) {
        this.locale.setValue(locale);
    }

    public String getApiInfo() {
        return apiInfo;
    }

    /* Properties keys */
    public static final String WORKSPACE_PATH = WORKSPACE + ".path";
    public static final String WINDOW_WIDTH = WINDOW + ".width";
    public static final String WINDOW_HEIGHT = WINDOW + ".height";
    public static final String WINDOW_MAXIMIZED = WINDOW + ".maximized";
}
