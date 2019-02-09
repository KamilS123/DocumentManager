<head>
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/editDocumentContent.css"/>
</head>
<body>
<div id="header">
    <h1>Please edit your document details...</h1>
</div>
<div id="registryForm">
    <form method="post" action="/saveEditedDocuments">
        <input type="hidden" name="docID" value="${docID}"/>
        <input type="text" name="documentNameValue" placeholder="Document name">
        <input type="text" name="documentDescription" placeholder="Document description">
        <input type="text" name="documentComments" placeholder="Document comments">
        <input type="submit" value="Save details">
    </form>
</div>
</body>
</html>