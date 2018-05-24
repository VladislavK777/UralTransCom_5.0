package com.uraltranscom.service.additional;

import java.util.List;

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
    public List<Object> wagon;
    public Integer distance;

    public CompareMapValue(List<Object> wagon, Integer distance) {
        this.wagon = wagon;
        this.distance = distance;
    }

    public int compareTo(Object o) {
        if (o instanceof CompareMapValue) {
            final int diff = distance - ((CompareMapValue) o).distance;
            return Integer.compare(diff, 0);
        } else {
            return 0;
        }
    }
}
