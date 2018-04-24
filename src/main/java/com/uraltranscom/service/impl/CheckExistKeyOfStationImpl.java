package com.uraltranscom.service.impl;

/**
 *
 * Класс проверки корректности кода станции
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

import com.uraltranscom.service.CheckExistKeyOfStation;
import com.uraltranscom.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CheckExistKeyOfStationImpl extends ConnectionDB implements CheckExistKeyOfStation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(CheckExistKeyOfStationImpl.class);

    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    private CheckExistKeyOfStationImpl() {
    }

    public boolean checkExistKey(String keyOfStation) {
        Boolean isExist = false;
        try (Connection connection = getDataSource().getConnection()) {

            // Подготавливаем запрос
            preparedStatement = connection.prepareStatement("select distinct 1 from distances where (station_id1 = ? or station_id2 = ?)");

            // Определяем значения параметров
            preparedStatement.setString(1, keyOfStation);
            preparedStatement.setString(2, keyOfStation);

            // Выполняем запрос
            resultSet = preparedStatement.executeQuery();

            // Вычитываем полученное значение
            while (resultSet.next()) {
                if (!resultSet.wasNull()) {
                    isExist = true;
                }
            }
        } catch (SQLException ex) {
            logger.error("Ошибка запроса: {}", preparedStatement);
        }
        return isExist;
    }
}
