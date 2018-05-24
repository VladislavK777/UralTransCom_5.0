package com.uraltranscom.service;

/**
 *
 * Интерфейс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public interface GetFullMonthCircleOfWagon {
    int fullDays(String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute);
}

