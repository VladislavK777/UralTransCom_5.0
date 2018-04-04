package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.ClassHandlerLookingFor;
import com.uraltranscom.service.additional.CompareMapValue;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.service.additional.PrefixOfDays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс-обработчик алгоритма расчета
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

@Service
public class ClassHandlerLookingForImpl extends JavaHelperBase implements ClassHandlerLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerLookingForImpl.class);

    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private BasicClassLookingForImpl basicClassLookingFor;

    private ClassHandlerLookingForImpl() {
    }

    @Override
    public void lookingForOptimalMapOfRoute(Map<Integer, Route> mapOfRoutes, List<Wagon> tempListOfWagons) {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");

        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>();
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>();
        for (Wagon wagon : tempListOfWagons) {
            copyListOfWagon.add(wagon);
        }
        tempMapOfRoutes.putAll(mapOfRoutes);

        // Список расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Запускаем цикл
        Boolean isOk = true;
        while (!tempMapOfRoutes.isEmpty() && !copyListOfWagon.isEmpty()) {
            int countWagons = copyListOfWagon.size();

            // Очищаем массивы
            mapDistance.clear();

            for (int i = 0; i < countWagons; i++) {

                // Поулчаем номер вагона
                String numberOfWagon = copyListOfWagon.get(i).getNumberOfWagon().trim();

                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = copyListOfWagon.get(i).getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                Iterator<Map.Entry<Integer, Route>> iterator = tempMapOfRoutes.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Route> tempMapOfRoute = iterator.next();
                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = tempMapOfRoute.getValue().getKeyOfStationDeparture();
                    list.add(numberOfWagon);
                    list.add(tempMapOfRoute.getValue());
                    String key = copyListOfWagon.get(i).getNameOfStationDestination().trim() + "_" + tempMapOfRoute.getValue().getNameOfStationDeparture().trim();
                    if (basicClassLookingFor.getGetListOfDistance().getRootMapWithDistances().containsKey(key)) {
                        mapDistance.put(list, basicClassLookingFor.getGetListOfDistance().getRootMapWithDistances().get(key));
                    } else if (basicClassLookingFor.getGetListOfDistance().getRootMapWithDistanceMoreDist3000().containsKey(key)) {
                        continue; // Нам не интересны расстояния больше 3000км
                    } else {
                        int distance = getDistanceBetweenStations.getDistanceBetweenStations(keyOfStationOfWagonDestination, keyOfStationDeparture);
                        if (distance != -1) {
                            if (distance <= MAX_DISTANCE) {
                                basicClassLookingFor.getGetListOfDistance().getRootMapWithDistances().put(key, distance);
                                mapDistance.put(list, distance);
                            } else {
                                basicClassLookingFor.getGetListOfDistance().getRootMapWithDistanceMoreDist3000().put(key, distance);
                            }
                        } else {
                            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationDeparture)) {
                                basicClassLookingFor.getListOfError().add("Проверьте код станции " + keyOfStationDeparture);
                                logger.error("Проверьте код станции {}", keyOfStationDeparture);
                                basicClassLookingFor.getListOfUndistributedRoutes().add(tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDeparture() + " - " +
                                        tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDestination() + ". Оставшиеся количество рейсов: " +
                                        tempMapOfRoutes.get(tempMapOfRoute.getKey()).getCountOrders());
                                iterator.remove();
                                break;
                            }
                            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationOfWagonDestination)) {
                                basicClassLookingFor.getListOfError().add("Проверьте код станции " + keyOfStationOfWagonDestination);
                                logger.error("Проверьте код станции {}", keyOfStationOfWagonDestination);
                                copyListOfWagon.remove(i);
                                countWagons = copyListOfWagon.size();
                                break;
                            }
                        }
                    }
                }
            }

            if (!mapDistance.isEmpty()) {
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

                // Мапа для удаления использованных маршрутов
                Map<Integer, Route> mapOfRoutesForDelete = tempMapOfRoutes;

                // Цикл формирования рейсов
                // Проверяем на пустоту мап, либо вагоны, либо рейсы
                outer:
                if (!mapDistanceSort.isEmpty() && !copyListOfWagon.isEmpty()) {
                    Map.Entry<List<Object>, Integer> mapDistanceSortFirstElement = mapDistanceSort.entrySet().iterator().next();
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
                                    for (int i = 0; i < copyListOfWagon.size(); i++) {
                                        if (copyListOfWagon.get(i).getNumberOfWagon().equals(numberOfWagon)) {
                                            getKeyNumber = i;
                                        }
                                    }

                                    // Число дней пройденных вагоном
                                    int numberOfDaysOfWagon = getFullMonthCircleOfWagonImpl.fullDays(copyListOfWagon.get(getKeyNumber).getTypeOfWagon(), mapDistanceSortFirstElement.getValue(), r.getDistanceOfWay());

                                    // Если больше 30 дней, то исключаем вагон, лимит 30 дней
                                    if (numberOfDaysOfWagon < MAX_FULL_CIRCLE_DAYS) {
                                        // Удаляем вагон
                                        for (int i = 0; i < tempListOfWagons.size(); i++) {
                                            if (tempListOfWagons.get(i).getNumberOfWagon().equals(copyListOfWagon.get(getKeyNumber).getNumberOfWagon())) {
                                                tempListOfWagons.remove(i);
                                            }
                                        }
                                        copyListOfWagon.remove(getKeyNumber);

                                        // Уменьшаем количество рейсов у маршрута
                                        mapOfRoutesForDelete.put(entry.getKey(), new Route(mapOfRoutesForDelete.get(entry.getKey()).getKeyOfStationDeparture(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getNameOfStationDeparture(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getKeyOfStationDestination(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getNameOfStationDestination(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getDistanceOfWay(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getVIP(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getCustomer(),
                                                mapOfRoutesForDelete.get(entry.getKey()).getCountOrders() - 1));

                                        basicClassLookingFor.getListOfDistributedRoutesAndWagons().add("Вагон " + numberOfWagon + " едет на станцию "
                                                + nameOfStationDepartureOfWagon + ": "
                                                + mapDistanceSortFirstElement.getValue() + " км. Маршрут: "
                                                + tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination() + ". Общее время в пути: "
                                                + numberOfDaysOfWagon + " " + PrefixOfDays.parsePrefixOfDays(numberOfDaysOfWagon) + ".");

                                        basicClassLookingFor.getTotalMapWithWagonNumberAndRoute().put(numberOfWagon, tempMapOfRouteForDelete.get(j));

                                        // Удаляем маршрут, если по нему 0 рейсов
                                        if (mapOfRoutesForDelete.get(entry.getKey()).getCountOrders() == 0) {
                                            it.remove();
                                        }
                                        // Выходим из цикла, так как с ним больше ничего не сделать
                                        break outer;
                                    } else {
                                        // Удаляем вагон
                                        copyListOfWagon.remove(getKeyNumber);

                                        // Выходим из цикла, так как с ним больше ничего не сделать
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Завершает корневой цикл, так как нет больше расстояний меньше 3000км
                isOk = false;
            }
            if(!isOk) {
                break;
            }
        }

        // Заполняем итоговый массив маршрутов
        tempMapOfRoutes.forEach((k, v) -> {
            basicClassLookingFor.getListOfUndistributedRoutes().add(v.getNameOfStationDeparture() + " - " + v.getNameOfStationDestination() + ". Оставшиеся количество рейсов: " + v.getCountOrders());
        });

        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
    }
}
