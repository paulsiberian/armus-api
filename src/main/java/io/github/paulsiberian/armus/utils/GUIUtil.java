/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.utils;

import io.github.paulsiberian.armus.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class GUIUtil {

    private static ResourceBundle loadBundle(String fxml, Class c) {
        return ResourceBundle.getBundle(c.getPackageName() + '.' + fxml, SettingsManager.getInstance().getLocale());
    }

    private static FXMLLoader fxmlLoader(String fxml, Class c) {
        return new FXMLLoader(c.getResource(fxml + ".fxml"), loadBundle(fxml, c));
    }

    public static <T> T loadFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }

    public static Stage loadStageFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }
}
