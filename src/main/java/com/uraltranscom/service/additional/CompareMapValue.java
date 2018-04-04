package com.uraltranscom.service.additional;

import java.util.List;

/**
 *
 * Класс сортировки
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 06.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
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
            final int diff = distance.intValue() - ((CompareMapValue) o).distance.intValue();
            return diff < 0 ? -1 : (diff > 0 ? 1 : 0);
        } else {
            return 0;
        }
    }
}
