package com.uraltranscom.service.impl;

import com.uraltranscom.model.Route;
import com.uraltranscom.service.GetList;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.util.PropertyUtil;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Класс получения списка маршрутов
 * Implementation for {@link GetList} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 25.10.2017
 *
 * 17.11.2017
 *   1. Изменен метод заполнения Map
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 * 24.04.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetListOfRoutesImpl extends JavaHelperBase implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfRoutesImpl.class);

    // Основаная мапа, куда записываем все маршруты
    private Map<Integer, Route> mapOfRoutes = new HashMap<>();

    // Переменные для работы с файлами
    private File file;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    @Autowired
    private PropertyUtil propertyUtil;

    private GetListOfRoutesImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
        mapOfRoutes.clear();
        // Получаем файл формата xls
        try {
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем Map данными
            sheet = xssfWorkbook.getSheetAt(0);
            int i = 0;
            for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(1);

                String keyOfStationDeparture = null;
                String nameOfStationDeparture = null;
                String roadOfStationDeparture = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;
                String roadOfStationDestination = null;
                String distanceOfWay = null;
                String customer = null;
                int volumeFrom = 0;
                int volumeTo = 0;
                String numberOrder = null;
                String nameCargo = null;
                String keyCargo = null;
                String wagonType = null;
                Double rate = 0.00;

                for (int c = 1; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.keystationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.namestationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.roadstationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.keystationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.namestationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.roadstationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.distanceway"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        String val = Double.toString(xssfRow.getCell(c).getNumericCellValue());
                        double valueDouble = xssfRow.getCell(c).getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        distanceOfWay = val;
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.customer"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.volumefrom"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeFrom = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.volumeto"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volumeTo = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.wagontype"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        wagonType = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.numberorder"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        numberOrder = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.namecargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.keycargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("route.rate"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        rate = xssfRow.getCell(c).getNumericCellValue();
                    }
                }
                if (wagonType.equals(TYPE_OF_WAGON_KR)) {
                    mapOfRoutes.put(i, new Route(keyOfStationDeparture, nameOfStationDeparture, roadOfStationDeparture, keyOfStationDestination, nameOfStationDestination, roadOfStationDestination, distanceOfWay, customer, volumeFrom, volumeTo, numberOrder, nameCargo, keyCargo, wagonType, rate));
                    i++;
                }
            }
            logger.debug("Body route: {}", mapOfRoutes);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
    }

    public Map<Integer, Route> getMapOfRoutes() {
        return mapOfRoutes;
    }

    public void setMapOfRoutes(Map<Integer, Route> mapOfRoutes) {
        this.mapOfRoutes = mapOfRoutes;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}
