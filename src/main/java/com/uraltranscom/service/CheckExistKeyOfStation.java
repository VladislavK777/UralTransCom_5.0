package com.uraltranscom.service;

/**
 *
 * Интерфейс проверки корректности кода станции
 *
 * @author Vladislav Klochkov
 * @version 3.0
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 *
 */

import java.sql.Connection;

public interface CheckExistKeyOfStation {
    boolean checkExistKey(String keyOfStation, Connection connection);
}

