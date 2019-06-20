/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class GUIManager {
    private static GUIManager ourInstance = new GUIManager();
    private Class appClass;
    private Scene scene;
    private Stage window;

    private GUIManager() {
    }

    private ResourceBundle loadBundle(String fxml, Class c) {
        return ResourceBundle.getBundle(c.getPackageName() + '.' + fxml, SettingsManager.getInstance().getLocale());
    }

    private FXMLLoader fxmlLoader(String fxml, Class c) {
        return new FXMLLoader(c.getResource(fxml + ".fxml"), loadBundle(fxml, c));
    }

    private BorderPane getRoot() {
        return (BorderPane) getScene().getRoot();
    }

    public static GUIManager getInstance() {
        return ourInstance;
    }

    public final void init(final Class appClass, final Scene scene) {
        this.appClass = appClass;
        this.scene = scene;
    }

    public final void init(final Class appClass, final Stage stage) {
        this.appClass = appClass;
        this.window = stage;
    }

    public final Parent loadFXML(String fxml) throws IOException {
        return loadFXML(fxml, appClass);
    }

    public final Parent loadFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }

    public final Stage loadStageFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }

    public final void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public final void setRoot(String fxml, Class c) throws IOException {
        scene.setRoot(loadFXML(fxml, c));
    }

    public final Scene getScene() {
        if (scene == null && window != null) {
            return window.getScene();
        }
        return scene;
    }

    public final Stage getWindow() {
        if (window == null && scene != null) {
            return (Stage) scene.getWindow();
        }
        return window;
    }

    public final MenuBar getMenuBar() {
        return (MenuBar) getRoot().getTop();
    }

    public final HBox getStatusBar() {
        return (HBox) getRoot().getBottom();
    }

    public final Node getContentCenter() {
        return getRoot().getCenter();
    }

    public final void setContentCenter(Node node) {
        getRoot().setCenter(node);
    }

    public final Node getContentLeft() {
        return getRoot().getLeft();
    }

    public final void setContentLeft(Node node) {
        getRoot().setLeft(node);
    }

    public final Node getContentRight() {
        return getRoot().getRight();
    }

    public final void setContentRight(Node node) {
        getRoot().setRight(node);
    }
}
