/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.api.extension;

public abstract class Extension implements IExtension {
    public Extension() {
    }

    @Override
    public final boolean init() {
        try {
            start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
