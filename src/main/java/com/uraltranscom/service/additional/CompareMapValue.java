package com.uraltranscom.service.additional;

import com.uraltranscom.model.Route;
import com.uraltranscom.model_ext.TotalCalculateRoute;

import java.util.Map;

/**
 *
 * Класс сортировки
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 06.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public class CompareMapValue implements Comparable {
    public Map<Route, TotalCalculateRoute> route;
    public double yield;

    public CompareMapValue(Map<Route, TotalCalculateRoute> route, double yield) {
        this.route = route;
        this.yield = yield;
    }

    public int compareTo(Object o) {
        if (o instanceof CompareMapValue) {
            final double diff = yield - ((CompareMapValue) o).yield;
            return Double.compare(0, diff);
        } else {
            return 0;
        }
    }
}
