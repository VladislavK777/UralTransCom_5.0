package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.model_ext.TotalCalculateRoute;
import com.uraltranscom.service.ClassHandlerLookingFor;
import com.uraltranscom.service.additional.CompareMapValue;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.service.additional.PrepareDistanceOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс-обработчик алгоритма расчета
 * Implementation for {@link ClassHandlerLookingFor} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 28.03.2018
 *
 * 28.03.2018
 *   1. Версия 4.0
 * 23.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class ClassHandlerLookingForImpl extends JavaHelperBase implements ClassHandlerLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ClassHandlerLookingForImpl.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetReturnStationImpl getReturnStation;
    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;
    @Autowired
    private GetTypeOfCargoImpl getTypeOfCargo;
    @Autowired
    private GetFullMonthCircleOfWagonImpl getFullMonthCircleOfWagon;
    @Autowired
    private YieldCalculationImpl yieldCalculation;

    // Основная мапа
    private Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> rootMap = new HashMap<>();

    private ClassHandlerLookingForImpl() {
    }

    @Override
    public void lookingForOptimalMapOfRoute() {
        logger.info("Start root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");

        Map<Integer, Route> mapOfRoutes = new HashMap<>(getListOfRoutesImpl.getMapOfRoutes());
        List<Wagon> listOfWagons = new ArrayList<>(getListOfWagonsImpl.getListOfWagons());

        for (int i = 0; i < listOfWagons.size(); i++) {
            Map<Map<Route, TotalCalculateRoute>, Double> mapRoute = new HashMap<>();
            String keyStationDep = listOfWagons.get(i).getKeyOfStationDep();
            String nameStationDep = listOfWagons.get(i).getNameOfStationDeparture();
            String keyStationDestination = listOfWagons.get(i).getKeyOfStationDestination();
            String nameStationDestination = listOfWagons.get(i).getNameOfStationDestination();
            String currentCargo = listOfWagons.get(i).getCargo();
            String distanceCurrentRoute = String.valueOf(getDistanceBetweenStations.getDistanceBetweenStations(keyStationDep, keyStationDestination));
            double rateCurrentRoute = listOfWagons.get(i).getRate();
            if (rateCurrentRoute == 0.00) {
                rateCurrentRoute = 55000.00;
            }
            for (int j = 0; j < mapOfRoutes.size(); j++) {
                Map<Route, TotalCalculateRoute> mapRouteAndTotalCalculate = new HashMap<>();
                Route route = mapOfRoutes.get(j);
                if (listOfWagons.get(i).getVolume() >= route.getVolumePeriod().getVolumeFrom() && listOfWagons.get(i).getVolume() <= route.getVolumePeriod().getVolumeTo()) {
                    String keyStationDepartureRoute = route.getKeyOfStationDeparture();
                    String nameStationDepartureRoute = route.getNameOfStationDeparture();
                    String keyStationDestinationRoute = route.getKeyOfStationDestination();
                    String nameStationDestinationRoute = route.getNameOfStationDestination();
                    String cargoRoute = route.getCargo();
                    if (keyStationDestinationRoute.equals("")) {
                        keyStationDestinationRoute = mapOfRoutes.get(new Random().nextInt(200)).getKeyOfStationDeparture();
                        if (keyStationDestinationRoute.equals(keyStationDepartureRoute)) {
                            keyStationDestinationRoute = mapOfRoutes.get(new Random().nextInt(200)).getKeyOfStationDeparture();
                        }
                    }
                    String distanceRoute = route.getDistanceOfWay();
                    String keyStationOporn = getReturnStation.getReturnStation(keyStationDestinationRoute, listOfWagons.get(i).getVolume()); //TODO изменить имя переменной

                    double rateRoute = route.getRate();
                    if (rateRoute == 0.00) {
                        rateRoute = 55000.00;
                    }
                    int distanceEmpty1 = getDistanceBetweenStations.getDistanceBetweenStations(keyStationDestination, keyStationDepartureRoute);
                    double tariff1 = distanceEmpty1 * 4.83;
                    int distanceEmpty2 = getDistanceBetweenStations.getDistanceBetweenStations(keyStationDestinationRoute, keyStationOporn);
                    double tariff2 = distanceEmpty2 * 4.83;
                    int fullDays = getFullMonthCircleOfWagon.fullDaysForYield("КР", distanceCurrentRoute, distanceEmpty1, distanceRoute, distanceEmpty2);
                    double yieldDay = yieldCalculation.yieldCalculation(rateCurrentRoute, tariff1, rateRoute, tariff2, fullDays);
                    mapRouteAndTotalCalculate.put(route, new TotalCalculateRoute(nameStationDep,
                            nameStationDestination,
                            currentCargo,
                            distanceCurrentRoute,
                            rateCurrentRoute,
                            Math.ceil(Integer.parseInt(distanceCurrentRoute)/PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceCurrentRoute))),
                            nameStationDestination,
                            nameStationDepartureRoute,
                            String.valueOf(distanceEmpty1),
                            tariff1,
                            Math.ceil(distanceEmpty1/PrepareDistanceOfDay.getDistanceOfDay(distanceEmpty1)),
                            nameStationDepartureRoute,
                            nameStationDestinationRoute,
                            cargoRoute,
                            distanceRoute,
                            rateRoute,
                            Math.ceil(Integer.parseInt(distanceRoute)/PrepareDistanceOfDay.getDistanceOfDay(Integer.parseInt(distanceRoute))),
                            nameStationDestinationRoute,
                            keyStationOporn,
                            String.valueOf(distanceEmpty2),
                            tariff2,
                            Math.ceil(distanceEmpty2/PrepareDistanceOfDay.getDistanceOfDay(distanceEmpty2))
                            ));
                    mapRoute.put(mapRouteAndTotalCalculate, yieldDay);
                }
            }
            // Сортируем мапу по значению
            Map<Map<Route, TotalCalculateRoute>, Double> mapYieldSort = new LinkedHashMap<>();

            int index = mapRoute.size();
            CompareMapValue[] compareMapValues = new CompareMapValue[index];
            index = 0;
            for (Map.Entry<Map<Route, TotalCalculateRoute>, Double> entry : mapRoute.entrySet()) {
                compareMapValues[index++] = new CompareMapValue(entry.getKey(), entry.getValue());
            }
            Arrays.sort(compareMapValues);
            for (CompareMapValue cmv : compareMapValues) {
                mapYieldSort.put(cmv.route, cmv.yield);
            }
            rootMap.put(listOfWagons.get(i), mapYieldSort);
        }

        basicClassLookingForImpl.setTotal(rootMap);
        /*
        for (Map.Entry<String, Map<Route, Double>> map : rootMap.entrySet()) {

            basicClassLookingForImpl.getListOfDistributedRoutesAndWagons().add(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue(), nameOfStationDepartureOfWagon, tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination(), wagon.getCargo().trim(), getListOfDistance.getRootMapWithTypeOfCargo().get(wagon.getKeyItemCargo())));

        }


        basicClassLookingForImpl.getTotalMapWithWagonNumberAndRoute().put(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue()), tempMapOfRouteForDelete.get(j));


        // Заполняем мапы
        List<Wagon> copyListOfWagon = new ArrayList<>(tempListOfWagons);
        Map<Integer, Route> tempMapOfRoutes = new HashMap<>(mapOfRoutes);

        // Список расстояний
        Map<List<Object>, Integer> mapDistance = new HashMap<>();

        // Запускаем цикл
        Boolean isOk = true;
        while (isOk) {
            isOk = false;
            // Очищаем массивы
            mapDistance.clear();

            for (Wagon _copyListOfWagon : copyListOfWagon) {
                // Получаем код станции назначения вагона
                String keyOfStationOfWagonDestination = _copyListOfWagon.getKeyOfStationDestination().trim();

                // По каждому вагону высчитываем расстояние до каждой начальной станнции маршрутов
                // Цикл расчета расстояния и заполнения мапы
                for (Map.Entry<Integer, Route> _tempMapOfRoutes : tempMapOfRoutes.entrySet()) {

                    List<Object> list = new ArrayList<>();
                    String keyOfStationDeparture = _tempMapOfRoutes.getValue().getKeyOfStationDeparture();
                    String keyItemCargo = _copyListOfWagon.getKeyItemCargo();
                    list.add(_copyListOfWagon);
                    list.add(_tempMapOfRoutes.getValue());
                    String key = keyOfStationOfWagonDestination + "_" + keyOfStationDeparture;

                    // Ищем в готовой мапе расстояние
                    if (getListOfDistance.getRootMapWithDistances().containsKey(key)) {
                        if (getListOfDistance.getRootMapWithTypeOfCargo().containsKey(keyItemCargo)) {
                            if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 0) {
                                switch (getListOfDistance.getRootMapWithTypeOfCargo().get(keyItemCargo)) {
                                    case 3:
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 300) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                        break;
                                    default:
                                        if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 600) {
                                            mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                        }
                                        break;
                                }
                            } else if (getListOfDistance.getRootMapWithDistances().get(key).get(1) == 1) {
                                if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 2500) {
                                    mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                }
                            } else {
                                if (getListOfDistance.getRootMapWithDistances().get(key).get(0) <= 1800) {
                                    mapDistance.put(list, getListOfDistance.getRootMapWithDistances().get(key).get(0));
                                }
                            }
                        }
                    }
                }
            }

            // Сортируем мапу по значению
            Map<List<Object>, Integer> mapDistanceSort = new LinkedHashMap<>();

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

            // Цикл формирования рейсов
            // Проверяем на пустоту мап, либо вагоны, либо рейсы
            outer:
            if (!mapDistanceSort.isEmpty() && !copyListOfWagon.isEmpty()) {
                for (Map.Entry<List<Object>, Integer> mapDistanceSortFirstElement : mapDistanceSort.entrySet()) {
                    List<Object> listRouteMinDistance = mapDistanceSortFirstElement.getKey();
                    Route route = (Route) listRouteMinDistance.get(1);
                    Wagon wagon = (Wagon) listRouteMinDistance.get(0);
                    String nameOfStationDepartureOfWagon = route.getNameOfStationDeparture();

                    final int[] o = {0};
                    Map<Integer, Route> tempMapOfRouteForDelete = new HashMap<>();
                    tempMapOfRoutes.forEach((k, v) -> {
                        tempMapOfRouteForDelete.put(o[0], v);
                        o[0]++;
                    });

                    Iterator<Map.Entry<Integer, Route>> it = tempMapOfRoutes.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Integer, Route> entry = it.next();
                        for (int j = 0; j < tempMapOfRouteForDelete.size(); j++) {
                            // Находим маршрут для вагона
                            if (tempMapOfRouteForDelete.get(j).equals(route)) {
                                if (tempMapOfRouteForDelete.get(j).equals(entry.getValue())) {
                                    if (wagon.getVolume() >= entry.getValue().getVolumePeriod().getVolumeFrom() && wagon.getVolume() <= entry.getValue().getVolumePeriod().getVolumeTo()) {

                                        int getKeyNumber = 0;

                                        /*for (int i = 0; i < copyListOfWagon.size(); i++) {
                                            if (copyListOfWagon.get(i).getNumberOfWagon().equals(wagon.getNumberOfWagon())) {
                                                getKeyNumber = i;
                                            }
                                        }*/

                                        // Число дней пройденных вагоном
                                        //int countCircleDays = 0;
                                        //getFullMonthCircleOfWagonImpl.fullDays(copyListOfWagon.get(getKeyNumber).getWagonType().toString(), mapDistanceSortFirstElement.getValue(), route.getDistanceOfWay());

                                        // Удаляем вагон
                                        /*for (int i = 0; i < tempListOfWagons.size(); i++) {
                                            if (tempListOfWagons.get(i).getNumberOfWagon().equals(copyListOfWagon.get(getKeyNumber).getNumberOfWagon())) {
                                                tempListOfWagons.remove(i);
                                            }
                                        }*/

                                        //copyListOfWagon.remove(getKeyNumber);
                                        /*
                                        // Уменьшаем количество рейсов у маршрута
                                        tempMapOfRoutes.put(entry.getKey(), new Route(tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDeparture(),
                                                tempMapOfRoutes.get(entry.getKey()).getNameOfStationDeparture(),
                                                tempMapOfRoutes.get(entry.getKey()).getKeyOfStationDestination(),
                                                tempMapOfRoutes.get(entry.getKey()).getNameOfStationDestination(),
                                                tempMapOfRoutes.get(entry.getKey()).getDistanceOfWay(),
                                                tempMapOfRoutes.get(entry.getKey()).getCustomer(),
                                                tempMapOfRoutes.get(entry.getKey()).getVolumePeriod(),
                                                tempMapOfRoutes.get(entry.getKey()).getNumberOrder(),
                                                tempMapOfRoutes.get(entry.getKey()).getCargo(),
                                                tempMapOfRoutes.get(entry.getKey()).getWagonType()));


                                        // Удаляем маршрут, если по нему 0 рейсов
                                        if (tempMapOfRoutes.get(entry.getKey()).getCountOrders() == 0) {
                                            it.remove();
                                        }
                                         */
/*
                                        basicClassLookingFor.getListOfDistributedRoutesAndWagons().add(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue(), nameOfStationDepartureOfWagon, tempMapOfRouteForDelete.get(j).getNameOfStationDeparture() + " - " + tempMapOfRouteForDelete.get(j).getNameOfStationDestination(), wagon.getCargo().trim(), getListOfDistance.getRootMapWithTypeOfCargo().get(wagon.getKeyItemCargo())));
                                        basicClassLookingFor.getTotalMapWithWagonNumberAndRoute().put(new WagonFinalInfo(wagon.getNumberOfWagon(), countCircleDays, mapDistanceSortFirstElement.getValue()), tempMapOfRouteForDelete.get(j));

                                        isOk = true;
                                        // Выходим из цикла, так как с ним больше ничего не сделать
                                        break outer;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        */
        logger.info("Stop root method: {}", this.getClass().getSimpleName() + ".fillMapRouteIsOptimal");
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
