/*
 * Copyright (c) Храпунов П. Н., 2019.
 */

package io.github.paulsiberian.armus.api.utils;

import com.github.rjeschke.txtmark.Processor;
import io.github.paulsiberian.armus.api.SettingsManager;

import java.io.IOException;

public class MarkdownUtil {

    private static final String MD = ".md";

    public static String loadMD(String md, Class c) throws IOException {
        var locale = SettingsManager.getInstance().getLocale();
        var localeMD = md + '_' + locale.getLanguage() + MD;
        var stream = c.getResourceAsStream(localeMD);
        if (stream == null) {
            localeMD = md + '_' + locale.getLanguage() + '_' + locale.getCountry() + MD;
            stream = c.getResourceAsStream(localeMD);
        }
        if (stream == null) {
            localeMD = md + MD;
            stream = c.getResourceAsStream(localeMD);
        }
        return Processor.process(stream);
    }

}
