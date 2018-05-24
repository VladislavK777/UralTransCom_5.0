package com.uraltranscom.service;

/**
 *
 * Интерфейс проверки корректности кода станции
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
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public interface CheckExistKeyOfStation {
    boolean checkExistKey(String keyOfStation);
}

