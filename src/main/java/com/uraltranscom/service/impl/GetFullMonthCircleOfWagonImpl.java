package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetFullMonthCircleOfWagon;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * Класс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

@Service
public class GetFullMonthCircleOfWagonImpl extends JavaHelperBase implements GetFullMonthCircleOfWagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);



    //Map<String, Integer> mapOfDaysOfWagon = new HashMap<>();

    private GetFullMonthCircleOfWagonImpl() {
    }

    /*
     * Метод расчета дней, пройденных вагоном за один цикл
     * По вагонам количесво дней суммируется
     */

    @Override
    public int fullDays(String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute) {
        int fullMonthCircle = 0;

        fullMonthCircle += Math.round(distanceOfEmpty / 300f + 1);
        if (typeOfWagon.equals(TYPE_OF_WAGON_KR)) {
            fullMonthCircle += LOADING_OF_WAGON_KR;
        } else {
            fullMonthCircle += LOADING_OF_WAGON_PV;
        }
        fullMonthCircle += Math.round(Integer.parseInt(distanceOfRoute) / 300f + 1);
        fullMonthCircle += UNLOADING_OF_WAGON;

        return fullMonthCircle;
    }

    /*
    public int getNumberOfDaysOfWagon(String numberOfWagon) {
        return mapOfDaysOfWagon.get(numberOfWagon);
    }

    public void deleteFromMap(String numberOfWagon) {
        Iterator<Map.Entry<String, Integer>> it = mapOfDaysOfWagon.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            if (entry.getKey().equals(numberOfWagon)) {
                it.remove();
            }
        }
    }
    */
}
