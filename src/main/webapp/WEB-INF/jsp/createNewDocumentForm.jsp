<html xmlns="http://www.w3.org/1999/xhtml">
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/createNewDocumentForm.css"/>
</head>
<body>
<div id="header">
    <h1>Please pass your document details...</h1>
</div>
<div id="registryForm">
    <form method="post" action="/selectDocument" enctype="multipart/form-data">
        <input type="text" name="documentName" placeholder="Document name">
        <input type="text" name="documentDescription" placeholder="Document description">
        <input type="text" name="documentComments" placeholder="Document comments">
        <input type="text" name="choosenDocument" placeholder="C:\Users\Kamil\Desktop\rys1.png">
        <input type="submit" value="Save details">
    </form>
</div>
</body>
</html>