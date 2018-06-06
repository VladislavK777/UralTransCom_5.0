package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.model_ext.TotalCalculateRoute;
import com.uraltranscom.model_ext.WagonFinalInfo;
import com.uraltranscom.service.BasicClassLookingFor;
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
 * Implementation for {@link BasicClassLookingFor} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 01.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 03.04.2018
 *   1. Версия 4.1
 * 09.04.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class BasicClassLookingForImpl extends JavaHelperBase implements BasicClassLookingFor {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassLookingForImpl.class);

    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;

    // Мапа для записи в файл Вагона + Станция назначения.
    private Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute = new HashMap<>();

    // Массив распределенных маршрутов и вагонов
    private List<WagonFinalInfo> listOfDistributedRoutesAndWagons = new ArrayList<>();

    private Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> total = new HashMap<>();

    // Массив ошибок
    private List<String> listOfError = new ArrayList<>();

    private BasicClassLookingForImpl() {
    }

    @Override
    public void fillMapRouteIsOptimal() {
        // Очищаем массивы итоговые
        totalMapWithWagonNumberAndRoute.clear();
        listOfDistributedRoutesAndWagons.clear();
        listOfError.clear();
        total.clear();

        classHandlerLookingFor.lookingForOptimalMapOfRoute();
    }

    public Map<WagonFinalInfo, Route> getTotalMapWithWagonNumberAndRoute() {
        return totalMapWithWagonNumberAndRoute;
    }

    public void setTotalMapWithWagonNumberAndRoute(Map<WagonFinalInfo, Route> totalMapWithWagonNumberAndRoute) {
        this.totalMapWithWagonNumberAndRoute = totalMapWithWagonNumberAndRoute;
    }

    public List<WagonFinalInfo> getListOfDistributedRoutesAndWagons() {
        return listOfDistributedRoutesAndWagons;
    }

    public void setListOfDistributedRoutesAndWagons(List<WagonFinalInfo> listOfDistributedRoutesAndWagons) {
        this.listOfDistributedRoutesAndWagons = listOfDistributedRoutesAndWagons;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public void setListOfError(List<String> listOfError) {
        this.listOfError = listOfError;
    }

    public Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> getTotal() {
        return total;
    }

    public void setTotal(Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> total) {
        this.total = total;
    }

    public ClassHandlerLookingForImpl getClassHandlerLookingFor() {
        return classHandlerLookingFor;
    }

    public void setClassHandlerLookingFor(ClassHandlerLookingForImpl classHandlerLookingFor) {
        this.classHandlerLookingFor = classHandlerLookingFor;
    }
}