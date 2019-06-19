package io.github.paulsiberian.armus.utils;

import io.github.paulsiberian.armus.extension.IExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class ExtensionUtilTest {
    private final File root = new File("/home/paulsiberian/Development/Armus_Dev/test_app_dir/");

    @AfterEach
    void tearDown() {
        System.out.println(ExtensionUtil.getInstance().getExtensions().size());
        ExtensionUtil.getInstance().getExtensions().forEach(IExtension::init);
    }

    @Test
    void load() {
        try {
            ExtensionUtil.getInstance().load(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}