package com.uraltranscom.model.additional_model;

/**
 *
 * Класс объем вагона с/до
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 19.04.2018
 *
 * 19.04.2018
 *   1. Версия 4.1
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public class VolumePeriod {
    private int volumeFrom;
    private int volumeTo;

    public VolumePeriod(int volumeFrom, int volumeTo) {
        this.volumeFrom = volumeFrom;
        this.volumeTo = volumeTo;
    }

    public int getVolumeFrom() {
        return volumeFrom;
    }

    public void setVolumeFrom(int volumeFrom) {
        this.volumeFrom = volumeFrom;
    }

    public int getVolumeTo() {
        return volumeTo;
    }

    public void setVolumeTo(int volumeTo) {
        this.volumeTo = volumeTo;
    }

    @Override
    public String toString() {
        return volumeFrom +
                ", " + volumeTo;
    }
}
