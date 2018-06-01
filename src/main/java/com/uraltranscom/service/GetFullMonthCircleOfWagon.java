package com.uraltranscom.service;

/**
 *
 * Интерфейс расчета количества дней, затраченных вагоном за один цикл.
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

    /**
     * Расчет оборото-дней для получения доходности
     * @param typeOfWagon - тип вагона
     * @param distanceOfRoute1 - расстояние текущего рейса вагона
     * @param distanceOfEmpty1 - порожнее расстоняние до станции погрузки
     * @param distanceOfRoute2 - расстоние второго рейса
     * @param distanceOfEmpty2 - порожнее расстояние до опорной станции погрузки
     */
    int fullDaysForYield(String typeOfWagon, String distanceOfRoute1, Integer distanceOfEmpty1, String distanceOfRoute2, Integer distanceOfEmpty2);
}

