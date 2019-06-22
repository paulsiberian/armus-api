/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class ExtensionLoaderTest {
    private final File root = new File("/home/paulsiberian/Development/Armus_Dev/test_app_dir/");
    private ExtensionLoader loader;

    @BeforeEach
    void setUp() {
        loader = new ExtensionLoader(new File(root.getPath() + File.separator + "extensions"));
    }

    @Test
    void load() {
        loader.load();
        loader.enableExtensions();
    }
}