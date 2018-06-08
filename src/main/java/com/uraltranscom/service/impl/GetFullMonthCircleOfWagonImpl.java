package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.service.additional.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл.
 * Implementation for {@link GetFullMonthCircleOfWagon} interface
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

    private GetFullMonthCircleOfWagonImpl() {
    }

    @Override
    public int fullDaysForYield(String typeOfWagon, String distanceOfRoute1, Integer distanceOfEmpty1, String distanceOfRoute2, Integer distanceOfEmpty2) {

        int fullMonthCircle = 0;

        // В зависимости от типа вагона, прибавляем количество дней первой погрузки, на станции отправления текущего рейса(настраивается в application.properties)
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += FIRST_LOADING_WAGON_KR;
        } else {
            fullMonthCircle += LOADING_WAGON_PV;
        }
        // Расчитываем количество дней текущего рейса
        fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute1) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute1)));
        // Прибавляем количество дней выгрузки
        fullMonthCircle += UNLOADING_WAGON;
        // Прибавляем количество дней порожнего расстояния до станции отпраления следующего рейса
        fullMonthCircle += Math.ceil(distanceOfEmpty1 / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty1));


        // Прибавляем второй цикл
        // В зависимости от типа вагона, прибавляем количество дней второй погрузки(настраивается в application.properties)
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += SECOND_LOADING_WAGON_KR;
        } else {
            fullMonthCircle += LOADING_WAGON_PV;
        }
        // Расчитываем количество дней следующего рейса
        fullMonthCircle += Math.ceil(Integer.parseInt(distanceOfRoute2) / PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceOfRoute2)));
        // Прибавляем количество дней выгрузки
        fullMonthCircle += UNLOADING_WAGON;
        // Прибавляем количество дней порожнего расстояния до опорной станци погрузки
        fullMonthCircle += Math.ceil(distanceOfEmpty2 / PrepareDistanceOfDay.getDistanceOfDay(distanceOfEmpty2));

        return fullMonthCircle;
    }
}
