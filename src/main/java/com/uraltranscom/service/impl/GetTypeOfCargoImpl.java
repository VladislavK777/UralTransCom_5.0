package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetTypeOfCargo;
import com.uraltranscom.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения класса груза
 *
 * @author Vladislav Klochkov
 * @version 4.2
 * @create 03.05.2018
 *
 * 03.05.2018
 *   1. Версия 4.1
 *
 */

@Service
public class GetTypeOfCargoImpl extends ConnectionDB implements GetTypeOfCargo {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetTypeOfCargoImpl.class);

    public GetTypeOfCargoImpl() {
    }

    @Override
    public int getTypeOfCargo(String key) {

        ResultSet resultSet;
        CallableStatement callableStatement = null;
        int type = 0;

        try (Connection connection = getDataSource().getConnection()) {

            // Подготавливаем запрос
            callableStatement = connection.prepareCall(" { call getclassofcargo(?) } ");

            // Определяем значения параметров
            callableStatement.setString(1, key);

            // Выполняем запрос
            resultSet = callableStatement.executeQuery();

            // Вычитываем полученное значение
            while (resultSet.next()) {
                type = resultSet.getInt(1);
            }
            logger.debug("Get type of cargo: {}", key + ": " + type);
        } catch (SQLException sqlEx) {
            logger.error("Ошибка запроса {} - {}", callableStatement, sqlEx.getMessage());
        }
        return type;
    }
}
