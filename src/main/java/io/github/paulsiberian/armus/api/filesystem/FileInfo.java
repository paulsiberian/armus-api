/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.filesystem;

import io.github.paulsiberian.armus.api.utils.FileUtil;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Класс объекта, содержащих отображаемую информацию о файлах и директориях и сами файлы и директории
 */
public class FileInfo implements IFileItem {
    /** Иконка файла или директории */
    private Property<FontIcon> icon;
    /** Имя файла или директории */
    private Property<String> name;
    /** Размер файла или директории */
    private Property<String> size;
    /** Дата создания файла или директории */
    private Property<String> creationDate;
    /** Дата последнего изменения файла или директории */
    private Property<String> lastModifiedDate;
    /** Файл или директория */
    private File file;

    /**
     * Конструктор
     * @param file - файл или директория
     */
    public FileInfo(File file) {
        this.file = file;
        try {
            var attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            this.icon = new SimpleObjectProperty<>(FileUtil.getIcon(file, 24));
            this.name = new SimpleStringProperty(file.getName());
            this.size = new SimpleStringProperty(FileUtil.getSize(file));
            this.creationDate = new SimpleStringProperty(FileUtil.getDate(attributes.creationTime()));
            this.lastModifiedDate = new SimpleStringProperty(FileUtil.getDate(attributes.lastModifiedTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает файл или директорию
     *
     * @return файл или директория
     */
    @Override
    public File getFile() {
        return file;
    }

    /**
     * Метод возвращает значение иконки файла или директории
     * @return иконка
     */
    public FontIcon getIcon() {
        return icon.getValue();
    }

    /**
     * Отображаемая иконка файла или директории
     * @see #getIcon()
     * @see #setIcon(FontIcon)
     */
    public Property<FontIcon> iconProperty() {
        return icon;
    }

    /**
     * Метод устанавлевает значение иконки файла или директории
     * @param icon - иконка
     */
    public void setIcon(FontIcon icon) {
        this.icon.setValue(icon);
    }

    /**
     * Метод возвращает значение имени файла или фиректории
     * @return имя
     */
    public String getName() {
        return name.getValue();
    }

    /**
     * Отображаемое имя файла или директории
     * @see #getName()
     * @see #setName(String)
     */
    public Property<String> nameProperty() {
        return name;
    }

    /**
     * Метод устанавливающий значение имени файла или директории
     * @param name - имя
     */
    public void setName(String name) {
        this.name.setValue(name);
    }

    /**
     * Метод возвращает значение размера файла или директории
     * @return размер
     */
    public String getSize() {
        return size.getValue();
    }

    /**
     * Отображаемый размер файла или директории
     * @see #getSize()
     * @see #setSize(String)
     */
    public Property<String> sizeProperty() {
        return size;
    }

    /**
     * Метод устанавливает значение размера файла или директории
     * @param size - размер
     */
    public void setSize(String size) {
        this.size.setValue(size);
    }

    /**
     * Метод возвращает значение даты создания файла или директории
     * @return дата создания
     */
    public String getCreationDate() {
        return creationDate.getValue();
    }

    /**
     * Отображаемая дата создания файла или директории
     * @see #getCreationDate()
     * @see #setCreationDate(String)
     */
    public Property<String> creationDateProperty() {
        return creationDate;
    }

    /**
     * Метод устанавливает значение даты создания файла или директории
     * @param creationDate - дата создания
     */
    public void setCreationDate(String creationDate) {
        this.creationDate.setValue(creationDate);
    }

    /**
     * Метод возвращает значение даты последнего изменения файла или директории
     * @return дата последнего изменения
     */
    public String getLastModifiedDate() {
        return lastModifiedDate.getValue();
    }

    /**
     * Отображаемая дата последнего изменения файла или директории
     * @see #getLastModifiedDate()
     * @see #setLastModifiedDate(String)
     */
    public Property<String> lastModifiedDateProperty() {
        return lastModifiedDate;
    }

    /**
     * Метод устанавливает значение даты последнего изменения файла или директории
     * @param lastModifiedDate - дата последнего изменения
     */
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate.setValue(lastModifiedDate);
    }
}
