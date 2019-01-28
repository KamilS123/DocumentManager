<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<!doctype html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/userMainContent.css"/>
</head>
<body>
<div id="header">
    <h1>Hi <span style="color: red">${loginName}...</span></h1>
</div>

<div id="menuForm">
    <form method="post" action="/createNewDocument">
        <input type="submit" value="Add new document"/>
    </form>

    <form method="post" action="/docMenuShow">
        <input type="submit" value="Edit documents"/>
    </form>

    <form>
        <input type="submit" value="Add comment"/>
    </form>

    <form>
        <input type="submit" value="Review document"/>
    </form>

    <form>
        <input type="submit" value="Download document"/>
    </form>

    <form>
        <input type="submit" value="Find document"/>
    </form>

    <form>
        <input type="submit" value="Sort documents"/>
    </form>

    <form>
        <input type="submit" value="Send message to admin"/>
    </form>

    <form>
        <input type="submit" value="Change password"/>
    </form>
</div>
</body>
</html>