package com.uraltranscom.service.impl;

import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.GetList;
import com.uraltranscom.service.export.WriteToFileExcel;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс получения списка вагонов
 * Implementation for {@link GetList} interface
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 25.10.2017
 *
 * 06.11.2017
 *   1. Добавлено внесение в Map название ЖД, для более детального поиска номера станции
 * 10.11.2017
 *   1. Переделано получения целого числа в поле Номер вагона
 * 17.11.2017
 *   1. Изменен метод заполнения Map
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 19.04.2018
 *   1. Версия 4.1
 * 03.05.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

@Service
public class GetListOfWagonsImpl implements GetList {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(GetListOfWagonsImpl.class);

    // Основаная мапа, куда записываем все вагоны
    private List<Wagon> listOfWagons = new ArrayList<>();

    // Переменные для работы с файлами
    private File file ;
    private FileInputStream fileInputStream;

    // Переменные для работы с Excel файлом(формат XLSX)
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    @Autowired
    private WriteToFileExcel writeToFileExcel;

    @Autowired
    private PropertyUtil propertyUtil;

    private GetListOfWagonsImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, отвязать от количества строк, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
        listOfWagons.clear();
        writeToFileExcel.setFile(null);
        writeToFileExcel.setFile(file);

        // Получаем файл формата xls
        try {
            fileInputStream = new FileInputStream(this.file);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            // Заполняем мапу данными
            sheet = xssfWorkbook.getSheetAt(0);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                XSSFRow row = sheet.getRow(0);

                String numberOfWagon = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;
                String roadOfStationDestination = null;
                String nameOfStationDeparture = null;
                String roadOfStationDeparture = null;
                int volume = 0;
                String cargo = null;
                String keyItemCargo = null;
                Double rate = 0.00d;
                String keyOfStationDep = null;
                String customer = null;


                for (int c = 0; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.numberwagon"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        numberOfWagon = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.namestationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keystationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.roadstationdestination"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.namestationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.roadstationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        roadOfStationDeparture = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.volume"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        volume = (int) xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.cargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        cargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keyitemcargo"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyItemCargo = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.rate"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        rate = xssfRow.getCell(c).getNumericCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.keystationdeparture"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDep = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals(propertyUtil.getProperty("wagon.customer"))) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        customer = xssfRow.getCell(c).getStringCellValue();
                    }

                }
                listOfWagons.add(new Wagon(numberOfWagon, keyOfStationDestination, nameOfStationDestination, roadOfStationDestination, nameOfStationDeparture, roadOfStationDeparture, volume, cargo, keyItemCargo, rate, keyOfStationDep, customer));
            }
            logger.debug("Body wagon: {}", listOfWagons);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла дислокации, необходим формат xlsx");
        }

    }

    protected void replaceListOfWagon(List<Wagon> listOfWagonsAfterGetDistance) {
        listOfWagons.clear();
        listOfWagons.addAll(listOfWagonsAfterGetDistance);
    }

    public List<Wagon> getListOfWagons() {
        return listOfWagons;
    }

    public void setListOfWagons(List<Wagon> listOfWagons) {
        this.listOfWagons = listOfWagons;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        fillMap();
    }
}
