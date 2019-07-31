<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List With Messages</title>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<link rel = "stylesheet" type="text/css" href="css/listWithMessage.css">
<body>
<div id="tab">
    <table>
        <tr>
            <th>Message_id</th>
            <th>User_id</th>
            <th>User_name</th>
            <th>User_surname</th>
            <th>User_status</th>
            <th>Message</th>
        </tr>
        <c:forEach var="element" items="${listWithMessagesToAdmin}">
            <tr>
                <td>${element.getId()}</td>
                <td>${element.getUser().getId()}</td>
                <td>${element.getUser().getName()}</td>
                <td>${element.getUser().getSurname()}</td>
                <td>${element.getUser().getStatus()}</td>
                <td>${element.getMessage()}</td>
                <td>
                    <form method="post" action="deleteMessageFromUsers">
                        <input type="hidden" name="deleteMessageID" value="${element.getId()}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" action="displayAdminMainContent">
        <input type="submit" value="Go to menu">
    </form>
</div>
</body>
</html>
