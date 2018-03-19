package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetDistanceBetweenStations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 25.10.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

@Service
public class GetDistanceBetweenStationsImpl implements GetDistanceBetweenStations {

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsImpl.class);

    private static ResultSet resultSet;
    private static CallableStatement callableStatement;

    @Override
    public int getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination, Connection connection) {
        int distance = 0;
        try {

            // Подготавливаем запрос
            callableStatement = connection.prepareCall(" { call getDistance(?,?) } ");

            // Определяем значения параметров
            callableStatement.setString(1, keyOfStationDeparture);
            callableStatement.setString(2, keyOfStationDestination);

            // Выполняем запрос
            resultSet = callableStatement.executeQuery();

            // Вычитываем полученное значение
            while (resultSet.next()) {
                distance = resultSet.getInt(1);
                if (resultSet.wasNull()) {
                    distance = -1;
                }
            }
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса {} - {}", callableStatement, sqlEx.getMessage());
        }
        return distance;
    }
}
