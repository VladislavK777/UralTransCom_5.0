package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetDistanceBetweenStations;
import com.uraltranscom.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения расстояния между станциями
 * Implementation for {@link GetDistanceBetweenStations} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
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
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetDistanceBetweenStationsImpl extends ConnectionDB implements GetDistanceBetweenStations {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetDistanceBetweenStationsImpl.class);

    private GetDistanceBetweenStationsImpl() {
    }

    @Override
    public int getDistanceBetweenStations(String keyOfStationDeparture, String keyOfStationDestination) {
        int distance = 0;
        try (Connection connection = getDataSource().getConnection();
             CallableStatement callableStatement = createCallableStatement(connection, keyOfStationDeparture, keyOfStationDestination);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                distance = resultSet.getInt(1);
            }
            logger.debug("Get distance for: {}", keyOfStationDeparture + "_" + keyOfStationDestination + ": " + distance);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса: {} - {}", sqlEx.getMessage(), keyOfStationDeparture + "_" + keyOfStationDestination + ": " + distance);
        }
        return distance;
    }

    private CallableStatement createCallableStatement(Connection connection, String keyOfStationDeparture, String keyOfStationDestination) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getdistancetest2(?,?) } ");
        callableStatement.setString(1, keyOfStationDeparture);
        callableStatement.setString(2, keyOfStationDestination);
        return callableStatement;
    }
}
