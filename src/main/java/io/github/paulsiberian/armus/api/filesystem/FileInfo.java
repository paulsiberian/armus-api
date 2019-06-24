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

public class FileInfo implements IFileItem {
    private Property<FontIcon> icon;
    private Property<String> name;
    private Property<String> size;
    private Property<String> creationDate;
    private Property<String> lastModifiedDate;
    private File file;

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

    @Override
    public File getFile() {
        return file;
    }

    public FontIcon getIcon() {
        return icon.getValue();
    }

    public Property<FontIcon> iconProperty() {
        return icon;
    }

    public void setIcon(FontIcon icon) {
        this.icon.setValue(icon);
    }

    public String getName() {
        return name.getValue();
    }

    public Property<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public String getSize() {
        return size.getValue();
    }

    public Property<String> sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.setValue(size);
    }

    public String getCreationDate() {
        return creationDate.getValue();
    }

    public Property<String> creationDateProperty() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.setValue(creationDate);
    }

    public String getLastModifiedDate() {
        return lastModifiedDate.getValue();
    }

    public Property<String> lastModifiedDateProperty() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate.setValue(lastModifiedDate);
    }
}
