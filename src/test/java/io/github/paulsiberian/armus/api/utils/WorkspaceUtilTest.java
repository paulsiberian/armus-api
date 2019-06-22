/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import io.github.paulsiberian.armus.api.api.utils.WorkspaceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class WorkspaceUtilTest {
    private File srcDir;
    private File destDir;
    private File srcFile;
    private File destFile;

    @BeforeEach
    void setUp() {
        srcDir = new File("/home/paulsiberian/Development/Armus_Dev/test-armus-extension");
        destDir = new File("/home/paulsiberian/Development/test-armus-extension");
        srcFile = new File("/home/paulsiberian/Development/Armus_Dev/test-armus-extension/pom.xml");
        destFile = new File("/home/paulsiberian/Development/test-armus-extension/pom.xml");
    }

    @Test
    void cpDir() {
        try {
            WorkspaceUtil.cp(srcDir, destDir);
        } catch (IOException e) {
            System.out.println("Провал!!!");
            e.printStackTrace();
        }
    }

    @Test
    void cpFile() {
        try {
            WorkspaceUtil.cp(srcFile, destFile);
        } catch (IOException e) {
            System.out.println("Провал!!!");
            e.printStackTrace();
        }
    }
}