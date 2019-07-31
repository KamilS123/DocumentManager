<%--
  Created by IntelliJ IDEA.
  User: Kamil
  Date: 2019-02-03
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change password form</title>
</head>
<link rel="stylesheet" href="css/changePasswordForm.css"/>
<body>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>

<div id="changePasswordHeader">
    <h1>Pass your new password...</h1>
</div>
<div id="changePasswordForm">
    <form method="post" action="/changePassword">
        <input type="password" name="password" placeholder="Password">
        <input type="password" name="repeatPassword" placeholder="Repeat Password">
        <input class="changePasswordSubmit" type="submit" value="Save password">
    </form>
</div>
</body>
</html>
