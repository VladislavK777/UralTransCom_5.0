package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.GetListOfDistance;
import com.uraltranscom.service.additional.FillMapsNotVipAndVip;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Класс получения списка первоначальных расстояний
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 14.03.2018
 *
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

@Service
public class GetListOfDistanceImpl extends JavaHelperBase implements GetListOfDistance {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfDistanceImpl.class);

    @Autowired
    private GetListOfRoutesImpl getListOfRoutesImpl;
    @Autowired
    private GetDistanceBetweenStationsImpl getDistanceBetweenStations;
    @Autowired
    private GetListOfWagonsImpl getListOfWagonsImpl;
    @Autowired
    private CheckExistKeyOfStationImpl checkExistKeyOfStationImpl;
    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;
    @Autowired
    private FillMapsNotVipAndVip fillMapsNotVipAndVip;

    // Основная мапа
    private static Map<String, Integer> rootMapWithDistances = new HashMap<>();

    // Мапа с расстояниями больше максимального значения
    private static Map<String, Integer> rootMapWithDistanceMoreMaxDist = new HashMap<>();

    // Заполненные мапы Вагонов и Маршрутов
    private Map<Integer, Route> mapOfRoutes = new HashMap<>();
    private List<Wagon> listOfWagons = new ArrayList<>();

    @Override
    public void fillMap(String routeId) {
        logger.info("Start process fill map with distances");

        mapOfRoutes = getListOfRoutesImpl.getMapOfRoutes();
        listOfWagons = getListOfWagonsImpl.getListOfWagons();

        if (!routeId.isEmpty()) {
            String[] routesId = routeId.split(",");
            for (Map.Entry<Integer, Route> _mapOfRoutes : mapOfRoutes.entrySet()) {
                for (String _routesId : routesId) {
                    if (_mapOfRoutes.getKey() == Integer.parseInt(_routesId)) {
                        mapOfRoutes.get(Integer.parseInt(_routesId)).setVIP("1");
                    }
                }
            }
        }

        Iterator<Map.Entry<Integer, Route>> iterator = mapOfRoutes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            for (int i = 0; i < listOfWagons.size(); i++) {
                String stationCode1 = listOfWagons.get(i).getKeyOfStationDestination();
                String stationCode2 = entry.getValue().getKeyOfStationDeparture();
                String key = stationCode1 + "_" + stationCode2;
                if (!rootMapWithDistances.containsKey(key)) {
                    if (!rootMapWithDistanceMoreMaxDist.containsKey(key)) {
                        int distance = getDistanceBetweenStations.getDistanceBetweenStations(stationCode1, stationCode2);
                        if (distance != -1) {
                            if (distance != -20000) {
                                rootMapWithDistances.put(key, distance);
                            } else {
                                rootMapWithDistanceMoreMaxDist.put(key, distance);
                            }
                        } else {
                            if (!checkExistKeyOfStationImpl.checkExistKey(stationCode2)) {
                                basicClassLookingForImpl.getListOfError().add("Проверьте код станции " + entry.getValue().getKeyOfStationDeparture());
                                logger.error("Проверьте код станции " + entry.getValue().getKeyOfStationDeparture());
                                basicClassLookingForImpl.getListOfUndistributedRoutes().add(entry.getValue().getNameOfStationDeparture() + " - " +
                                        entry.getValue().getNameOfStationDestination() + ". Оставшиеся количество рейсов: " + entry.getValue().getCountOrders());
                                iterator.remove();
                                break;
                            }
                            if (!checkExistKeyOfStationImpl.checkExistKey(stationCode1)) {
                                basicClassLookingForImpl.getListOfError().add("Проверьте код станции " + listOfWagons.get(i).getKeyOfStationDestination());
                                logger.error("Проверьте код станции {}", listOfWagons.get(i).getKeyOfStationDestination());
                                basicClassLookingForImpl.getListOfUndistributedWagons().add(listOfWagons.get(i).getNumberOfWagon());
                                listOfWagons.remove(i);
                                break;

                            }
                        }
                    }
                }
            }
        }

        try {
            fillMapsNotVipAndVip.separateMaps(mapOfRoutes);
        } catch (NullPointerException e) {
            logger.error("Map must not empty");
        }
        logger.info("Stop process fill map with distances");
    }

    public Map<String, Integer> getRootMapWithDistances() {
        return rootMapWithDistances;
    }

    public void setRootMapWithDistances(Map<String, Integer> rootMapWithDistances) {
        GetListOfDistanceImpl.rootMapWithDistances = rootMapWithDistances;
    }

    public Map<String, Integer> getRootMapWithDistanceMoreMaxDist() {
        return rootMapWithDistanceMoreMaxDist;
    }

    public void setRootMapWithDistanceMoreMaxDist(Map<String, Integer> rootMapWithDistanceMoreMaxDist) {
        GetListOfDistanceImpl.rootMapWithDistanceMoreMaxDist = rootMapWithDistanceMoreMaxDist;
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public List<Wagon> getListOfWagons() {
        return listOfWagons;
    }

    public void setListOfWagons(List<Wagon> listOfWagons) {
        this.listOfWagons = listOfWagons;
    }

    /*public static void setConnection(Connection connection) {
        GetListOfDistanceImpl.connection = connection;
    }*/

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
