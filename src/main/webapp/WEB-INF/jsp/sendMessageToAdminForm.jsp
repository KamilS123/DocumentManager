<%--
  Created by IntelliJ IDEA.
  User: Kamil
  Date: 2019-02-18
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Message To Admin</title>
    <link rel="stylesheet" href="css/messageToAdmin.css" type="text/css">
</head>
<body>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<div id = "formForMessage">
<form method="post" action="sendMessageToAdmin">
    <input type="text" name="messageToAdmin">
    <input type="submit" value="Send">
</form>
</div>
</body>
</html>
