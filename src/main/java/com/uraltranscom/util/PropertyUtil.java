package com.uraltranscom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * Класс для получения application.properties
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 29.05.2018
 *
 * 29.05.2018
 *   1. Версия 5.0
 *
 */

public class PropertyUtil {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private Properties properties = new Properties();

    public PropertyUtil() {
        loadProp();
    }

    private void loadProp() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8")) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            logger.error("Ошибка загрузки файла application.properties: {}", e.getMessage());
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }


}
