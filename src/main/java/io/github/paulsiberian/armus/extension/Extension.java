package io.github.paulsiberian.armus.extension;

import java.util.Properties;

public abstract class Extension implements IExtension {
    private Properties properties;

    public Extension() {
    }

    @Override
    public boolean init() {
        try {
            start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setProperties(Properties props) {
        properties = props;
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
