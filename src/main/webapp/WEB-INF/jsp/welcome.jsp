<%--
  Created by IntelliJ IDEA.
  User: Vladislav.Klochkov
  Date: 15.01.2018
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UralTransCom</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>

    <!-- Скрипт всплывающего окна -->
    <script type="text/javascript">
        $(document).ready(function () {
            $(popup_bg).click(function () {
                $(popup).fadeOut(800);
            });
        });
        function showPopup() {
            $(popup).fadeIn(800);
        }
    </script>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>

    <!-- Блокировка экрана -->
    <script type="text/javascript">
        function lockScreen() {
            var lock = document.getElementById('lockPane');
            if (lock)
                lock.className = 'lockScreenOn';
                $('body').addClass('stop-scrolling');
                document.body.scrollTop = document.documentElement.scrollTop = 0;
        }
    </script>

    <style>
        body {
            font: 16px "Calibri Light", sans-serif;
        }
        /* Настрйка вкладок*/
        /* Стили секций с содержанием */
        .tabs > section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
        }
        .tabs > section > p {
            margin: 0 0 5px;
            line-height: 1.5;
            color: #383838;
            /* прикрутим анимацию */
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }
        /* Прячем чекбоксы */
        .tabs > input {
            display: none;
            position: absolute;
        }
        /* Стили переключателей вкладок (табов) */
        .tabs > label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: center;
            color: #aaa;
            border: 0px solid #ddd;
            border-width: 1px 1px 1px 1px;
            background: #f1f1f1;
            border-radius: 3px 3px 0 0;
        }
        /* Шрифт-иконки от Font Awesome в формате Unicode */
        .tabs > label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
        }
        /* Изменения стиля переключателей вкладок при наведении */
        .tabs > label:hover {
            color: #888;
            cursor: pointer;
        }
        /* Стили для активной вкладки */
        .tabs > input:checked + label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
        }
        /* Активация секций с помощью псевдокласса :checked */
        #tab1:checked ~ #content-tab1,
        #tab2:checked ~ #content-tab2 {
            display: block;
        }
        /* Убираем текст с переключателей и оставляем иконки на малых экранах*/
        @media screen and (max-width: 680px) {
            .tabs > label {
                font-size: 0;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 18px;
            }
        }
        /* Изменяем внутренние отступы переключателей для малых экранов */
        @media screen and (max-width: 400px) {
            .tabs > label {
                padding: 15px;
            }
        }
        /* Блокировка экрана */
        .lockScreenOff {
            display: none;
            visibility: hidden;
        }
        .lockScreenOn {
            display: block;
            visibility: visible;
            position: absolute;
            z-index: 999;
            top: 0px;
            left: 0px;
            width: 100%;
            height: 100%;
            background-color: #ccc;
            text-align: center;
            filter: alpha(opacity=75);
            opacity: 0.75;
        }
        .stop-scrolling {
            height: 100%;
            overflow: hidden;
        }
        /* Стили лоадера */
        .hide {
            display: none;
        }
        .loader {
            border: 16px solid #f3f3f3;
            border-top: 16px solid #364274;
            border-radius: 50%;
            width: 120px;
            height: 120px;
            animation: spin 2s linear infinite;
            position: relative;
            top: 40%;
            left: 45%;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>

<body onload="cop()">

<div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
</div>

<div class="one">
    <h1>сервис распределения вагонов</h1>
    <div class="train">
    		<img src="resources/train.jpg" width="auto">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<br><br><br><br><br>

<div>
    <c:if test="${empty totalMap}">
        <input type="button" value="Создать отчет" onclick="showPopup()" class="bot1" style="visibility:visible">
    </c:if>

    <c:if test="${!empty totalMap}">
        <form action="/uraltranscom" method="get" style="visibility:visible">
            <input type="submit" value="Очистить форму" class="bot1">
        </form>
    </c:if>

    <table class="table_load_file">
        <tr>
            <td class="td_load_file">
                <div id="popup"
                     style="position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;">
                    <div id="popup_bg"
                         style="background: rgba(0, 0, 0, 0.2); position: absolute; z-index: 1; height: 100%; width: 100%;">
                    </div>
                    <div class="form">
                        <form enctype="multipart/form-data" method="post" action="report">
                            <p>Файл заявок</p>
                            <input type="file" name="routes" multiple accept="xlsx">
                            <p>Файл дислокации вагонов</p>
                            <input type="file" name="wagons" multiple accept="xlsx">
                            <p>
                                <input type="submit" value="Запустить распределение" class="bot1" id="startProcess" onclick="lockScreen();">
                            </p>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <form action="export" method="post">
    <br>
        <c:if test="${!empty totalMap}">
            <input type="submit" value="Скачать отчет" class="bot1">
        </c:if>
        <div>
            <div class="tabs">
                <input id="tab1" type="radio" name="tabs" checked>
                <label for="tab1" title="Распределенные рейсы">Распределенные заявки</label>

                <section id="content-tab1">
                 <div>
                    <table class="table_report">
                        <tr>
                            <th class="td_report">Вагон</th>
                            <th class="td_report">Станция назначения вагона</th>
                            <th class="td_report">Текущий клиент</th>
                            <th class="td_report" align="center">Выгодные направления</th>
                        </tr>
                        <c:if test="${!empty totalMap}">
                            <c:forEach items="${totalMap}" var="map">
                            <tr>
                                <td class="td_report1">${map.key.getNumberOfWagon()}</td>
                                <td class="td_report2">${map.key.getNameOfStationDestination()}</td>
                                <td class="td_report3">${map.key.getCustomer()}</td>
                                <td class="td_report4">
                                    <div class="div">
                                        <table class="table_total">
                                            <c:forEach items="${map.value}" var="mapRoute" begin="0" end="2">
                                                <c:forEach items="${mapRoute.key}" var="route">
                                                <tr>
                                                    <td class="td_total_report1">${route.key.getNameOfStationDeparture()}</td>
                                                    <td class="td_total_report2">${route.key.getNameOfStationDeparture()} - ${route.key.getNameOfStationDestination()}</td>
                                                    <td class="td_total_report3">${route.key.getCustomer()}</td>
                                                    <td class="td_total_report4">${mapRoute.value}</td>
                                                    <td class="td_total_report5"><input type="checkbox" name="routeIds" value="${route.key.getNumberOrder()}_${map.key.getNumberOfWagon()}" /></td>
                                                    <form action="calc" method="post" id="calc">
                                                        <td class="td_total_report6">
                                                            <input type="image" form="calc" src="resources/excel.png" width="25px" height="25px" name="nameFile" value="${route.key.getNumberOrder()}_${map.key.getNumberOfWagon()}" />
                                                        </td>
                                                    </form>
                                                    <td class="td_total_report7">
                                                        <div class="poster">
                                                            <img src="resources/magnifier.png" width="20px" height="20px">
                                                            <div class="descr">
                                                                <table class="table_calculate">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="td_table1" rowspan="3">Станция отправления</td>
                                                                            <td class="td_table1" rowspan="3">Станция назначения</td>
                                                                            <td class="td_table1" rowspan="3">Наименование груза</td>
                                                                            <td class="td_table1" rowspan="3">Расст., км</td>
                                                                            <td class="td_table1" rowspan="3">Время в пути, сут</td>
                                                                            <td class="td_table1" rowspan="3">Погр. / выгр.</td>
                                                                            <td class="td_table1" rowspan="3">Оборот, сут.</td>
                                                                            <td class="td_table1" rowspan="3">ВО</td>
                                                                            <td class="td_table1" rowspan="2">ДОХОД</td>
                                                                            <td class="td_table1">РАСХОД</td>
                                                                            <td class="td_table1" colspan="2">ПРИБЫЛЬ</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table1">Тариф в собств. вагонах</td>
                                                                            <td class="td_table1">За нахождение в пути</td>
                                                                            <td class="td_table1">В сутки</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table1">руб/ваг.</td>
                                                                            <td class="td_table1">руб/ваг.</td>
                                                                            <td class="td_table1">руб/ваг.</td>
                                                                            <td class="td_table1">руб/ваг/сут.</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getCurrentNameStationDeparture()} (${route.value.getCurrentRoadStationDeparture()})</td>
                                                                            <td class="td_table2">${route.value.getCurrentNameStationDestination()} (${route.value.getCurrentRoadStationDestination()})</td>
                                                                            <td class="td_table2">${route.value.getCurrentCargo()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentDistance()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentCountDays()}</td>
                                                                            <td class="td_table2">${route.value.getFirstLoadingWagon()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentCountDaysWithLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2">${route.value.getCurrentRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getCurrentRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getEmptyNameStationDeparture()} (${route.value.getEmptyRoadStationDeparture()})</td>
                                                                            <td class="td_table2">${route.value.getEmptyNameStationDestination()} (${route.value.getEmptyRoadStationDestination()})</td>
                                                                            <td class="td_table2">${route.value.getEmptyCargo()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyDistance()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyCountDays()}</td>
                                                                            <td class="td_table2">${route.value.getUnloadingWagon()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyCountDaysWithLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getEmptyTariff()}</td>
                                                                            <td class="td_table2">-${route.value.getEmptyTariff()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getSecondNameStationDeparture()} (${route.value.getSecondRoadStationDeparture()})</td>
                                                                            <td class="td_table2">${route.value.getSecondNameStationDestination()} (${route.value.getSecondRoadStationDestination()})</td>
                                                                            <td class="td_table2">${route.value.getSecondCargo()}</td>
                                                                            <td class="td_table2">${route.value.getSecondDistance()}</td>
                                                                            <td class="td_table2">${route.value.getSecondCountDays()}</td>
                                                                            <td class="td_table2">${route.value.getSecondLoadingWagon()}</td>
                                                                            <td class="td_table2">${route.value.getSecondCountDaysWithLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2">${route.value.getSecondRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getSecondRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getEmptySecondNameStationDeparture()} (${route.value.getEmptySecondRoadStationDeparture()})</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondNameStationDestination()} (${route.value.getEmptySecondRoadStationDestination()})</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCargo()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondDistance()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCountDays()}</td>
                                                                            <td class="td_table2">${route.value.getUnloadingWagon()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCountDaysWithLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getEmptySecondTariff()}</td>
                                                                            <td class="td_table2">-${route.value.getEmptySecondTariff()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table3" colspan="3"></td>
                                                                            <td class="td_table3">${route.value.getDistanceSummary()}</td>
                                                                            <td class="td_table3">${route.value.getCountDaysSummary()}</td>
                                                                            <td class="td_table3">${route.value.getSummaryLoading()}</td>
                                                                            <td class="td_table3">${route.value.getCountDaysSummaryWithLoad()}</td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3">${route.value.getTotalSummary()}</td>
                                                                            <td class="td_table3">${mapRoute.value}</td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                                </c:forEach>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                 </div>
                </section>
            </div>
        </div>
    </form>
    <br>
</div>

<br><br><br>

<div align="center" id="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>(ver. 5.0)
</div>

</body>
</html>