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
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>

    <!-- Вызов лоадера -->
    <script>
        jQuery(function ($) {
            $('#startProcess').on('click', function (e) {
                $('.content').toggleClass('hide');
            });
        });
    </script>

    <!-- Блокировка экрана -->
    <script type="text/javascript">
        function lockScreen() {
            var lock = document.getElementById('lockPane');
            if (lock)
                lock.className = 'lockScreenOn';
        }
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

    <style>
        body {
            font: 14px/1 "Open Sans", sans-serif;
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
        /* Описываем анимацию свойства opacity */
        @-webkit-keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
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
        #tab2:checked ~ #content-tab2,
        #tab3:checked ~ #content-tab3,
        #tab4:checked ~ #content-tab4 {
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
    </style>
</head>

<body onload="cop()">

<div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
</div>

<div class="one">
    <h1>сервис распределения вагонов</h1>
</div>

<div>
    <img style="position: relative; left: 15%;" src="resources/logo.jpg">
</div>

<br><br><br><br><br>

<div>
    <input type="button" value="Создать отчет" onclick="showPopup()"
           class="bot1">
    <form action="/uraltranscom" method="get">
        <input type="submit" value="Очистить форму"
               class="bot1">
    </form>

    <table class="table_report">
        <tr>
            <td class="td_report">
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
                                <input type="submit" value="Start" class="bot2" id="startProcess" onclick="lockScreen();">
                            </p>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <div class="container">
        <div class="tabs">
            <input id="tab1" type="radio" name="tabs" checked>
            <label for="tab1" title="Распределенные рейсы">Распределенные рейсы</label>

            <input id="tab2" type="radio" name="tabs">
            <label for="tab2" title="Нераспределенные рейсы">Нераспределенные рейсы</label>

            <input id="tab3" type="radio" name="tabs">
            <label for="tab3" title="Нераспределенные вагоны">Нераспределенные вагоны</label>

            <input id="tab4" type="radio" name="tabs">
            <label for="tab4" title="Ошибки">Ошибки</label>

            <section id="content-tab1">
                <p>
                    <c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
                <table>
                    <c:forEach items="${reportListOfDistributedRoutesAndWagons}" var="reportList">
                        <tr>
                            <td>${reportList}</td>
                        </tr>
                    </c:forEach>
                </table>
                </c:if>
                </p>
            </section>
            <section id="content-tab2">
            <div>
            	<table class="table_report">
            		<tr>
            			<th>Номер заявки</th>
            			<th>Станция отправления</th>
            			<th>Станция назначения</th>
            			<th>Расстояние</th>
            			<th>Разница. ПС</th>
            			<th>Груз</th>
            			<th>Объем</th>
            		</tr>
            		<br><br>
                    <c:if test="${!empty reportMapOfUndistributedRoutes}">
                        <c:forEach items="${reportMapOfUndistributedRoutes}" var="reportMapNoRoute">
                            <tr>
                                <td>${reportMapNoRoute.value.getNumberOrder()}</td>
                                <td>${reportMapNoRoute.value.getNameOfStationDeparture()}</td>
                                <td>${reportMapNoRoute.value.getNameOfStationDestination()}</td>
                                <td>${reportMapNoRoute.value.getDistanceOfWay()}</td>
                                <td>${reportMapNoRoute.value.getCountOrders()}</td>
                                <td>${reportMapNoRoute.value.getCargo()}</td>
                                <td>${reportMapNoRoute.value.getVolumePeriod()}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
            	</table>
            </div>
            </section>
            <section id="content-tab3">
                <p>
                    <c:if test="${!empty reportListOfDistributedWagons}">
                <table>
                    <c:forEach items="${reportListOfDistributedWagons}" var="reportListNoWagon">
                        <tr>
                            <td>${reportListNoWagon}</td>
                        </tr>
                    </c:forEach>
                </table>
                </c:if>
                </p>
            </section>
            <section id="content-tab4">
                <p>
                    <c:if test="${!empty reportListOfError}">
                <table>
                    <c:forEach items="${reportListOfError}" var="Error">
                        <tr>
                            <td>${Error}</td>
                        </tr>
                    </c:forEach>
                </table>
                </c:if>
                </p>
            </section>
        </div>
    </div>
    <br>
    <form action="export" method="post">
        <input type="submit" value="Скачать отчет" id="download" class="bot1">
    </form>
</div>

<br><br><br>

<div align="center" id="footer">
    Copyright &copy; ООО "Уральская транспортная компания" <span id="copy"></span>.
    Create by Vladislav Klochkov. All rights reserved
</div>

</body>
</html>