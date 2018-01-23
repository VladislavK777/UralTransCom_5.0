package com.uraltranscom.service;

import com.uraltranscom.dao.ConnectionDB;
import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.additional.CompareMapValue;
import com.uraltranscom.service.additional.PrefixOfDays;
import com.uraltranscom.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

/*
 *
 * Основной класс алгоритма расчета
 *
 * @author Vladislav Klochkov
 * @version 3.0
 * @create 01.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 *
 */

@Service
public class MethodOfBasicLogic {

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(MethodOfBasicLogic.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;

    private Map<Integer, Route> tempMapOfRoutes = new HashMap<>();
    private List<Wagon> tempListOfWagons = new ArrayList<>();
    private static Connection connection;

    // Итоговые массивы для вывода на страницу
    // Массив распределенных маршрутов и вагонов
    private List <String> listOfDistributedRoutesAndWagons = new ArrayList<>();

    // Массив нераспределенных маршрутов
    private List <String> listOfUndistributedRoutes = new ArrayList<>();

    // Массив нераспределенных вагонов
    private List <String> listOfUndistributedWagons = new ArrayList<>();

    // Массив ошибок
    private List <String> listOfError = new ArrayList<>();

    public void lookingForOptimalMapOfRoute() {
        // Устанавливаем соединение
        connection = ConnectionDB.getConnection();

        // Заполняем мапы
        tempMapOfRoutes = getListOfRoutesImpl.getMapOfRoutes();
        tempListOfWagons = getListOfWagonsImpl.getListOfWagons();

        // Очищаем массивы итоговые
        listOfDistributedRoutesAndWagons.clear();
        listOfUndistributedRoutes.clear();
        listOfUndistributedWagons.clear();
        listOfError.clear();

        // Очищаем мапу по количеству дней
        getFullMonthCircleOfWagonImpl.getMapOfDaysOfWagon().clear();

        // Список расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Список распределенных вагонов
        Set<String> SetOfDistributedWagons = new HashSet<>();

        // Список нераспределенных вагонов
        Set<String> SetOfUndistributedWagons = new HashSet<>();

        // Запускаем цикл
        while (!tempMapOfRoutes.isEmpty() && !tempListOfWagons.isEmpty()) {
            int countWagons = tempListOfWagons.size();

            // Очищаем массивы
            mapDistance.clear();

            for (int i = 0; i < countWagons; i++) {

                // Поулчаем номер вагона
                String numberOfWagon = tempListOfWagons.get(i).getNumberOfWagon().trim();

                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = tempListOfWagons.get(i).getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                for (Map.Entry<Integer, Route> tempMapOfRoute : tempMapOfRoutes.entrySet()) {
                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = tempMapOfRoute.getValue().getKeyOfStationDeparture();
                    list.add(numberOfWagon);
                    list.add(tempMapOfRoute.getValue());
                    int distance = getDistanceBetweenStations.getDistanceBetweenStations(keyOfStationOfWagonDestination, keyOfStationDeparture, connection);
                    if (distance != -1) {
                        mapDistance.put(list, distance);
                    } else {
                        if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationDeparture)) {
                            listOfError.add("Проверьте код станции " + keyOfStationDeparture);
                            logger.error("Проверьте код станции {}", keyOfStationDeparture);
                            listOfUndistributedRoutes.add(tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDeparture() + " - " + tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDestination());
                            tempMapOfRoutes.remove(tempMapOfRoute.getKey());
                            break;
                        }
                        if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationOfWagonDestination)) {
                            listOfError.add("Проверьте код станции " + keyOfStationOfWagonDestination);
                            logger.error("Проверьте код станции {}", keyOfStationOfWagonDestination);
                            listOfUndistributedWagons.add(tempListOfWagons.get(i).getNumberOfWagon());
                            tempListOfWagons.remove(i);
                            countWagons = tempListOfWagons.size();
                            break;
                        }
                    }
                }
            }
            // Отсортированный список расстояний
            Map<List<Object>, Integer> mapDistanceSort = new LinkedHashMap<>();

            // Поиск меньшего значения в мапе
            int index = mapDistance.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[index];
            index = 0;
            for (Map.Entry<List<Object>, Integer> entry : mapDistance.entrySet()) {
                compareMapValues[index++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);
            for (CompareMapValue cmv : compareMapValues) {
                mapDistanceSort.put(cmv.wagon, cmv.distance);
            }

            Map<List<Object>, Integer> mapDistanceSortWithPriority = CompareMapValue.sortMap(mapDistanceSort);

            // Мапа для удаления использованных маршрутов
            Map<Integer, Route> mapOfRoutesForDelete = tempMapOfRoutes;

            // Цикл формирования рейсов
            // Проверяем на пустоту мап, либо вагоны, либо рейсы
            outer:
            if (!mapDistanceSortWithPriority.isEmpty() && !tempListOfWagons.isEmpty()) {
                Map.Entry<List<Object>, Integer> mapDistanceSortFirstElement = mapDistanceSortWithPriority.entrySet().iterator().next();
                List<Object> listRouteMinDistance = mapDistanceSortFirstElement.getKey();
                Route r = (Route) listRouteMinDistance.get(1);
                String numberOfWagon = (String) listRouteMinDistance.get(0);
                String nameOfStationDepartureOfWagon = r.getNameOfStationDeparture();

                final int[] o = {0};
                Map<Integer, Route> tempMapOfRouteForDelete = new HashMap<>();
                mapOfRoutesForDelete.forEach((k, v) -> {
                    tempMapOfRouteForDelete.put(o[0], v);
                    o[0]++;
                });

                for (Iterator<Map.Entry<Integer, Route>> it = mapOfRoutesForDelete.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<Integer, Route> entry = it.next();
                    for (int j = 0; j < tempMapOfRouteForDelete.size(); j++) {
                        // Находим маршрут для вагона
                        if (tempMapOfRouteForDelete.get(j).equals(r)) {
                            if (tempMapOfRouteForDelete.get(j).equals(entry.getValue())) {
                                int getKeyNumber = 0;
                                for (int i = 0; i < tempListOfWagons.size(); i++) {
                                    if (tempListOfWagons.get(i).getNumberOfWagon().equals(numberOfWagon)) {
                                        getKeyNumber = i;
                                    }
                                }
                                // Расчет дней затраченных одним вагоном на один цикл
                                getFullMonthCircleOfWagonImpl.fullDays(numberOfWagon, tempListOfWagons.get(getKeyNumber).getTypeOfWagon(), mapDistanceSortFirstElement.getValue(), r.getDistanceOfWay());

                                // Число дней пройденных вагоном
                                double numberOfDaysOfWagonDouble = getFullMonthCircleOfWagonImpl.getNumberOfDaysOfWagon(numberOfWagon);

                                //Округляем до целого
                                int numberOfDaysOfWagon = (int) numberOfDaysOfWagonDouble;

                                // Если больше 30 дней, то исключаем вагон, лимит 30 дней
                                if (numberOfDaysOfWagon < 31) {

                                    // Заменяем маршрут вагону
                                    tempListOfWagons.set(getKeyNumber, new Wagon(numberOfWagon, tempListOfWagons.get(getKeyNumber).getTypeOfWagon(), tempMapOfRouteForDelete.get(j).getKeyOfStationDestination(), tempMapOfRouteForDelete.get(j).getNameOfStationDestination()));

                                    // Добавляем новый вагон в список
                                    SetOfDistributedWagons.add(numberOfWagon);

                                    listOfDistributedRoutesAndWagons.add("Вагон " + numberOfWagon + " едет на станцию "
                                            + nameOfStationDepartureOfWagon + ": "
                                            + mapDistanceSortFirstElement.getValue() + " км. Маршрут: "
                                            + tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination() + ". Общее время в пути: "
                                            + numberOfDaysOfWagon + " " + PrefixOfDays.parsePrefixOfDays(numberOfDaysOfWagon));

                                    logger.info("Вагон {} едет на станцию {}: {} км.", numberOfWagon, nameOfStationDepartureOfWagon, mapDistanceSortFirstElement.getValue());
                                    logger.info("Общее время в пути: {} {}.", numberOfDaysOfWagon, PrefixOfDays.parsePrefixOfDays(numberOfDaysOfWagon));
                                    logger.info("Маршрут: {}", tempMapOfRouteForDelete.get(j).toString());
                                    logger.info("-------------------------------------------------");

                                    // Удаляем маршрут, так как он занят вагоном
                                    it.remove();

                                    // Выходим из цикла, так как с ним больше ничего не сделать
                                    break outer;
                                } else {
                                    logger.info("Вагон {} должен был ехать на {}: {} км.", numberOfWagon, nameOfStationDepartureOfWagon, mapDistanceSortFirstElement.getValue());
                                    logger.info("Общее время в пути: {} {}.", numberOfDaysOfWagon, PrefixOfDays.parsePrefixOfDays(numberOfDaysOfWagon));
                                    logger.info("Далее по маршруту: {}", tempMapOfRouteForDelete.get(j).toString());
                                    logger.info("-------------------------------------------------");

                                    if (!SetOfDistributedWagons.contains(numberOfWagon)) {
                                        SetOfUndistributedWagons.add(numberOfWagon);
                                    }

                                    // Удаляем вагон
                                    tempListOfWagons.remove(getKeyNumber);

                                    // Выходим из цикла, так как с ним больше ничего не сделать
                                    break outer;
                                }
                            }
                        }
                    }
                }

                // Обновляем мапу маршрутов
                tempMapOfRoutes = mapOfRoutesForDelete;

                // Очищаем временный массив рейсов
                tempMapOfRouteForDelete.clear();
            }
        }

        // Заполняем итоговые массивы
        tempMapOfRoutes.forEach((k, v) -> {
            listOfUndistributedRoutes.add(v.getNameOfStationDeparture() + " - " + v.getNameOfStationDestination());
        });

        SetOfUndistributedWagons.forEach((k) -> {
            listOfUndistributedWagons.add(k.toString());
        });

        /*// Закрываем соединение
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Ошибка закрытия соединения - {}", e.getMessage());
        }*/

    }

    public List<String> getListOfDistributedRoutesAndWagons() {
        return listOfDistributedRoutesAndWagons;
    }

    public List<String> getListOfUndistributedRoutes() {
        return listOfUndistributedRoutes;
    }

    public List<String> getListOfUndistributedWagons() {
        return listOfUndistributedWagons;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public GetListOfRoutesImpl getGetListOfRoutesImpl() {
        return getListOfRoutesImpl;
    }

    public void setGetListOfRoutesImpl(GetListOfRoutesImpl getListOfRoutesImpl) {
        this.getListOfRoutesImpl = getListOfRoutesImpl;
    }

    public GetListOfWagonsImpl getGetListOfWagonsImpl() {
        return getListOfWagonsImpl;
    }

    public void setGetListOfWagonsImpl(GetListOfWagonsImpl getListOfWagonsImpl) {
        this.getListOfWagonsImpl = getListOfWagonsImpl;
    }
}