<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <title>Document list</title>
    <link rel="stylesheet" type="text/css" href="css/documentsMenu.css"/>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="documentMenuHeader">
    <h2>List of your documents...</h2>
</div>
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
                    <input type="hidden" name="docID" value="${element.getId()}"/>
                    <input type="submit" value="Edit"/>
                </form>
            </td>
            <td>
                <form method="post" action="/deleteDocument">
                    <input type="hidden" name="docID" value="${element.getId()}"/>
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <form id="goToMenuBtn" method="post" action="/goToMainContent">
        <input  type="submit" value="Go to menu">
    </form>
</table>

</body>
</html>