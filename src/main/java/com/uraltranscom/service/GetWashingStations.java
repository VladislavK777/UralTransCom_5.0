package com.uraltranscom.service;

import java.util.List;
import java.util.Map;

/**
 *
 * Интерфейс получения станции промывки
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 24.05.2018
 *
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public interface GetWashingStations {
    /**
     *
     * @param customer - название Заказчика
     * @return - возвращает мапу со списком информации о станции(код, название, дорога)
     */
    Map<Integer, List<String>> getReturnStation(String customer);

}
