<html>
<head>
    <title>Create new document form</title>
    <link rel="stylesheet" type="text/css" href="css/createNewDocumentForms.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
    <form method="post" action="/selectDocument" >
        <input type="hidden" id="hiddenWithFileName" name="documentName" placeholder="Document name">
        <input type="text" name="documentDescription" placeholder="Document description">
        <input type="text" name="documentComments" placeholder="Document comments">
        <input type="text" id="file-upload" name="choosenDocument" placeholder="C:\Users\Kamil\Desktop\rys1.png">
        <p id="uploadError" style="color: red;">${errorInfo}</p>
        <input id="newDocSubmit" onclick="getPatch()" type="submit" value="Save details">
    </form>
    <a href="/login">Go back</a>
</div>
</body>
<script>
        function getPatch() {
           document.getElementById("hiddenWithFileName").value=document.querySelector("#file-upload").value;
        }
</script>

<script src="js/jQueryFile.js"></script>
</html>