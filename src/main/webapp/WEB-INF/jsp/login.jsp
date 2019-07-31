<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css" type="text/css">
</head>
<style>
    body
    {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }

    /*#loginHeader {*/
        /*margin: 20px auto auto auto;*/
        /*padding: 30px;*/
        /*text-align: center;*/
        /*font-size: 30px;*/
        /*font-weight: bold;*/
        /*color: white;*/
        /*border-bottom: 5px solid white;*/
    /*}*/

    /*#loginLogForm {*/
        /*border: 4px solid black;*/
        /*width: 300px;*/
        /*height: 300px;*/
        /*margin: 50px auto 0 auto;*/
    /*}*/

    /*#loginLogForm form {*/
        /*text-align: center;*/
    /*}*/

    /*#loginLogForm form input {*/
        /*width: 250px;*/
        /*height: 40px;*/
        /*padding: 10px;*/
        /*margin-top: 10px;*/
        /*border-radius: 3px;*/
    /*}*/
    /*.submit*/
    /*{*/
        /*background-color: black;*/
        /*color: white;*/
        /*font-size: 20px;*/
    /*}*/
    /*.submit:hover*/
    /*{*/
        /*background-color: gray;*/
        /*color: white;*/
    /*}*/
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