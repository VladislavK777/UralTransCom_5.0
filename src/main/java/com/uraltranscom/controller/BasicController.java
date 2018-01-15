package com.uraltranscom.controller;

/*
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 01.11.2017
 *
 * 15.01.2018
 * Версия 2.0
 *
 */

import com.uraltranscom.service.MethodOfBasicLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SuppressWarnings("ALL")
@Controller
public class BasicController {

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private MethodOfBasicLogic methodOfBasicLogic;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String reportList(Model model) {
        methodOfBasicLogic.lookingForOptimalMapOfRoute();
        model.addAttribute("reportListOfDistributedRoutesAndWagons", methodOfBasicLogic.getListOfDistributedRoutesAndWagons());
        model.addAttribute("reportListOfDistributedRoutes", methodOfBasicLogic.getListOfUndistributedRoutes());
        model.addAttribute("reportListOfDistributedWagons", methodOfBasicLogic.getListOfUndistributedWagons());
        model.addAttribute("reportListOfError", methodOfBasicLogic.getListOfError());
        return "reports";
    }
}
