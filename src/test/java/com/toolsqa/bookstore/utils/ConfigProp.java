package com.toolsqa.bookstore.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProp {
    private static final String PATH_PROP = "/src/test/resources/config.properties";
    private static final String PATH_FULL = System.getProperty("user.dir") + PATH_PROP;
    private static final Properties properties = new Properties();

    private static Properties loadProp() {
        try {
            properties.load(new FileInputStream(PATH_FULL));
        } catch (IOException e) {
            throw new RuntimeException("Arquivo 'config.properties' não encontrado em 'src/test/java/resources/'" + e);
        }
        return properties;
    }

    public static String getProp(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = loadProp().getProperty(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Variável obrigatória não definida: " + key + ". Por favor, defina " +
                    "a variável de ambiente ou adicione-a ao arquivo 'secrets.properties'.");
        }
        return value;
    }
}

