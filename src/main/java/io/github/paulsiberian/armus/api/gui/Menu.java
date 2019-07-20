package io.github.paulsiberian.armus.api.gui;

import java.util.ArrayList;
import java.util.List;

public class Menu implements IMenuItem {

    private String name;
    private List<IMenuItem> children;

    private Menu() {
        children = new ArrayList<>();
    }

    public Menu(String name) {
        this();
        this.name = name;
    }

    public List<IMenuItem> getChildren() {
        return children;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
