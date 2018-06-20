package com.uraltranscom.service;

/**
 *
 * Интерфейс расчета доходности
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 30.05.2018
 *
 * 30.05.2018
 *   1. Версия 5.0
 *
 */

public interface YieldCalculation {
    /**
     * @param rate1 - ставка текущего рейса
     * @param tariff1 - тариф порожнего маршрута1
     * @param rate2 - ставка следующего рейса
     * @param tariff2 - тариф порожнего маршрута2
     * @param countDays - число дней, затраченных на весь цикл
     * @return - возвращает ставку суточную
     */

    double yieldCalculation(double rate1, double tariff1, double rate2, double tariff2, int countDays);
}
