<html>
<head>
    <title>Registry</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
</head>
<style>
    body
    {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="registrationHeader">
    <h1>Please registry...</h1>
</div>
<div id="registrationForm">
    <form method="post" action="/addUser">
        <input type="text" name="registryName" placeholder="Name">
        <input type="text" name="registrySurname" placeholder="Surname">
        <input type="password" name="registryPassword" placeholder="Password">
        <input class="registerBtn" type="submit" value="Save details">
    </form>

    <form method="post" action="/login">
        <input class="registerBtn" type="submit" value="Go back to login">
    </form>
</div>
</body>
</html>