package com.uraltranscom.service.impl;

import com.uraltranscom.model.Wagon;
import com.uraltranscom.service.GetListOfWagons;
import com.uraltranscom.service.export.WriteToFileExcel;
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
 *
 * @author Vladislav Klochkov
 * @version 4.0
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
 *
 */

@Service
public class GetListOfWagonsImpl implements GetListOfWagons {
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

    private GetListOfWagonsImpl() {
    }

    // Заполняем Map вагонами
    // TODO Переписать метод, отвязать от количества строк, избавиться от формата жесткого, необходимо и XLSX и XLS
    @Override
    public void fillMap() {
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
                String typeOfWagon = null;
                String keyOfStationDestination = null;
                String nameOfStationDestination = null;

                for (int c = 0; c < row.getLastCellNum(); c++) {
                    if (row.getCell(c).getStringCellValue().trim().equals("Вагон №")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        String val = Double.toString(xssfRow.getCell(c).getNumericCellValue());
                        double valueDouble = xssfRow.getCell(c).getNumericCellValue();
                        if ((valueDouble - (int) valueDouble) * 1000 == 0) {
                            val = (int) valueDouble + "";
                        }
                        numberOfWagon = val;
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Тип вагона")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        typeOfWagon = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Станция назначения")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        nameOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                    if (row.getCell(c).getStringCellValue().trim().equals("Код станции назначения")) {
                        XSSFRow xssfRow = sheet.getRow(j);
                        keyOfStationDestination = xssfRow.getCell(c).getStringCellValue();
                    }
                }
                listOfWagons.add(new Wagon(numberOfWagon, typeOfWagon, keyOfStationDestination, nameOfStationDestination));
            }
            logger.debug("Body wagon: {}", listOfWagons);
        } catch (IOException e) {
            logger.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            logger.error("Некорректный формат файла, необходим формат xlsx");
        }

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
