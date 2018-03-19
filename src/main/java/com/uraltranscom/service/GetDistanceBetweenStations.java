package com.uraltranscom.service;

/**
 *
 * Интерфейс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 25.10.2017
 *
 * 06.11.2017
 *   1. Добавлено внесение в мапу название ЖД, для более детального поиска номера станции
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

import java.sql.Connection;

public interface GetDistanceBetweenStations {
    int getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination, Connection connection);

}
