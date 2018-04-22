package com.uraltranscom.model;

import com.uraltranscom.model.additional_model.WagonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс Вагон
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 17.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 *
 */

public class Wagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Wagon.class);

    private String numberOfWagon; // Номер вагона
    private WagonType wagonType; // Тип вагона
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Название станции назначения
    private int volume; // Объем вагона

    public Wagon(String numberOfWagon, WagonType wagonType, String keyOfStationDestination, String nameOfStationDestination, int volume) {
        this.numberOfWagon = numberOfWagon;
        this.wagonType = wagonType;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.volume = volume;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
    }

    public String getKeyOfStationDestination() {
        return keyOfStationDestination;
    }

    public void setKeyOfStationDestination(String keyOfStationDestination) {
        this.keyOfStationDestination = keyOfStationDestination;
    }

    public String getNameOfStationDestination() {
        return nameOfStationDestination;
    }

    public void setNameOfStationDestination(String nameOfStationDestination) {
        this.nameOfStationDestination = nameOfStationDestination;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wagon wagon = (Wagon) o;
        return volume == wagon.volume &&
                Objects.equals(numberOfWagon, wagon.numberOfWagon) &&
                Objects.equals(wagonType, wagon.wagonType) &&
                Objects.equals(keyOfStationDestination, wagon.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, wagon.nameOfStationDestination);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, wagonType, keyOfStationDestination, nameOfStationDestination, volume);
    }

    @Override
    public String toString() {
        return  numberOfWagon +
                ", " + wagonType.toString() +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination +
                ", " + volume;
    }
}
