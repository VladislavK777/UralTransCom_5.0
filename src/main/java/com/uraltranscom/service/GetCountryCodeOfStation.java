package com.uraltranscom.service;

/**
 *
 * Интерфейс получения кода страны для станции
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 05.04.2018
 *
 * 05.04.2018
 *   1. Версия 4.1
 *
 */

public interface GetCountryCodeOfStation {
    int getCountryCodeOfStation(String stationCode);
}
