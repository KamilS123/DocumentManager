<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/loginForm.css" type="text/css">
</head>
<style>
    body
    {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="loginHeader">
    <h1 id="">Welcome in <span style="color: black; font-weight: bolder">Document Manager!!!</span></h1>
</div>
<div id="loginLogForm">
    <form method="post" action="/login">
        <input type="text" name="loginName" placeholder="Name">
        <input type="password" name="loginPassword" placeholder="Password">
        <input class="submit" type="submit" value="Sign in">
    </form>

    <form method="post" action="/registry">
        <input class="submit" type="submit" value="Registry">
    </form>
</div>
</body>
</html>
