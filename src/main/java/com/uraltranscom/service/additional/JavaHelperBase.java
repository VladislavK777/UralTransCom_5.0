package com.uraltranscom.service.additional;

/**
 *
 * Класс-помощник содержит основные константы
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 25.03.2018
 *
 * 25.03.2018
 *   1. Версия 4.0
 *
 */

public class JavaHelperBase {

    // Максимальное количетво дней в обороте вагона
    public static final int MAX_FULL_CIRCLE_DAYS = 31;

    // Параметры загрузки вагонов
    public static final int LOADING_OF_WAGON_KR = 7; // Крытый вагон
    public static final int LOADING_OF_WAGON_PV = 4; // Полувагон
    public static final int UNLOADING_OF_WAGON = 4; // Выгрузка вагонов

    // Типы вагонов
    public static final String TYPE_OF_WAGON_KR = "КР";
    public static final String TYPE_OF_WAGON_PV = "ПВ";

    // Константы для класса преобразования префикла "дней"
    public static final String PREFIX_ONE_DAY = "день";
    public static final String PREFIX_2_4_DAYS = "дня";
    public static final String PREFIX_5_10_DAYS = "дней";

    // Максимальное расстояние для пустого вагона
    public static final int MAX_DISTANCE = 3000;

}
