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
</head>
<script>
    function cop() {
        document.getElementById("copy").innerText = new Date().getFullYear();
    }
</script>
<body onload="cop()">
<div class="one">
    <h1>Сервис распределения вагонов</h1>
    <img src="resources/logo.jpg" >
</div>

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

<br>

<input type="button" value="Добавить отчет" onclick="showPopup()"
       class="bot1">

<div align="center" id="footer">
    Copyright &copy; ООО "Уральская транспортная компания" <span id="copy"></span>.
    Create by Vladislav Klochkov. All rights reserved
</div>
</body>
</html>
