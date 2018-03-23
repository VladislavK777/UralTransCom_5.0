package com.uraltranscom.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Класс Маршрута
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

public class Route implements Comparable<Object> {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Route.class);

    private String keyOfStationDeparture; // Код станции отправления
    private String nameOfStationDeparture; // Станция отправления
    private String keyOfStationDestination; // Код станции назначения
    private String nameOfStationDestination; // Станция назначения
    private String distanceOfWay; // Расстояние маршрута
    private String VIP; // Флаг приоритера 1 - Приоритетный, 0 - Неприоритетный
    private String customer; // Заказчик

    public Route(String keyOfStationDeparture, String nameOfStationDeparture, String keyOfStationDestination, String nameOfStationDestination, String distanceOfWay, String VIP, String customer) {
        this.keyOfStationDeparture = keyOfStationDeparture;
        this.nameOfStationDeparture = nameOfStationDeparture;
        this.keyOfStationDestination = keyOfStationDestination;
        this.nameOfStationDestination = nameOfStationDestination;
        this.distanceOfWay = distanceOfWay;
        this.VIP = VIP;
        this.customer = customer;
    }

    public String getKeyOfStationDeparture() {
        return keyOfStationDeparture;
    }

    public void setKeyOfStationDeparture(String keyOfStationDeparture) {
        this.keyOfStationDeparture = keyOfStationDeparture;
    }

    public String getNameOfStationDeparture() {
        return nameOfStationDeparture;
    }

    public void setNameOfStationDeparture(String nameOfStationDeparture) {
        this.nameOfStationDeparture = nameOfStationDeparture;
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

    public String getDistanceOfWay() {
        return distanceOfWay;
    }

    public void setDistanceOfWay(String distanceOfWay) {
        this.distanceOfWay = distanceOfWay;
    }

    public String getVIP() {
        return VIP;
    }

    public void setVIP(String VIP) {
        this.VIP = VIP;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(keyOfStationDeparture, route.keyOfStationDeparture) &&
                Objects.equals(nameOfStationDeparture, route.nameOfStationDeparture) &&
                Objects.equals(keyOfStationDestination, route.keyOfStationDestination) &&
                Objects.equals(nameOfStationDestination, route.nameOfStationDestination) &&
                Objects.equals(distanceOfWay, route.distanceOfWay) &&
                Objects.equals(VIP, route.VIP) &&
                Objects.equals(customer, route.customer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(keyOfStationDeparture, nameOfStationDeparture, keyOfStationDestination, nameOfStationDestination, distanceOfWay, VIP, customer);
    }

    @Override
    public String toString() {
        return  keyOfStationDeparture +
                ", " + nameOfStationDeparture +
                ", " + keyOfStationDestination +
                ", " + nameOfStationDestination +
                ", " + distanceOfWay +
                ", " + VIP +
                ", " + customer;
    }

    @Override
    public int compareTo(Object o) {
        try {
            Route p = (Route) o;
            if (p.getVIP() == this.VIP) {
                return p.hashCode() - this.hashCode();
            }
            if (Integer.parseInt(p.getVIP()) < Integer.parseInt(this.VIP)) {
                return -1;
            } else {
                return 1;
            }
        } catch (NullPointerException | ClassCastException e) {
            return 1;
        }
    }
}
