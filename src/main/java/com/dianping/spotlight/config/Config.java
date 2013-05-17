package com.dianping.spotlight.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class Config {
    public static String[] sources;
    public static String searchUrl;
    static {
        Properties p = new Properties();
        try {
            p.load(Config.class.getResourceAsStream("/app-config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String sourcesString = p.getProperty("sources");
        sources = StringUtils.split(sourcesString, ',');
        searchUrl = p.getProperty("searchUrl");
    }

    public Config() {
    }

    public static String[] getSources() {
        return sources;
    }

}
