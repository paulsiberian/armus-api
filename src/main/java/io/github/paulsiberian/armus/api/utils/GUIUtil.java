/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import io.github.paulsiberian.armus.api.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Класс состоит из статичных методов для работы с FXML-файлами
 */
public class GUIUtil {

    /**
     * Метод получения пакета ресурсов, соответствующего названию FXML-файла
     * @param fxml - имя FXML-файла (без расширения)
     * @param c - класс
     * @return пакет ресурсов
     */
    public static ResourceBundle loadBundle(String fxml, Class c) {
        return ResourceBundle.getBundle(c.getPackageName() + '.' + fxml, SettingsManager.getInstance().getLocale());
    }

    /**
     * Метод получения загрузчика FXML-файла
     * @param fxml - имя FXML-файла (без расширения)
     * @param c - класс
     * @return загрузчик FXML-файла
     */
    public static FXMLLoader fxmlLoader(String fxml, Class c) {
        return new FXMLLoader(c.getResource(fxml + ".fxml"), loadBundle(fxml, c));
    }

    /**
     * Метод получения корня FXML-файла
     * @param fxml - имя FXML-файла (без расширения)
     * @param c - класс
     * @param <T> - тип корня FXML-файла
     * @return корень FXML-файла
     * @throws IOException если произошла ошибка загрузки FXML-файла
     */
    public static <T> T loadFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }

    /**
     * Метод получения окна из FXML-файла
     * @param fxml - имя FXML-файла (без расширения)
     * @param c - класс
     * @return окно
     * @throws IOException если произошла ошибка загрузки FXML-файла
     */
    public static Stage loadStageFXML(String fxml, Class c) throws IOException {
        return fxmlLoader(fxml, c).load();
    }
}
