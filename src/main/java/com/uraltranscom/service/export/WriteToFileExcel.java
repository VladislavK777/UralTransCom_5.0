package com.uraltranscom.service.export;

/**
 *
 * Класс записи в файл Excel
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 09.11.2017
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 25.04.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

import com.uraltranscom.model.Route;
import com.uraltranscom.model.Wagon;
import com.uraltranscom.model_ext.TotalCalculateRoute;
import com.uraltranscom.service.additional.JavaHelperBase;
import com.uraltranscom.service.additional.PrefixOfDays;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class WriteToFileExcel extends JavaHelperBase {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(WriteToFileExcel.class);

    // Успешная выгрузка
    private static boolean isOk = false;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static File file;
    private static Map<String, List<String>> mapStationsAndCustomers = new HashMap<>();

    public WriteToFileExcel() {
    }

    public static void downloadFileExcel(HttpServletResponse response, Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String routeIds) {
        try {
            String fileName = "Report_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileExcel(response, map, routeIds);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static void downloadFileCalcExcel(HttpServletResponse response, Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String nameFile) {
        try {
            String fileName = "Calculate_" + nameFile + "_" + ".xlsx";
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            writeToFileCalcExcel(response, map, nameFile);

            isOk = true;
        } catch (Exception e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
            isOk = false;
        }

    }

    public static synchronized void writeToFileCalcExcel(HttpServletResponse response, Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String nameFile) {
        try {
            TotalCalculateRoute totalCalculateRoute = getRouteForCalc(map, nameFile);
            logger.debug("totalCalculateRoute: {}", totalCalculateRoute.toString());

            ClassLoader classLoader = WriteToFileExcel.class.getClassLoader();
            File fileCalc = new File(classLoader.getResource("calculation.xlsx").getFile());

            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileCalc))) {

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

                // Строка текущего рейса вагона
                XSSFRow row4 = sheet.getRow(4);
                Cell currentCell4_0 = row4.createCell(0);
                currentCell4_0.setCellValue(totalCalculateRoute.getCurrentNameStationDeparture() + " (" + totalCalculateRoute.getCurrentRoadStationDeparture() + ")");
                currentCell4_0.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_1 = row4.createCell(1);
                currentCell4_1.setCellValue(totalCalculateRoute.getCurrentNameStationDestination() + " (" + totalCalculateRoute.getCurrentRoadStationDestination() + ")");
                currentCell4_1.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_2 = row4.createCell(2);
                currentCell4_2.setCellValue(totalCalculateRoute.getCurrentCargo());
                currentCell4_2.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_3 = row4.createCell(3);
                currentCell4_3.setCellValue(Double.valueOf(totalCalculateRoute.getCurrentDistance()));
                currentCell4_3.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_4 = row4.createCell(4);
                currentCell4_4.setCellFormula("ROUNDUP(IF(D5<2500,D5/200,IF(AND(D5>2499,D5<3000),D5/270,D5/300)),0)");
                currentCell4_4.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_5 = row4.createCell(5);
                currentCell4_5.setCellValue(totalCalculateRoute.getFirstLoadingWagon());
                currentCell4_5.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_6 = row4.createCell(6);
                currentCell4_6.setCellFormula("E5+F5");
                currentCell4_6.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_8 = row4.createCell(8);
                currentCell4_8.setCellValue(totalCalculateRoute.getCurrentRate());
                currentCell4_8.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell4_10 = row4.createCell(10);
                currentCell4_10.setCellFormula("I5*1");
                currentCell4_10.setCellStyle(cellStyleForCacl(sheet));

                // Строка первого порожняка
                XSSFRow row5 = sheet.getRow(5);
                Cell currentCell5_0 = row5.createCell(0);
                currentCell5_0.setCellValue(totalCalculateRoute.getEmptyNameStationDeparture() + " (" + totalCalculateRoute.getEmptyRoadStationDeparture() + ")");
                currentCell5_0.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_1 = row5.createCell(1);
                currentCell5_1.setCellValue(totalCalculateRoute.getEmptyNameStationDestination() + " (" + totalCalculateRoute.getEmptyRoadStationDestination() + ")");
                currentCell5_1.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_2 = row5.createCell(2);
                currentCell5_2.setCellValue(totalCalculateRoute.getEmptyCargo());
                currentCell5_2.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_3 = row5.createCell(3);
                currentCell5_3.setCellValue(Double.valueOf(totalCalculateRoute.getEmptyDistance()));
                currentCell5_3.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_4 = row5.createCell(4);
                currentCell5_4.setCellFormula("ROUNDUP(IF(D6<2500,D6/200,IF(AND(D6>2499,D6<3000),D6/270,D6/300)),0)");
                currentCell5_4.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_5 = row5.createCell(5);
                currentCell5_5.setCellValue(totalCalculateRoute.getUnloadingWagon());
                currentCell5_5.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_6 = row5.createCell(6);
                currentCell5_6.setCellFormula("E6+F6");
                currentCell5_6.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_9 = row5.createCell(9);
                currentCell5_9.setCellValue(totalCalculateRoute.getEmptyTariff());
                currentCell5_9.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell5_10 = row5.createCell(10);
                currentCell5_10.setCellFormula("J6*(-1)");
                currentCell5_10.setCellStyle(cellStyleForCacl(sheet));

                // Строка второго рейса
                XSSFRow row6 = sheet.getRow(6);
                Cell currentCell6_0 = row6.createCell(0);
                currentCell6_0.setCellValue(totalCalculateRoute.getSecondNameStationDeparture() + " (" + totalCalculateRoute.getSecondRoadStationDeparture() + ")");
                currentCell6_0.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_1 = row6.createCell(1);
                currentCell6_1.setCellValue(totalCalculateRoute.getSecondNameStationDestination() + " (" + totalCalculateRoute.getSecondRoadStationDestination() + ")");
                currentCell6_1.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_2 = row6.createCell(2);
                currentCell6_2.setCellValue(totalCalculateRoute.getSecondCargo());
                currentCell6_2.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_3 = row6.createCell(3);
                currentCell6_3.setCellValue(Double.valueOf(totalCalculateRoute.getSecondDistance()));
                currentCell6_3.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_4 = row6.createCell(4);
                currentCell6_4.setCellFormula("ROUNDUP(IF(D7<2500,D7/200,IF(AND(D7>2499,D7<3000),D7/270,D7/300)),0)");
                currentCell6_4.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_5 = row6.createCell(5);
                currentCell6_5.setCellValue(totalCalculateRoute.getSecondLoadingWagon());
                currentCell6_5.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_6 = row6.createCell(6);
                currentCell6_6.setCellFormula("E7+F7");
                currentCell6_6.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_8 = row6.createCell(8);
                currentCell6_8.setCellValue(totalCalculateRoute.getSecondRate());
                currentCell6_8.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell6_10 = row6.createCell(10);
                currentCell6_10.setCellFormula("I7*1");
                currentCell6_10.setCellStyle(cellStyleForCacl(sheet));

                // Строка второго порожняка
                XSSFRow row7 = sheet.getRow(7);
                Cell currentCell7_0 = row7.createCell(0);
                currentCell7_0.setCellValue(totalCalculateRoute.getEmptySecondNameStationDeparture() + " (" + totalCalculateRoute.getEmptySecondRoadStationDeparture() + ")");
                currentCell7_0.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_1 = row7.createCell(1);
                currentCell7_1.setCellValue(totalCalculateRoute.getEmptySecondNameStationDestination() + " (" + totalCalculateRoute.getEmptySecondRoadStationDestination() + ")");
                currentCell7_1.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_2 = row7.createCell(2);
                currentCell7_2.setCellValue(totalCalculateRoute.getEmptySecondCargo());
                currentCell7_2.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_3 = row7.createCell(3);
                currentCell7_3.setCellValue(Double.valueOf(totalCalculateRoute.getEmptySecondDistance()));
                currentCell7_3.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_4 = row7.createCell(4);
                currentCell7_4.setCellFormula("ROUNDUP(IF(D8<2500,D8/200,IF(AND(D8>2499,D8<3000),D8/270,D8/300)),0)");
                currentCell7_4.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_5 = row7.createCell(5);
                currentCell7_5.setCellValue(totalCalculateRoute.getUnloadingWagon());
                currentCell7_5.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_6 = row7.createCell(6);
                currentCell7_6.setCellFormula("E8+F8");
                currentCell7_6.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_9 = row7.createCell(9);
                currentCell7_9.setCellValue(totalCalculateRoute.getEmptySecondTariff());
                currentCell7_9.setCellStyle(cellStyleForCacl(sheet));
                Cell currentCell7_10 = row7.createCell(10);
                currentCell7_10.setCellFormula("J8*(-1)");
                currentCell7_10.setCellStyle(cellStyleForCacl(sheet));

                // Строка итоговых расчетов
                XSSFRow row8 = sheet.getRow(8);
                Cell currentCell8_3 = row8.createCell(3);
                currentCell8_3.setCellFormula("SUM(D5:D8)");
                currentCell8_3.setCellStyle(cellStyleForTotalCacl(sheet));
                Cell currentCell8_4 = row8.createCell(4);
                currentCell8_4.setCellFormula("SUM(E5:E8)");
                currentCell8_4.setCellStyle(cellStyleForTotalCacl(sheet));
                Cell currentCell8_5 = row8.createCell(5);
                currentCell8_5.setCellFormula("SUM(F5:F8)");
                currentCell8_5.setCellStyle(cellStyleForTotalCacl(sheet));
                Cell currentCell8_6 = row8.createCell(6);
                currentCell8_6.setCellFormula("SUM(G5:G8)");
                currentCell8_6.setCellStyle(cellStyleForTotalCacl(sheet));
                Cell currentCell8_10 = row8.createCell(10);
                currentCell8_10.setCellFormula("SUM(K5:K8)");
                currentCell8_10.setCellStyle(cellStyleForTotalCacl(sheet));
                Cell currentCell8_11 = row8.createCell(11);
                currentCell8_11.setCellFormula("SUM(K9/G9)");
                currentCell8_11.setCellStyle(cellStyleForTotalYieldCacl(sheet));

                xssfWorkbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    public static synchronized void writeToFileExcel(HttpServletResponse response, Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String routeIds) {
        try {
            getStringParamForExport(map, routeIds);
            ServletOutputStream outputStream = response.getOutputStream();

            try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file))) {

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    XSSFRow row = sheet.getRow(0);
                    for (int c = 0; c < row.getLastCellNum(); c++) {
                        if (row.getCell(c).getStringCellValue().trim().equals("Номер вагона")) {
                            XSSFRow xssfRow = sheet.getRow(j);
                            String val = xssfRow.getCell(c).getStringCellValue();
                            for (Map.Entry<String, List<String>> mapForAdd : mapStationsAndCustomers.entrySet()) {
                                if (val.equals(mapForAdd.getKey())) {
                                    for (int q = 0; q < row.getLastCellNum(); q++) {
                                        if (row.getCell(q).getStringCellValue().trim().equals("Клиент Следующее задание")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(mapForAdd.getValue().get(0));
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                        if (row.getCell(q).getStringCellValue().trim().equals("Станция погрузки запланированная")) {
                                            Cell cell = xssfRow.createCell(q);
                                            cell.setCellValue(mapForAdd.getValue().get(1));
                                            cell.setCellStyle(cellStyle(sheet));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл - {}", e.getMessage());
        }
    }

    private static String buildText(int dist, int countCircle) {
        if (countCircle < MAX_FULL_CIRCLE_DAYS ) {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle);
        } else {
            return dist + " км./" + countCircle + " " + PrefixOfDays.parsePrefixOfDays(countCircle) + "(превышение!)";
        }
    }

    public static void getStringParamForExport(Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String routeIds) {
        mapStationsAndCustomers.clear();
        for (Map.Entry<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> mapForAdd : map.entrySet()) {
            StringBuilder stringBuilderCustomers = new StringBuilder();
            StringBuilder stringBuilderStations = new StringBuilder();
            List<String> listStationsAndCustomers = new ArrayList<>();
            List<String> listStations = new ArrayList<>();
            List<String> listCustomers = new ArrayList<>();
            List<String> listRouteId = new ArrayList<>();
            for (Map.Entry<Map<Route, TotalCalculateRoute>, Double> mapValue2 : mapForAdd.getValue().entrySet()) {
                for (Map.Entry<Route, TotalCalculateRoute> mapRoutes : mapValue2.getKey().entrySet()) {
                    listStations.add(mapRoutes.getKey().getNameOfStationDeparture());
                    listCustomers.add(mapRoutes.getKey().getCustomer());
                    listRouteId.add(mapRoutes.getKey().getNumberOrder());
                }
            }

            for (int i = 0; i < 3; i++) {
                if (!routeIds.isEmpty()) {
                    String[] routeAndWagonIds = routeIds.split(",");
                    List listRouteIds = new ArrayList();
                    for (String _routeAndWagonIds : routeAndWagonIds) {
                        if (_routeAndWagonIds.contains(listRouteId.get(i) + "_" + mapForAdd.getKey().getNumberOfWagon())) {
                            String[] routeAndWagonId = _routeAndWagonIds.split("_");
                            listRouteIds.add(routeAndWagonId[0]);
                        }
                    }
                    if (!listRouteIds.contains(listRouteId.get(i))) {
                        stringBuilderCustomers.append(listCustomers.get(i));
                        stringBuilderStations.append(listStations.get(i));
                        if (i < 2) {
                            stringBuilderCustomers.append("/");
                            stringBuilderStations.append("/");
                        }
                    }
                } else {
                    stringBuilderCustomers.append(listCustomers.get(i));
                    stringBuilderStations.append(listStations.get(i));
                    if (i < 2) {
                        stringBuilderCustomers.append("/");
                        stringBuilderStations.append("/");
                    }
                }
            }
            listStationsAndCustomers.add(stringBuilderCustomers.toString());
            listStationsAndCustomers.add(stringBuilderStations.toString());
            mapStationsAndCustomers.put(mapForAdd.getKey().getNumberOfWagon(), listStationsAndCustomers);
        }
    }

    public static TotalCalculateRoute getRouteForCalc(Map<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> map, String nameFile) {
        for (Map.Entry<Wagon, Map<Map<Route, TotalCalculateRoute>, Double>> mapForAdd : map.entrySet()) {
            String[] routeAndWagonId = nameFile.split("_");
            if (mapForAdd.getKey().getNumberOfWagon().equals(routeAndWagonId[1])) {
                for (Map.Entry<Map<Route, TotalCalculateRoute>, Double> mapValue2 : mapForAdd.getValue().entrySet()) {
                    for (Map.Entry<Route, TotalCalculateRoute> mapRoutes : mapValue2.getKey().entrySet()) {
                        if (mapRoutes.getKey().getNumberOrder().equals(routeAndWagonId[0])) {
                            return mapRoutes.getValue();
                        }
                    }
                }
            }
        }
        return null;
    }

    private static XSSFCellStyle cellStyle(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleForCacl(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        ((XSSFFont) fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleForTotalCacl(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        ((XSSFFont) fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private static XSSFCellStyle cellStyleForTotalYieldCacl(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        fontTitle.setFontName("Calibri Light");
        ((XSSFFont) fontTitle).setFontHeight(14.0);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }



    public static boolean isIsOk() {
        return isOk;
    }

    public static void setIsOk(boolean isOk) {
        WriteToFileExcel.isOk = isOk;
    }

    public static void setFile(File file) {
        WriteToFileExcel.file = file;
    }
}
