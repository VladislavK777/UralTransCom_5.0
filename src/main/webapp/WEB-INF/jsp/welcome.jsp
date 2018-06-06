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

        .poster {
            position:relative;
            background:#ff6600;
            height:10px;
            width:10px;
        }

        .descr {
            display:none;
            margin-left:-350px;
            padding:10px;
            margin-top:17px;
            background:#f3f3f3;
            height:205px;
            -moz-box-shadow:0 5px 5px rgba(0,0,0,0.3);
            -webkit-box-shadow:0 5px 5px rgba(0,0,0,0.3);
            box-shadow:0 5px 5px rgba(0,0,0,0.3);
        }

        .poster:hover .descr {
            display:block;
            position:fixed;
            top: 200px;
            right: 80px;
            z-index: 9999;
            width: 1355px;
        }

        .td_table {
            border-width: 0px 1px 1px 1px;
            border-top-style: initial;
            border-right-style: solid;
            border-bottom-style: solid;
            border-left-style: initial;
            border-top-color: initial;
            border-right-color: rgb(136, 136, 136);
            border-bottom-color: rgb(136, 136, 136);
            border-left-color: initial;
            border-image: initial;
            padding: 3px;
            min-width: 25px;
            text-align: center;
            vertical-align: middle;
            background-color: rgb(255, 255, 153);
        }

        .td_table2 {
            border: 1px solid rgb(136, 136, 136);
            padding: 3px;
            min-width: 25px;
            text-align: center;
            vertical-align: middle;
        }

        .td_table3 {
            border-width: 1px 1px 0px 0px;
            border-top-style: solid;
            border-right-style: solid;
            border-bottom-style: initial;
            border-left-style: initial;
            border-top-color: rgb(136, 136, 136);
            border-right-color: rgb(136, 136, 136);
            border-bottom-color: initial;
            border-left-color: initial;
            border-image: initial;
            padding: 3px;
            min-width: 25px;
            text-align: center;
            vertical-align: middle;
            background-color: rgb(204, 255, 204);
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
    		<img src="resources/train.jpg">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<br><br><br><br><br>

<div>
    <c:if test="${empty reportListOfDistributedRoutesAndWagons}">
        <input type="button" value="Создать отчет" onclick="showPopup()" class="bot1" style="visibility:visible">
    </c:if>

    <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
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
                        <form enctype="multipart/form-data" method="post" action="reports">
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
        <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
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
                        <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
                            <c:forEach items="${reportListOfDistributedRoutesAndWagons}" var="reportList">
                            <tr>
                                <td class="td_report1">${reportList.key.getNumberOfWagon()}</td>
                                <td class="td_report2">${reportList.key.getNameOfStationDestination()}</td>
                                <td class="td_report3">${reportList.key.getCustomer()}</td>
                                <td class="td_report4">
                                    <div class="div">
                                        <table class="table_total">
                                            <c:forEach items="${reportList.value}" var="var" begin="0" end="2">
                                                <c:forEach items="${var.key}" var="route">
                                                <tr>
                                                    <td class="td_total_report1">${route.key.getNameOfStationDeparture()}</td>
                                                    <td class="td_total_report2">${route.key.getNameOfStationDeparture()} - ${route.key.getNameOfStationDestination()}</td>
                                                    <td class="td_total_report3">${route.key.getCustomer()}</td>
                                                    <td class="td_total_report4">${var.value}р.</td>
                                                    <td class="td_total_report5"><input type="checkbox" name="routeIds" value="${route.key.getNumberOrder()}_${reportList.key.getNumberOfWagon()}" /></td>
                                                    <td class="td_total_report6">Скачать</td>
                                                    <td class="td_total_report7">

                                                        <div class="poster">
                                                            <div class="descr">
                                                                <table style="border: 1px solid rgb(136, 136, 136); border-collapse: collapse; table-layout: fixed;">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="td_table" rowspan="3">Станция отправления</td>
                                                                            <td class="td_table" rowspan="3">Станция назначения</td>
                                                                            <td class="td_table" rowspan="3">Наименование груза</td>
                                                                            <td class="td_table" rowspan="3">Расст., км</td>
                                                                            <td class="td_table" rowspan="3">Время в пути, сут</td>
                                                                            <td class="td_table" rowspan="3">Погр. / выгр.</td>
                                                                            <td class="td_table" rowspan="3">Оборот, сут.</td>
                                                                            <td class="td_table" rowspan="3">ВО</td>
                                                                            <td class="td_table" rowspan="2">ДОХОД</td>
                                                                            <td class="td_table">РАСХОД</td>
                                                                            <td class="td_table" colspan="2">ПРИБЫЛЬ</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table">Тариф в собств. вагонах</td>
                                                                            <td class="td_table">За нахождение в пути</td>
                                                                            <td class="td_table">В сутки</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table">руб/ваг.</td>
                                                                            <td class="td_table">руб/ваг.</td>
                                                                            <td class="td_table">руб/ваг.</td>
                                                                            <td class="td_table">руб/ваг/сут.</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getCurrentNameStationDeparture()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentNameStationDestination()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentCargo()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentDistance()}</td>
                                                                            <td class="td_table2">${route.value.getCurrentCountDays()}</td>
                                                                            <td class="td_table2">7.0</td>
                                                                            <td class="td_table2">${route.value.getCurrentCountDaysMinLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2">${route.value.getCurrentRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getCurrentRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getEmptyNameStationDeparture()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyNameStationDestination()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyCargo()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyDistance()}</td>
                                                                            <td class="td_table2">${route.value.getEmptyCountDays()}</td>
                                                                            <td class="td_table2">4.0</td>
                                                                            <td class="td_table2">${route.value.getEmptyCountDaysMinLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getEmptyTariff()}</td>
                                                                            <td class="td_table2">-${route.value.getEmptyTariff()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getSecondNameStationDeparture()}</td>
                                                                            <td class="td_table2">${route.value.getSecondNameStationDestination()}</td>
                                                                            <td class="td_table2">${route.value.getSecondCargo()}</td>
                                                                            <td class="td_table2">${route.value.getSecondDistance()}</td>
                                                                            <td class="td_table2">${route.value.getSecondCountDays()}</td>
                                                                            <td class="td_table2">10.0</td>
                                                                            <td class="td_table2">${route.value.getSecondCountDaysMinLoad()}</td>
                                                                            <td class="td_table2">поваг</td>
                                                                            <td class="td_table2">${route.value.getSecondRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                            <td class="td_table2">${route.value.getSecondRate()}</td>
                                                                            <td class="td_table2"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="td_table2">${route.value.getEmptySecondNameStationDeparture()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondStationDestination()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCargo()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondDistance()}</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCountDays()}</td>
                                                                            <td class="td_table2">4.0</td>
                                                                            <td class="td_table2">${route.value.getEmptySecondCountDaysMinLoad()}</td>
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
                                                                            <td class="td_table3">25.0</td>
                                                                            <td class="td_table3">${route.value.getCountDaysSummaryMinLoad()}</td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3"></td>
                                                                            <td class="td_table3">${route.value.getTotalSummary()}</td>
                                                                            <td class="td_table3">${var.value}</td>
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