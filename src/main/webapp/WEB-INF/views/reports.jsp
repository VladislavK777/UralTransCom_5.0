<%--
  Created by IntelliJ IDEA.
  User: Vladislav.Klochkov
  Date: 15.01.2018
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${!empty reportListOfDistributedRoutesAndWagons}">
    <table>
        <tr>
            <th>Route</th>
        </tr>
        <c:forEach items="${reportListOfDistributedRoutesAndWagons}" var="reportList">
            <tr>
                <td>${reportList}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${!empty reportListOfDistributedRoutes}">
    <table>
        <tr>
            <th>NoRoote</th>
        </tr>
        <c:forEach items="${reportListOfDistributedRoutes}" var="reportListNo">
            <tr>
                <td>${reportListNo}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${!empty reportListOfDistributedWagons}">
    <table>
        <tr>
            <th>NoWagone</th>
        </tr>
        <c:forEach items="${reportListOfDistributedWagons}" var="reportListNoWagon">
            <tr>
                <td>${reportListNoWagon}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${!empty reportListOfError}">
    <table>
        <tr>
            <th>Error</th>
        </tr>
        <c:forEach items="${reportListOfError}" var="Error">
            <tr>
                <td>${Error}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
