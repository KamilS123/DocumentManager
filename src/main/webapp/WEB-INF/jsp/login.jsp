<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/login.css" type="text/css">
</head>
<body>
<div id="header">
    <h1>Welcome in <span style="color: red">Document Manager!!!</span></h1>
</div>
<div id="logForm">
    <form method="post" action="/checkData">
        <input type="text" name="loginName" placeholder="Name">
        <input type="text" name="loginSurname" placeholder="Surname">
        <input type="password" name="loginPassword" placeholder="Password">
        <input type="submit" value="Sign in">
    </form>

    <form method="post" action="/registry">
        <input type="submit" value="Registry">
    </form>
</div>
</body>
</html>
