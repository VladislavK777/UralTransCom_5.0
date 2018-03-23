package com.uraltranscom.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Класс Вагон
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 17.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

public class Wagon {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Wagon.class);

    private String numberOfWagon; // Номер вагона
    private String typeOfWagon; // Тип вагона
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Название станции назначения

    public Wagon(String numberOfWagon, String typeOfWagon, String keyOfStationDestination, String nameOfStationDestination) {
        this.numberOfWagon = numberOfWagon;
        this.typeOfWagon = typeOfWagon;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
    }

    public String getNumberOfWagon() {
        return numberOfWagon;
    }

    public void setNumberOfWagon(String numberOfWagon) {
        this.numberOfWagon = numberOfWagon;
    }

    public String getTypeOfWagon() {
        return typeOfWagon;
    }

    public void setTypeOfWagon(String typeOfWagon) {
        this.typeOfWagon = typeOfWagon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wagon wagon = (Wagon) o;

        if (numberOfWagon != null ? !numberOfWagon.equals(wagon.numberOfWagon) : wagon.numberOfWagon != null)
            return false;
        if (typeOfWagon != null ? !typeOfWagon.equals(wagon.typeOfWagon) : wagon.typeOfWagon != null) return false;
        if (keyOfStationDestination != null ? !keyOfStationDestination.equals(wagon.keyOfStationDestination) : wagon.keyOfStationDestination != null)
            return false;
        return nameOfStationDestination != null ? nameOfStationDestination.equals(wagon.nameOfStationDestination) : wagon.nameOfStationDestination == null;
    }

    @Override
    public int hashCode() {
        int result = numberOfWagon != null ? numberOfWagon.hashCode() : 0;
        result = 31 * result + (typeOfWagon != null ? typeOfWagon.hashCode() : 0);
        result = 31 * result + (keyOfStationDestination != null ? keyOfStationDestination.hashCode() : 0);
        result = 31 * result + (nameOfStationDestination != null ? nameOfStationDestination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  numberOfWagon +
                ", " + typeOfWagon +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination;
    }
}
