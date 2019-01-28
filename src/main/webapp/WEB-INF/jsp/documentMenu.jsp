<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/documentMenu.css"/>
</head>
<body>
<h2>List of your documents...</h2>
<table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Comments</th>
        <th>Add date</th>
        <th>Edition date</th>
    </tr>
    <c:forEach var="element" items="${documentList}">
    <tr>
            <td>${element.getdocument_name()}</td>
            <td>${element.getDocument_description()}</td>
            <td>${element.getDocument_comments()}</td>
            <td>${element.getAdd_date()}</td>
            <td>${element.getEdition_date()}</td>
            <td>
                <form method="post" action="/editDocFromList">
                    <input type="hidden" name="docNameValue" value="${element.getdocument_name()}"/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
            <td>
                <form method="post" action="/deleteDocument">
                    <input type="hidden" name="docNameValue" value="${element.getdocument_name()}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
    </tr>
    </c:forEach>
</table>

</body>
</html>