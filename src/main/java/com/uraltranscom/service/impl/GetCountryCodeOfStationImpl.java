package com.uraltranscom.service.impl;

import com.uraltranscom.service.GetCountryCodeOfStation;
import com.uraltranscom.util.ConnectionDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Класс получения кода страны для станции
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 05.04.2018
 *
 * 05.04.2018
 *   1. Версия 4.1
 *
 */

@Service
public class GetCountryCodeOfStationImpl extends ConnectionDB implements GetCountryCodeOfStation {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetCountryCodeOfStationImpl.class);

    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    private GetCountryCodeOfStationImpl() {
    }

    @Override
    public int getCountryCodeOfStation(String stationCode) {
        int countryCode = -1;
        try (Connection connection = getDataSource().getConnection()) {

            preparedStatement = connection.prepareStatement("select c.id from countries c, stations s, roads r where s.road_id = r.id and r.id_country = c.id and s.station_id = ?");
            preparedStatement.setString(1, stationCode);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                countryCode = resultSet.getInt(1);
                if (resultSet.wasNull()) {
                    countryCode = -1;
                }
            }
        } catch (SQLException ex) {
            logger.error("Ошибка запроса");
        }
        return countryCode;
    }
}
