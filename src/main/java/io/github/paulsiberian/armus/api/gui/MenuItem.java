package io.github.paulsiberian.armus.api.gui;

import java.util.EventListener;

public class MenuItem implements IMenuItem {

    private String name;
    private EventListener onAction;

    public EventListener getOnAction() {
        return onAction;
    }

    public void setOnAction(EventListener onAction) {
        this.onAction = onAction;
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
