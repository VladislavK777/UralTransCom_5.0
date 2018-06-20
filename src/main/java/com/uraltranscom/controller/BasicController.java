package com.uraltranscom.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 5.0
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 * 09.04.2018
 *   1. Версия 4.2
 * 24.05.2018
 *   1. Версия 5.0
 *
 */

import com.uraltranscom.service.export.WriteToFileExcel;
import com.uraltranscom.service.impl.BasicClassLookingForImpl;
import com.uraltranscom.util.MultipartFileToFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BasicController {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public String reportList(@RequestParam(value = "routes", required = false) MultipartFile routeFile,
                             @RequestParam(value = "wagons", required = false) MultipartFile wagonFile, Model model) {
        if (routeFile != null || wagonFile != null) {
            basicClassLookingForImpl.getClassHandlerLookingFor().getGetListOfRoutesImpl().setFile(MultipartFileToFileUtil.multipartToFile(routeFile));
            basicClassLookingForImpl.getClassHandlerLookingFor().getGetListOfWagonsImpl().setFile(MultipartFileToFileUtil.multipartToFile(wagonFile));
            basicClassLookingForImpl.getClassHandlerLookingFor().setArrayNeedWashing();
            if (!(basicClassLookingForImpl.getClassHandlerLookingFor().getNeedWashingRoute().isEmpty()) ||
                    !(basicClassLookingForImpl.getClassHandlerLookingFor().getNeedWashingWagon().isEmpty())) {
                model.addAttribute("needWashingRoute", basicClassLookingForImpl.getClassHandlerLookingFor().getNeedWashingRoute());
                model.addAttribute("needWashingWagon", basicClassLookingForImpl.getClassHandlerLookingFor().getNeedWashingWagon());
                return "washing";
            } else {
                basicClassLookingForImpl.fillMapRouteIsOptimal();
                model.addAttribute("totalMap", basicClassLookingForImpl.getTotal());
                return "welcome";
            }
        } else {
            basicClassLookingForImpl.fillMapRouteIsOptimal();
            model.addAttribute("totalMap", basicClassLookingForImpl.getTotal());
            return "welcome";
        }
    }

    // Выгрузка в Excel общего очтета
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void getXLS(@RequestParam(value = "routeIds", defaultValue = "") String routeIds, HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileExcel(response, basicClassLookingForImpl.getTotal(), routeIds);
    }

    // Выгрузка в Excel отдельного расчета
    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public void getCalcXLS(@RequestParam(value = "nameFile") String nameFile, HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileCalcExcel(response, basicClassLookingForImpl.getTotal(), nameFile);
    }
}
