<html xmlns="http://www.w3.org/1999/xhtml">
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/createdUser.css"/>
</head>
<body>
<h1>Registered with details...</h1>
<div id="mainContent">
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


        <form method="post" action="/login">
            <input type="submit" value="Sign in"/>
        </form>
</div>
</body>
</html>