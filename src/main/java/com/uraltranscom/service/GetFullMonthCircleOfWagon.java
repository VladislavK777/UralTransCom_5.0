package com.uraltranscom.service;

/**
 *
 * Интерфейс расчета количества дней, затраченных вагоном за один цикл. По вагонам количесво дней суммируется
 *
 * @author Vladislav Klochkov
 * @version 3.0
 * @create 08.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 *
 */

public interface GetFullMonthCircleOfWagon {
    void fullDays(String numberOfWagon, String typeOfWagon, Integer distanceOfEmpty, String distanceOfRoute);
}

