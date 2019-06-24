/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import java.io.*;

public class ObjectUtil {

    public static <T> T clone(T object) {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            var objOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objOutputStream.writeObject(object);
            objOutputStream.close();
            var byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            var objInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
