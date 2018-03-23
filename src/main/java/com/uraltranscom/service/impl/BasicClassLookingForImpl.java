package com.uraltranscom.service.impl;

import com.uraltranscom.dao.ConnectionDB;
import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.BasicClassLookingFor;
import com.uraltranscom.service.additional.CompareMapValue;
import com.uraltranscom.service.additional.PrefixOfDays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.*;

/**
 *
 * Основной класс алгоритма расчета
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 01.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

@Service
public class BasicClassLookingForImpl implements BasicClassLookingFor{
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassLookingForImpl.class);

    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private GetListOfDistanceImpl getListOfDistance;

    private Map<Integer, Route> tempMapOfRoutes = new HashMap<>();
    private List<Wagon> tempListOfWagons = new ArrayList<>();
    private static Connection connection;

    // Итоговые массивы для вывода на страницу
    // Массив распределенных маршрутов и вагонов
    private List<String> listOfDistributedRoutesAndWagons = new ArrayList<>();

    // Массив нераспределенных маршрутов
    private List<String> listOfUndistributedRoutes = new ArrayList<>();

    // Массив нераспределенных вагонов
    private List<String> listOfUndistributedWagons = new ArrayList<>();

    // Массив ошибок
    private List<String> listOfError = new ArrayList<>();

    // Мапа для записи в файл Вагона + Станция назначения.
    private Map<String, Route> totalMapWithWagonNumberAndRoute = new HashMap<>();

    @Override
    public void lookingForOptimalMapOfRoute() {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".lookingForOptimalMapOfRoute");

        // Устанавливаем соединение
        connection = ConnectionDB.getConnection();

        // Очищаем массивы итоговые
        listOfDistributedRoutesAndWagons.clear();
        listOfUndistributedRoutes.clear();
        listOfUndistributedWagons.clear();
        listOfError.clear();

        // Очищаем мапу по количеству дней
        getFullMonthCircleOfWagonImpl.getMapOfDaysOfWagon().clear();

        // Запускаем метод заполненеия первоначальной мапы расстояний
        getListOfDistance.setConnection(connection);
        getListOfDistance.fillMap();

        // Заполняем мапы
        tempMapOfRoutes = getListOfDistance.getMapOfRoutes();
        tempListOfWagons = getListOfDistance.getListOfWagons();

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
                Iterator<Map.Entry<Integer, Route>> iterator = tempMapOfRoutes.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, Route> tempMapOfRoute = iterator.next();
                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = tempMapOfRoute.getValue().getKeyOfStationDeparture();
                    list.add(numberOfWagon);
                    list.add(tempMapOfRoute.getValue());
                    String key = tempListOfWagons.get(i).getNameOfStationDestination().trim() + "_" + tempMapOfRoute.getValue().getNameOfStationDeparture().trim();
                    if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                        mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key));
                    } else {
                        int distance = getDistanceBetweenStations.getDistanceBetweenStations(keyOfStationOfWagonDestination, keyOfStationDeparture, connection);
                        if (distance != -1) {
                            getListOfDistance.getRootMapWithDistances().put(key, distance);
                            mapDistance.put(list, distance);
                        } else {
                            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationDeparture, connection)) {
                                listOfError.add("Проверьте код станции " + keyOfStationDeparture);
                                logger.error("Проверьте код станции {}", keyOfStationDeparture);
                                listOfUndistributedRoutes.add(tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDeparture() + " - " + tempMapOfRoutes.get(tempMapOfRoute.getKey()).getNameOfStationDestination());
                                iterator.remove();
                                break;
                            }
                            if (!checkExistKeyOfStationImpl.checkExistKey(keyOfStationOfWagonDestination, connection)) {
                                listOfError.add("Проверьте код станции " + keyOfStationOfWagonDestination);
                                logger.error("Проверьте код станции {}", keyOfStationOfWagonDestination);
                                if (!getFullMonthCircleOfWagonImpl.getMapOfDaysOfWagon().containsKey(tempListOfWagons.get(i).getNumberOfWagon())) {
                                    listOfUndistributedWagons.add(tempListOfWagons.get(i).getNumberOfWagon());
                                }
                                tempListOfWagons.remove(i);
                                countWagons = tempListOfWagons.size();
                                break;
                            }
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

                                    totalMapWithWagonNumberAndRoute.put(numberOfWagon, tempMapOfRouteForDelete.get(j));

                                    // Удаляем маршрут, так как он занят вагоном
                                    it.remove();

                                    // Выходим из цикла, так как с ним больше ничего не сделать
                                    break outer;
                                } else {
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

        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".lookingForOptimalMapOfRoute");
    }

    public GetDistanceBetweenStationsImpl getGetDistanceBetweenStations() {
        return getDistanceBetweenStations;
    }

    public void setGetDistanceBetweenStations(GetDistanceBetweenStationsImpl getDistanceBetweenStations) {
        this.getDistanceBetweenStations = getDistanceBetweenStations;
    }

    public GetFullMonthCircleOfWagonImpl getGetFullMonthCircleOfWagonImpl() {
        return getFullMonthCircleOfWagonImpl;
    }

    public void setGetFullMonthCircleOfWagonImpl(GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagonImpl) {
        this.getFullMonthCircleOfWagonImpl = getFullMonthCircleOfWagonImpl;
    }

    public CheckExistKeyOfStationImpl getCheckExistKeyOfStationImpl() {
        return checkExistKeyOfStationImpl;
    }

    public void setCheckExistKeyOfStationImpl(CheckExistKeyOfStationImpl checkExistKeyOfStationImpl) {
        this.checkExistKeyOfStationImpl = checkExistKeyOfStationImpl;
    }

    public Map<Integer, Route> getTempMapOfRoutes() {
        return tempMapOfRoutes;
    }

    public void setTempMapOfRoutes(Map<Integer, Route> tempMapOfRoutes) {
        this.tempMapOfRoutes = tempMapOfRoutes;
    }

    public List<Wagon> getTempListOfWagons() {
        return tempListOfWagons;
    }

    public void setTempListOfWagons(List<Wagon> tempListOfWagons) {
        this.tempListOfWagons = tempListOfWagons;
    }

    public List<String> getListOfDistributedRoutesAndWagons() {
        return listOfDistributedRoutesAndWagons;
    }

    public void setListOfDistributedRoutesAndWagons(List<String> listOfDistributedRoutesAndWagons) {
        this.listOfDistributedRoutesAndWagons = listOfDistributedRoutesAndWagons;
    }

    public List<String> getListOfUndistributedRoutes() {
        return listOfUndistributedRoutes;
    }

    public void setListOfUndistributedRoutes(List<String> listOfUndistributedRoutes) {
        this.listOfUndistributedRoutes = listOfUndistributedRoutes;
    }

    public List<String> getListOfUndistributedWagons() {
        return listOfUndistributedWagons;
    }

    public void setListOfUndistributedWagons(List<String> listOfUndistributedWagons) {
        this.listOfUndistributedWagons = listOfUndistributedWagons;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public void setListOfError(List<String> listOfError) {
        this.listOfError = listOfError;
    }

    public GetListOfDistanceImpl getGetListOfDistance() {
        return getListOfDistance;
    }

    public void setGetListOfDistance(GetListOfDistanceImpl getListOfDistance) {
        this.getListOfDistance = getListOfDistance;
    }

    public Map<String, Route> getTotalMapWithWagonNumberAndRoute() {
        return totalMapWithWagonNumberAndRoute;
    }

    public void setTotalMapWithWagonNumberAndRoute(Map<String, Route> totalMapWithWagonNumberAndRoute) {
        this.totalMapWithWagonNumberAndRoute = totalMapWithWagonNumberAndRoute;
    }
}