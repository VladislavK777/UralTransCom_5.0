package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.model_ext.WagonFinalInfo;
import com.uraltranscom.service.BasicClassLookingFor;
import com.uraltranscom.service.additional.FillMapsNotVipAndVip;
import com.uraltranscom.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Основной класс
 *
 * @author Vladislav Klochkov
 * @version 4.1
 * @create 01.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 *
 */

@Service
public class BasicClassLookingForImpl extends JavaHelperBase implements BasicClassLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassLookingForImpl.class);

    @Autowired
    private GetListOfDistanceImpl getListOfDistance;
    @Autowired
    private FillMapsNotVipAndVip fillMapsNotVipAndVip;
    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;

    // Мапа для записи в файл Вагона + Станция назначения.
    private Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute = new HashMap<>();

    // Итоговые массивы для вывода на страницу
    // Массив распределенных маршрутов и вагонов
    private List<String> listOfDistributedRoutesAndWagons = new ArrayList<>();

    // Массив нераспределенных маршрутов
    private List<String> listOfUndistributedRoutes = new ArrayList<>();

    // Массив нераспределенных вагонов
    private List<String> listOfUndistributedWagons = new ArrayList<>();

    // Массив ошибок
    private List<String> listOfError = new ArrayList<>();

    private BasicClassLookingForImpl() {
    }

    @Override
    public void fillMapRouteIsOptimal(String routeId) {
        // Очищаем массивы итоговые
        listOfDistributedRoutesAndWagons.clear();
        listOfUndistributedRoutes.clear();
        listOfUndistributedWagons.clear();
        listOfError.clear();

        // Запускаем метод заполненеия первоначальной мапы расстояний
        getListOfDistance.fillMap(routeId);

        // Заполняем мапы
        Map<Integer, Route> tempMapRoutesVip = fillMapsNotVipAndVip.getMapVIP();
        Map<Integer, Route> tempMapRoutesNotVip = fillMapsNotVipAndVip.getMapNotVIP();
        List<Wagon> tempListOfWagons = getListOfDistance.getListOfWagons();

        // Запускаем распределение для VIP
        if (!tempMapRoutesVip.isEmpty()) {
            classHandlerLookingFor.lookingForOptimalMapOfRoute(tempMapRoutesVip, tempListOfWagons);
        }

        // Запускаем распределение для неVIP
        if (!tempMapRoutesNotVip.isEmpty()) {
            classHandlerLookingFor.lookingForOptimalMapOfRoute(tempMapRoutesNotVip, tempListOfWagons);
        }

        for (int i = 0; i < tempListOfWagons.size(); i++) {
            listOfUndistributedWagons.add(tempListOfWagons.get(i).getNumberOfWagon());
        }

        // очищаем мапы
        tempListOfWagons.clear();
        tempMapRoutesVip.clear();
        tempMapRoutesNotVip.clear();
    }

    public Map<WagonFinalInfo, Route> getTotalMapWithWagonNumberAndRoute() {
        return totalMapWithWagonNumberAndRoute;
    }

    public void setTotalMapWithWagonNumberAndRoute(Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute) {
        this.totalMapWithWagonNumberAndRoute = totalMapWithWagonNumberAndRoute;
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
}