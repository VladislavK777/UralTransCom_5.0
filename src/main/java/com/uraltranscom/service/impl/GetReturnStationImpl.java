package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetReturnStation;
import com.uraltranscom.util.ConnectUtil.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 *
 * Класс подбора станции возврата
 * Implementation for {@link GetReturnStation} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 * 04.05.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetReturnStationImpl extends ConnectionDB implements GetReturnStation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetReturnStationImpl.class);

    private GetReturnStationImpl() {
    }

    @Override
    public String getReturnStation(String keyOfStation, int volume) {

        String returnStation = null;

        try (Connection connection = getDataSource().getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, keyOfStation, volume);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                returnStation = resultSet.getString(1);
            }
            logger.info("Подбираем станцию возврата для станции: {}, объем: {}", keyOfStation, volume);
        } catch (SQLException ex) {
            logger.error("Ошибка запроса: {}: Станция: {}, Объем: {}", ex.getMessage(), keyOfStation, volume);
        }
        return returnStation;
    }

    private PreparedStatement createPreparedStatement(Connection connection, String keyOfStation, int volume) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(" { call getreturnstation(?,?,?) } ");
        callableStatement.setString(1, keyOfStation);
        callableStatement.setInt(2, volume);
        callableStatement.setInt(3, volume);
        return callableStatement;
    }
}
