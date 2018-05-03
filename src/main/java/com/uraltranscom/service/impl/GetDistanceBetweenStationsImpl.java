package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetDistanceBetweenStations;
import com.uraltranscom.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения расстояния между станциями
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 25.10.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 22.04.2018
 *   1. Версия 4.1
 * 03.05.2018
 *   1. Версия 4.2
 *
 */

@Service
public class GetDistanceBetweenStationsImpl extends ConnectionDB implements GetDistanceBetweenStations {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsImpl.class);

    private GetDistanceBetweenStationsImpl() {
    }

    @Override
    public List<Integer> getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination) {

        ResultSet resultSet;
        CallableStatement callableStatement = null;
        List<Integer> listResult = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection()) {

            // Подготавливаем запрос
            callableStatement = connection.prepareCall(" { call getdistancetest(?,?) } ");

            // Определяем значения параметров
            callableStatement.setString(1, keyOfStationDeparture);
            callableStatement.setString(2, keyOfStationDestination);

            // Выполняем запрос
            resultSet = callableStatement.executeQuery();

            // Вычитываем полученное значение
            while (resultSet.next()) {
                listResult.add(resultSet.getInt(1));
            }
            logger.debug("Get distance for: {}", keyOfStationDeparture + "_" + keyOfStationDestination + ": " + listResult.get(0));
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса {} - {}", callableStatement, sqlEx.getMessage());
        }
        return listResult;
    }
}
