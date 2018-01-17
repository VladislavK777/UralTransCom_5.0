package com.uraltranscom.service;

/*
 *
 * Интерфейс получения списка вагонов
 *
 * @author Vladislav Klochkov
 * @version 3.0
 * @create 25.10.2017
 *
 * 06.11.2017
 *   1. Добавлено внесение в мапу название ЖД, для более детального поиска номера станции
 * 10.11.2017
 *   1. Переделано получения целого числа в поле Номер вагона
 * 12.01.2018
 *   1. Версия 3.0
 *
 */

public interface GetListOfWagons {
    void fillMapOfWagons();
}