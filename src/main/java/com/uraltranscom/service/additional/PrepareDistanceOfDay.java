package com.uraltranscom.service.additional;

import com.uraltranscom.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Класс расчета суточного пробега в зависимости от расстонояния
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 29.05.2018
 *
 * 29.05.2018
 *   1. Версия 5.0
 *
 */

public class PrepareDistanceOfDay {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(PrepareDistanceOfDay.class);
    private static float distanceOfDay;

    public static Float getDistanceOfDay(int distance) {
        PropertyUtil propertyUtil = new PropertyUtil();
        String stringDistanceOfDay = propertyUtil.getProperty("distanceday");
        String paramSplit [] = stringDistanceOfDay.split(";");
        for (String s : paramSplit) {
            String paramDistSplit [] = s.split("_");
            if (Integer.parseInt(paramDistSplit[0]) <= distance && distance <= Integer.parseInt(paramDistSplit[1])) {
                distanceOfDay = Float.parseFloat(paramDistSplit[2]) * 1f;
            }
        }
        return distanceOfDay;
    }

}
