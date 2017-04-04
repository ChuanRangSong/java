<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>订单列表</title>
</head>
<body>
    <table>
        <tr>
            <th>ID</th>
            <th>订单号</th>
            <th>订单金额</th>
            <th>创建时间</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.orderNo}</td>
                <td>${order.price}</td>
                <td>${order.createTime}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
