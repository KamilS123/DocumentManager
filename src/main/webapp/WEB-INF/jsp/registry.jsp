<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/registry.css">
</head>
<body>
<div id="header">
    <h1>Please registry...</h1>
</div>
<div id="registryForm">
    <form method="post" action="/addUser">
        <input type="text" name="registryName" placeholder="Name">
        <input type="text" name="registrySurname" placeholder="Surname">
        <input type="password" name="registryPassword" placeholder="Password">
        <input type="submit" value="Save details">
    </form>

    <form method="post" action="/login">
        <input type="submit" value="Go back to login">
    </form>
</body>
</html>