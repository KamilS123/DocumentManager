<html>
<head>
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/createNewDocumentForms.css"/>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="createNewDocHeader">
    <h1>Please pass your document details...</h1>
</div>
<div id="createNewDocPassingDataForm">
    <form method="post" action="/selectDocument" enctype="multipart/form-data">
        <input type="hidden" value="${loginID}" name="loginID"/>
        <input type="hidden" value="${loginName}" name="loginName"/>
        <input type="hidden" value="${loginSurname}" name="loginSurname"/>
        <input type="text" name="documentName" placeholder="Document name">
        <input type="text" name="documentDescription" placeholder="Document description">
        <input type="text" name="documentComments" placeholder="Document comments">
        <input type="text" name="choosenDocument" placeholder="C:\Users\Kamil\Desktop\rys1.png">
        <input id="newDocSubmit" type="submit" value="Save details">
    </form>
</div>
</body>
</html>