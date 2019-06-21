/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus;

import io.github.paulsiberian.armus.utils.GUIUtil;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.io.IOException;

public class GUIManager {
    private static GUIManager ourInstance = new GUIManager();
    private Class appClass;
    private Stage window;

    private GUIManager() {
    }

    public static GUIManager getInstance() {
        return ourInstance;
    }

    private BorderPane getRoot() {
        return (BorderPane) getWindow().getScene().getRoot();
    }

    private HBox getStatusBar() {
        return (HBox) getRoot().getBottom();
    }

    public final void init(final Class appClass, final Stage window) {
        this.appClass = appClass;
        this.window = window;
        this.window.setOnCloseRequest(windowEvent -> Platform.exit());
        Scene scene = null;
        try {
            scene = new Scene(GUIUtil.loadFXML("window", appClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (scene != null) {
            this.window.setMinWidth(scene.getRoot().minWidth(0));
            this.window.setMinHeight(scene.getRoot().minHeight(0));
            this.window.setScene(scene);
        }
    }

    public final <T> T loadFXML(String fxml) throws IOException {
        return GUIUtil.loadFXML(fxml, appClass);
    }

    public final Stage getWindow() {
        return window;
    }

    public final MenuBar getMenuBar() {
        return (MenuBar) getRoot().getTop();
    }

    public final StatusBar getStatusBarLeft() {
        return (StatusBar) getStatusBar().getChildren().get(0);
    }

    public final void setStatusBarLeft(StatusBar node) {
        getStatusBar().getChildren().set(0, node);
    }

    public final StatusBar getStatusBarCenter() {
        return (StatusBar) getStatusBar().getChildren().get(1);
    }

    public final void setStatusBarCenter(StatusBar node) {
        getStatusBar().getChildren().set(1, node);
    }

    public final StatusBar getStatusBarRight() {
        return (StatusBar) getStatusBar().getChildren().get(2);
    }

    public final void setStatusBarRight(StatusBar node) {
        getStatusBar().getChildren().set(2, node);
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
