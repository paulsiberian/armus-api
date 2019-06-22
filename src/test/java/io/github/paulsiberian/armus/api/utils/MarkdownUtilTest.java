/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import io.github.paulsiberian.armus.api.SettingsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownUtilTest {

    @BeforeEach
    void setUp() {
        SettingsManager.getInstance().init(MarkdownUtilTest.class);
    }

    @Test
    void loadMD() {
        try {
            System.out.println(MarkdownUtil.loadMD("about", MarkdownUtilTest.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}