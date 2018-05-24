package com.uraltranscom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * Зачитывает свойства из db-common.properties в Properties
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 03.04.2018
 *
 * 03.04.2018
 *   1. Версия 4.1
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public class CommonPropertiesAccessor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(CommonPropertiesAccessor.class);

    private static final String COMMON_PROPERTY_FILE = "db-common.properties";

    public Properties getCommonProperties() {
        Properties commonProperties = new Properties();
        URL url = this.getClass().getClassLoader().getResource(COMMON_PROPERTY_FILE);
        if (url == null)
            logger.error("Cannot found db-common.properties");
        else
            try (InputStream in = url.openStream()) {
                commonProperties.load(in);
            } catch (IOException e) {
                logger.error("Cannot load db-common.properties", e);
            }
        return commonProperties;
    }
}
