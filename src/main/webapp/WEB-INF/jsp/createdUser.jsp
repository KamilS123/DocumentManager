<html lang="en">
<head>
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/createdUsers.css"/>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="createdUserHeader">
    <h1>Registered with details...</h1>
</div>
<div id="createdUserMainContent">
    <table>
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>Password</th>
            <th>Status</th>
        </tr>
        <tr>
            <td>${registryName}</td>
            <td>${registrySurname}</td>
            <td>${registryPassword}</td>
            <td>${userStatus}</td>
        </tr>
    </table>
    <form id="cratedUserBtn" method="post" action="/login">
        <input type="submit" value="Sign in"/>
    </form>
</div>




</body>
</html>