package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.service.additional.PrepareDistanceOfDay;
import com.uraltranscom.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase implements GetFullMonthCircleOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);
    @Autowired
    private PropertyUtil propertyUtil;

    private GetFullMonthCircleOfWagonImpl() {
    }

    @Override
    public int fullDays(String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {

        int fullMonthCircle = 0;
        int loadingOfWagonKR = Integer.parseInt(propertyUtil.getProperty("loadingwagonkr"));
        int loadingOfWagonPV = Integer.parseInt(propertyUtil.getProperty("loadingwagonpv"));
        int unloadingOfWagon = Integer.parseInt(propertyUtil.getProperty("unloadingwagon"));

        fullMonthCircle += Math.ceil(distanceOfEmpty / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty));
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += loadingOfWagonKR;
        } else {
            fullMonthCircle += loadingOfWagonPV;
        }
        fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute)));
        fullMonthCircle += unloadingOfWagon;

        return fullMonthCircle;
    }
}
