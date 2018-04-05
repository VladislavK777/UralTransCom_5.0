package com.uraltranscom.model_ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс для формирвоания итоговой информации для вагона
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 04.04.2018
 *
 * 04.04.2018
 *   1. Версия 4.1
 *
 */

public class WagonFinalInfo {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WagonFinalInfo.class);

    private String numberOfWagon; // Номер вагона
    private int countCircleDays; // Количество дней в пути
    private int distanceEmpty; // Расстояние порожнее

    public WagonFinalInfo(String numberOfWagon, int countCircleDays, int distanceEmpty) {
        this.numberOfWagon = numberOfWagon;
        this.countCircleDays = countCircleDays;
        this.distanceEmpty = distanceEmpty;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public int getCountCircleDays() {
        return countCircleDays;
    }

    public void setCountCircleDays(int countCircleDays) {
        this.countCircleDays = countCircleDays;
    }

    public int getDistanceEmpty() {
        return distanceEmpty;
    }

    public void setDistanceEmpty(int distanceEmpty) {
        this.distanceEmpty = distanceEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WagonFinalInfo that = (WagonFinalInfo) o;
        return countCircleDays == that.countCircleDays &&
                distanceEmpty == that.distanceEmpty &&
                Objects.equals(numberOfWagon, that.numberOfWagon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, countCircleDays, distanceEmpty);
    }

    @Override
    public String toString() {
        return  numberOfWagon +
                ", " + countCircleDays +
                ", " + distanceEmpty;
    }
}
