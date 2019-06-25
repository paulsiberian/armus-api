/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api;

import io.github.paulsiberian.armus.api.utils.GUIUtil;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    private void saveWindowWidth(Number width) {
        SettingsManager.getInstance().setWindowProperty(SettingsManager.WINDOW_WIDTH, String.valueOf(width));
    }

    private void saveWindowHeight(Number height) {
        SettingsManager.getInstance().setWindowProperty(SettingsManager.WINDOW_HEIGHT, String.valueOf(height));
    }

    private void saveWindowMaximized(boolean maximized) {
        SettingsManager.getInstance().setWindowProperty(SettingsManager.WINDOW_MAXIMIZED, String.valueOf(maximized));
    }

    public final void init(final Class appClass) {
        this.appClass = appClass;
        try {
            var settings = SettingsManager.getInstance();
            var curWidth = Double.parseDouble(settings.getWindowProperty(SettingsManager.WINDOW_WIDTH));
            var curHeight = Double.parseDouble(settings.getWindowProperty(SettingsManager.WINDOW_HEIGHT));
            var maximized = Boolean.parseBoolean(settings.getWindowProperty(SettingsManager.WINDOW_MAXIMIZED));
            window = GUIUtil.loadStageFXML("window", appClass);
            window.setMinWidth(600);
            window.setMinHeight(400);
            window.setWidth(curWidth);
            window.setHeight(curHeight);
            window.setMaximized(maximized);
            window.setOnCloseRequest(windowEvent -> {
                settings.save();
                Platform.exit();
            });
            window.widthProperty().addListener((obsValue, oldValue, newValue) -> {
                if (window.isMaximized()) {
                    saveWindowWidth(oldValue);
                } else {
                    saveWindowWidth(newValue);
                }
            });
            window.heightProperty().addListener((obsValue, oldValue, newValue) -> {
                if (window.isMaximized()) {
                    saveWindowHeight(oldValue);
                } else {
                    saveWindowHeight(newValue);
                }
            });
            window.maximizedProperty().addListener((obsValue, oldValue, newValue) -> {
                saveWindowMaximized(newValue);
            });
        } catch (IOException e) {
            System.out.println("Ошибка: не удалось создать окно");
            e.printStackTrace();
        }
    }

    public final <T> T loadFXML(String fxml) throws IOException {
        return GUIUtil.fxmlLoader(fxml, appClass).load();
    }

    public final Stage getWindow() {
        return window;
    }

    public final MenuBar getMenuBar() {
        return (MenuBar) getRoot().getTop();
    }

    public final Node getStatusBarLeft() {
        return getStatusBar().getChildren().get(0);
    }

    public final void setStatusBarLeft(Node node) {
        getStatusBar().getChildren().set(0, node);
    }

    public final Node getStatusBarCenter() {
        return getStatusBar().getChildren().get(1);
    }

    public final void setStatusBarCenter(Node node) {
        getStatusBar().getChildren().set(1, node);
    }

    public final Node getStatusBarRight() {
        return getStatusBar().getChildren().get(2);
    }

    public final void setStatusBarRight(Node node) {
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
