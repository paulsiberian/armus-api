/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ResourceBundle;

public class GUIManager {
    private static GUIManager ourInstance = new GUIManager();
    private Class appClass;
    private Scene scene;

    public static GUIManager getInstance() {
        return ourInstance;
    }

    private GUIManager() {
    }

    public final void init(final Class appClass, final Scene scene) {
        this.appClass = appClass;
        this.scene = scene;
    }

    public final Scene getScene() {
        return scene;
    }

    public final Parent loadFXML(String fxml) throws IOException {
        return loadFXML(fxml, appClass);
    }

    public final Parent loadFXML(String fxml, Class c) throws IOException {
        var bundle = ResourceBundle.getBundle(c.getPackageName() + '.' + fxml, SettingsManager.getInstance().getLocale());
        var fxmlLoader = new FXMLLoader(c.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }

    public final void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public final void setRoot(String fxml, Class c) throws IOException {
        scene.setRoot(loadFXML(fxml, c));
    }
}
