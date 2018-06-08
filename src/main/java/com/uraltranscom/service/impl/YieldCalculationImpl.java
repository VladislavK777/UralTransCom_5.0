package com.uraltranscom.service.impl;

import com.uraltranscom.service.YieldCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Класс расчета доходности
 * Implementation for {@link YieldCalculation} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 30.05.2018
 *
 * 30.05.2018
 *   1. Версия 5.0
 *
 */

public class YieldCalculationImpl implements YieldCalculation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetFullMonthCircleOfWagonImpl.class);
    @Override
    public double yieldCalculation(double rate1, double tariff1, double rate2, double tariff2, int countDays) {
        double totalYieldOfDay;
        totalYieldOfDay = Math.round(((rate1 - tariff1 + rate2 - tariff2) / countDays) * 100) / 100.00d;
        return totalYieldOfDay;
    }
}
