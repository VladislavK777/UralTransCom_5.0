package com.uraltranscom.service.export;

/*
 *
 * Класс записи в файл Excel
 *
 * @author Vladislav Klochkov
 * @version 3.0
 * @create 09.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 *
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteToFileExcel {

    private WriteToFileExcel() {
    }

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WriteToFileExcel.class);

    // Успешная выгрузка
    private static boolean isOk = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static XSSFWorkbook xssfWorkbook;

    public static void downloadFileExcel(HttpServletResponse response, List<String>... listOfFinal) {
        try {
            xssfWorkbook = new XSSFWorkbook();
            xssfWorkbook.createSheet("Распределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные рейсы");
            xssfWorkbook.createSheet("Нераспределенные вагоны");
            xssfWorkbook.createSheet("Ошибки");

            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, listOfFinal);

            xssfWorkbook.close();
            isOk = true;
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    // Метод записи в файл
    public static synchronized void writeToFileExcel(HttpServletResponse response, List<String>... listOfFinalArray) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                XSSFRow row;
                Cell cell;

                // Заполняем данными
                for (String list : listOfFinalArray[i]) {
                    int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                    row = sheet.createRow(rowCount + 1);
                    cell = row.createCell(0);
                    cell.setCellValue(list);
                }
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

/*    // Метод записи в файл нераспределенных маршрутов
    public static synchronized void writeToFileExcelUnDistributedRoutes(HttpServletResponse response, List<String> listOfUnDistributedRoutes) {
        try {

            XSSFSheet sheet = xssfWorkbook.getSheetAt(1);
            XSSFRow rowHead = sheet.createRow(0);
            XSSFRow row;
            Cell cell;

            // Заголовки столбцов
            cell = rowHead.createCell(0, CellType.STRING);
            cell.setCellValue("Маршрут");

            // Заполняем данными
            for (String unDistributedRoute : listOfUnDistributedRoutes) {
                int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                row = sheet.createRow(rowCount + 1);
                cell = row.createCell(0);
                cell.setCellValue(unDistributedRoute);
            }

            ServletOutputStream outputStream = response.getOutputStream();
            sheet.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    // Метод записи в файл нераспределенных вагонов
    public static synchronized void writeToFileExcelUnDistributedWagons(HttpServletResponse response, List<String> listOfUnDistributedWagons) {
        try {

            XSSFSheet sheet = xssfWorkbook.getSheetAt(2);
            XSSFRow rowHead = sheet.createRow(0);
            XSSFRow row;
            Cell cell;

            // Заголовки столбцов
            cell = rowHead.createCell(0, CellType.STRING);
            cell.setCellValue("Номер вагона");

            // Заполняем данными
            for (String unDistributedWagon : listOfUnDistributedWagons) {
                int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                row = sheet.createRow(rowCount + 1);
                cell = row.createCell(0);
                cell.setCellValue(unDistributedWagon);
            }

            ServletOutputStream outputStream = response.getOutputStream();
            sheet.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    // Метод записи в файл ошибок
    public static synchronized void writeToFileExcelError(HttpServletResponse response, List<String> listOfError) {
        try {

            XSSFSheet sheet = xssfWorkbook.getSheetAt(3);
            XSSFRow rowHead = sheet.createRow(0);
            XSSFRow row;
            Cell cell;

            // Заголовки столбцов
            cell = rowHead.createCell(0, CellType.STRING);
            cell.setCellValue("Ошибки");

            // Заполняем данными
            for (String error : listOfError) {
                int rowCount = sheet.getPhysicalNumberOfRows() - 1;
                row = sheet.createRow(rowCount + 1);
                cell = row.createCell(0);
                cell.setCellValue(error);
            }

            ServletOutputStream outputStream = response.getOutputStream();
            sheet.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }*/

    public static boolean isIsOk() {
        return isOk;
    }

    public static void setIsOk(boolean isOk) {
        WriteToFileExcel.isOk = isOk;
    }
}
