<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View document</title>
</head>
<body>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<main>
        <div style=" width: 80%;height: 80%; margin: 50px auto auto auto">
            <%--<img  src="data:application/jpg;base64,${userImage}"/>--%>
            <embed style="width: 100%; height: 95%" type="application/pdf" src="data:application/pdf;base64,${userImage}"/>
            <a href="/login" style="font-size: 40px; font-weight: bolder; color: white">Go back</a>
        </div>

</main>

</body>
</html>
