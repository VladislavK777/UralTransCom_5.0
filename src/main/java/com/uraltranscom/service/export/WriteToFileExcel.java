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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private static XSSFCellStyle cellStyle(XSSFSheet sheet) {
        Font fontTitle = sheet.getWorkbook().createFont();
        fontTitle.setColor(HSSFColor.BLACK.index);
        XSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(fontTitle);
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
