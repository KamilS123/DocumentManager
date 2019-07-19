<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users Table</title>
</head>
<body>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<div>
<table style="width: 50%; font-size:30px; color:white; margin: 100px auto auto auto;">
    <tr style="background-color: darkgray; border: 4px solid white">
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Status</th>
        <th>Active/Banned</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr style="background-color: black; border: 4px solid white">
            <td>${user.getId()}</td>
            <td>${user.getName()}</td>
            <td>${user.getSurname()}</td>
            <td>${user.getStatus()}</td>
            <td>${user.getActivationStatus()}</td>
            <td></td>
            <td>
                <form method="post" action="lockUnlock">
                    <input type="hidden" value="${user.getId()}" name="userID">
                    <input type="submit" name="activationStatus" value="Lock/Unlock">
                </form>
            </td>
        </tr>
    </c:forEach>

</table>
</div>
<div style="width: 100px; height: 100px; margin-left: auto;margin-right: auto">
    <form method="post" action="adminMainContent">
        <input style="width: 100px; height: 100px; " type="submit" value="Go back">
    </form>
</div>
</body>
</html>
