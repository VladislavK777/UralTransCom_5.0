package com.uraltranscom.service;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;

import java.util.List;
import java.util.Map;

/**
 *
 * Интерфейс класса-обработчика основного алгоритма поиска
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

public interface ClassHandlerLookingFor {
    void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> listOfWagons);
}
