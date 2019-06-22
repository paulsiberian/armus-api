/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.workspace;

import io.github.paulsiberian.armus.api.utils.WorkspaceUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

class WorkspaceTest {
    private File[] fileArray;
    private Stream<File> fileStream;

    @Test
    void find() {
        var root = new File("/home/paulsiberian/Dev/Armus_Project/test_work_folder");
        var value = "2";
        try {
            fileArray = WorkspaceUtil.findFileArray(value, root);
            fileStream = WorkspaceUtil.findFileStream(value, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        for (var file : fileArray) {
            System.out.println(file.getPath());
        }
        System.out.println("===============================================");
        fileStream.forEach(System.out::println);
    }
}