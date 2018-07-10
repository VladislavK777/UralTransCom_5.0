<%--
  Created by IntelliJ IDEA.
  User: Vladislav.Klochkov
  Date: 15.06.2018
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

        * {
          outline: 0;
          font-family: sans-serif
        }
        span.choose {
          color: #555;
          padding: 5px 0 10px;
          display: inherit
        }
        .container {
          width: 500px;
        }

        .dropdown {
          width: 300px;
          display: inline-block;
          background-color: #fff;
          border-radius: 5px;
          box-shadow: 0 0 2px rgb(204, 204, 204);
          transition: all .5s ease;
          position: relative;
          font-size: 14px;
          color: #474747;
          height: 100%;
          text-align: left
        }
        .dropdown .select {
            cursor: pointer;
            display: block;
            padding: 10px
        }
        .dropdown .select > i {
            font-size: 13px;
            color: #888;
            cursor: pointer;
            transition: all .3s ease-in-out;
            float: right;
            line-height: 20px
        }
        .dropdown:hover {
            box-shadow: 0 0 4px rgb(204, 204, 204)
        }
        .dropdown:active {
            background-color: #f8f8f8
        }
        .dropdown.active:hover,
        .dropdown.active {
            box-shadow: 0 0 4px rgb(204, 204, 204);
            border-radius: 5px 5px 0 0;
            background-color: #f8f8f8
        }
        .dropdown.active .select > i {
            transform: rotate(-90deg)
        }
        .dropdown .dropdown-menu {
            position: absolute;
            background-color: #fff;
            width: 100%;
            left: 0;
            margin-top: 1px;
            box-shadow: 0 1px 2px rgb(204, 204, 204);
            border-radius: 0 1px 5px 5px;
            overflow: hidden;
            display: none;
            max-height: 144px;
            overflow-y: auto;
            z-index: 9
        }
        .dropdown .dropdown-menu li {
            padding: 10px;
            transition: all .2s ease-in-out;
            cursor: pointer
        }
        .dropdown .dropdown-menu {
            padding: 0;
            list-style: none
        }
        .dropdown .dropdown-menu li:hover {
            background-color: #f2f2f2
        }
        .dropdown .dropdown-menu li:active {
            background-color: #e2e2e2
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
    <form action="/uraltranscom" method="get">
        <input type="submit" value="Очистить форму" class="bot1">
    </form>

    <form action="report" method="post">
    <br>
        <input type="submit" value="Продолжить" class="bot1" id="startProcess" onclick="lockScreen();">
        <div>
            <div class="tabs">
                <c:if test="${!empty needWashingWagon}">
                    <input id="tab1" type="radio" name="tabs" checked>
                    <label for="tab1" title="Список дислокаций">Список дислокаций</label>
                    <section id="content-tab1">
                    <div>
                        <table class="table_report">
                            <tr>
                                <th>Номер вагона</th>
                                <th>Станция отправления</th>
                                <th>Станция назначения</th>
                                <th>Заказчик</th>
                                <th>Груз</th>
                                <th>Станция промывки</th>
                            </tr>
                            <c:forEach items="${needWashingWagon}" var="wagon">
                                <tr>
                                    <td>${wagon.value.getNumberOfWagon()}</td>
                                    <td>${wagon.value.getNameOfStationDeparture()}</td>
                                    <td>${wagon.value.getNameOfStationDestination()}</td>
                                    <td>${wagon.value.getCustomer()}</td>
                                    <td>${wagon.value.getCargo().getNameCargo()}</td>
                                    <td>
                                        <div class="container">
                                            <div class="dropdown">
                                                <div class="select">
                                                    <span>Выбрать станцию</span>
                                                    <i class="fa fa-chevron-left"></i>
                                                </div>
                                                <input type="hidden" name="gender">
                                                <ul class="dropdown-menu">
                                                    <li>Нет</li>
                                                    <c:forEach items="${wagon.key}" var="washingStation">
                                                        <li>(${washingStation.value[0]}) ${washingStation.value[1]} ${washingStation.value[2]}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                     </div>
                    </section>
                </c:if>
                <c:if test="${!empty needWashingRoute}">
                    <input id="tab2" type="radio" name="tabs">
                    <label for="tab2" title="Список заявок">Списоз заявок</label>
                    <section id="content-tab2">
                    <div>
                        <table class="table_report">
                            <tr>
                                <th>Номер заявки</th>
                                <th>Станция отправления</th>
                                <th>Станция назначения</th>
                                <th>Заказчик</th>
                                <th>Груз</th>
                                <th>Станция промывки</th>
                            </tr>
                            <c:forEach items="${needWashingRoute}" var="route">
                                <tr>
                                    <td>${route.value.getNumberOrder()}</td>
                                    <td>${route.value.getNameOfStationDeparture()}</td>
                                    <td>${route.value.getNameOfStationDestination()}</td>
                                    <td>${route.value.getCustomer()}</td>
                                    <td>${route.value.getCargo().getNameCargo()}</td>
                                    <td>
                                        <div class="container">
                                            <div class="dropdown">
                                                <div class="select">
                                                    <span>Выбрать станцию</span>
                                                    <i class="fa fa-chevron-left"></i>
                                                </div>
                                                <input type="hidden" name="gender">
                                                <ul class="dropdown-menu">
                                                    <li>Нет</li>
                                                    <c:forEach items="${route.key}" var="washingStation">
                                                        <li>(${washingStation.value[0]}) ${washingStation.value[1]} ${washingStation.value[2]}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                     </div>
                    </section>
                </c:if>
            </div>
        </div>
        <br>
    </form>
    <br>
</div>

<script type="text/javascript">
    $('.dropdown').click(function () {
        $(this).attr('tabindex', 1).focus();
        $(this).toggleClass('active');
        $(this).find('.dropdown-menu').slideToggle(300);
    });
    $('.dropdown').focusout(function () {
        $(this).removeClass('active');
        $(this).find('.dropdown-menu').slideUp(300);
    });
    $('.dropdown .dropdown-menu li').click(function () {
        $(this).parents('.dropdown').find('span').text($(this).text());
        $(this).parents('.dropdown').find('input').attr('value', $(this).attr('id'));
    });
</script>

<br><br><br>

<div align="center" id="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>(ver. 5.0)
</div>

</body>
</html>