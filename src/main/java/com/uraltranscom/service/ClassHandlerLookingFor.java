package com.uraltranscom.service;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * Интерфейс класса-обработчика основного алгоритма поиска
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 *
 */

public interface ClassHandlerLookingFor {
    void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> listOfWagons, Connection connection);
}
