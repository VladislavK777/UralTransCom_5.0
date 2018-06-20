package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetWashingStations;
import com.uraltranscom.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Класс получения станции промывки
 * Implementation for {@link GetWashingStations} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 16.06.2018
 *
 * 16.06.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetWashingStationsImpl extends ConnectionDB implements GetWashingStations {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetReturnStationImpl.class);

    private GetWashingStationsImpl() {
    }

    @Override
    public Map<Integer, List<String>> getReturnStation(String customer) {

        Map<Integer, List<String>> returnMap = new HashMap<>();
        int i = 0;

        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, customer);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                List<String> list = new ArrayList<>();
                list.add(resultSet.getString(1));
                list.add(resultSet.getString(2));
                list.add(resultSet.getString(3));
                returnMap.put(i, list);
                i++;
            }
            logger.debug("Получаем список промывочных станций для заказчика: {}", customer);
        } catch (SQLException ex) {
            logger.error("Ошибка запроса: {}: Заказчик: {}", ex.getMessage(), customer);
        }
        return returnMap;
    }

    private PreparedStatement createPreparedStatement(Connection connection, String customer) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getwashingstations(?) } ");
        callableStatement.setString(1, customer);
        return callableStatement;
    }
}
