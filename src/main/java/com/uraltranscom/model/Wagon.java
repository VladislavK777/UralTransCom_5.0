package com.uraltranscom.model;

import com.uraltranscom.model.additional_model.CargoClass;
import com.uraltranscom.model.additional_model.WagonType;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс Вагон
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 17.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 * 04.05.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public class Wagon extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Wagon.class);

    private String numberOfWagon; // Номер вагона
    private WagonType wagonType; // Тип вагона
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Название станции назначения
    private String roadOfStationDestination; // Дорога станции назначения
    private String nameOfStationDeparture; // Код станции отправления
    private String roadOfStationDeparture; // Дорога станции отправления
    private int volume; // Объем вагона
    private CargoClass cargo; // Груз(код, нименование, нужна ли промывка)
    private Double rate; // Ставка
    private String customer; // Текущий клиент


    //TODO удалить после тестов и добавить реальное расстояние
    private String keyOfStationDep; // Код станции отправления

    public Wagon(String numberOfWagon,
                 String keyOfStationDestination,
                 String nameOfStationDestination,
                 String roadOfStationDestination,
                 String nameOfStationDeparture,
                 String roadOfStationDeparture,
                 int volume,
                 String nameCargo, String keyCargo,
                 Double rate,
                 String customer,
                 String keyOfStationDep) {
        this.numberOfWagon = numberOfWagon;
        this.wagonType = new WagonType(TYPE_OF_WAGON_KR);
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.roadOfStationDestination = roadOfStationDestination;
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.roadOfStationDeparture = roadOfStationDeparture;
        this.volume = volume;
        this.cargo = new CargoClass(nameCargo, keyCargo);
        this.rate = rate;
        this.customer = customer;
        this.keyOfStationDep = keyOfStationDep;
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

    public String getRoadOfStationDestination() {
        return roadOfStationDestination;
    }

    public void setRoadOfStationDestination(String roadOfStationDestination) {
        this.roadOfStationDestination = roadOfStationDestination;
    }

    public String getNameOfStationDeparture() {
        return nameOfStationDeparture;
    }

    public void setNameOfStationDeparture(String nameOfStationDeparture) {
        this.nameOfStationDeparture = nameOfStationDeparture;
    }

    public String getRoadOfStationDeparture() {
        return roadOfStationDeparture;
    }

    public void setRoadOfStationDeparture(String roadOfStationDeparture) {
        this.roadOfStationDeparture = roadOfStationDeparture;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public CargoClass getCargo() {
        return cargo;
    }

    public void setCargo(CargoClass cargo) {
        this.cargo = cargo;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getKeyOfStationDep() {
        return keyOfStationDep;
    }

    public void setKeyOfStationDep(String keyOfStationDep) {
        this.keyOfStationDep = keyOfStationDep;
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
                Objects.equals(nameOfStationDestination, wagon.nameOfStationDestination) &&
                Objects.equals(roadOfStationDestination, wagon.roadOfStationDestination) &&
                Objects.equals(nameOfStationDeparture, wagon.nameOfStationDeparture) &&
                Objects.equals(roadOfStationDeparture, wagon.roadOfStationDeparture) &&
                Objects.equals(cargo, wagon.cargo) &&
                Objects.equals(rate, wagon.rate) &&
                Objects.equals(customer, wagon.customer) &&
                Objects.equals(keyOfStationDep, wagon.keyOfStationDep);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numberOfWagon, wagonType, keyOfStationDestination, nameOfStationDestination, roadOfStationDestination, nameOfStationDeparture, roadOfStationDeparture, volume, cargo, rate, customer, keyOfStationDep);
    }

    @Override
    public String toString() {
        return  numberOfWagon +
                ", " + wagonType.toString() +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination +
                ", " + roadOfStationDestination +
                ", " + nameOfStationDeparture +
                ", " + roadOfStationDeparture +
                ", " + volume +
                ", " + cargo.getNameCargo() +
                ", " + cargo.getKeyCargo() +
                ", " + rate +
                ", " + customer +
                ", " + cargo.isNeedWashingStation();
    }
}
