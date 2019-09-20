<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User main content</title>
    <link rel="stylesheet" type="text/css" href="css/userContent.css"/>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
    .file_option{
        width: 100%;
        height: 50px;
    }
</style>
<body>
<div id="main">
    <div id="mainContentModeratorForm">
        <form method="post" action="changeStatus">
            <input type="hidden" name="changeStatusValue" value="user"/>
            <input style="background-color: black; color: white;" type="submit" value="User"/>
        </form>
        <form method="post" action="changeStatus">
            <input type="hidden" name="changeStatusValue" value="moderator"/>
            <input type="submit" value="Moderator"/>
        </form>
        <form method="post" action="changeStatus">
            <input type="hidden" name="changeStatusValue" value="admin"/>
            <input type="submit" value="Admin"/>
        </form>
    </div>
    <div id="mainContentHeader">
        <h1>Document Manager</h1>
        <form method="post" action="/findDocByName">
            <input type="text" name="docNameToFind" placeholder="Find document by name..."/>
        </form>
    </div>
    <div style="clear: both"></div>
</div>
<div id="menuAndManiContentDiv">
    <div id="mainContentMenu">
        <h3 id="mainContentMenuH3">MENU</h3>

        <form method="post" action="createNewDocument">
            <input type="submit" value="Add new document"/>
        </form>

        <form method="post" action="docMenuShow">
            <input type="submit" value="Manage my collection"/>
        </form>

        <form method="post" action="sendMessageToAdminForm">
            <input type="submit" value="Send message to admin"/>
        </form>

        <form method="post" action="changePasswordForm">
            <input type="submit" value="Change password"/>
        </form>

        <form method="post" action="logOut">
            <input type="submit" value="Log out"/>
        </form>
    </div>
    <div id="userContent">
        <table style="width: 100%">
            <tr>
                <th>
                    <div id="sortBtn">
                        <form method="post" action="sortByName">
                            <input type="submit" value="Sort by name"/>Name
                        </form>
                    </div>
                </th>
                <th>Description</th>
                <th>Comments</th>
                <th>Add date</th>
                <th>Edition date</th>
            </tr>
            <c:forEach var="element" items="${docNameToFind}">
                <tr id="tableRow">
                    <td>${element.getDocumentName()}</td>
                    <td>${element.getDocumentDescription()}</td>
                    <td>${element.getDocumentComment()}</td>
                    <td>${element.getAdditionDate()}</td>
                    <td>${element.getEditionDate()}</td>
                    <td >
                        <form method="post" action="editDocFromList">
                            <input type="hidden" name="docID" value="${element.getId()}"/>
                            <input class="file_option" type="submit" value="Edit"/>
                        </form>
                    </td>
                    <td >
                        <form method="post" action="deleteDocument">
                            <input type="hidden" name="docID" value="${element.getId()}"/>
                            <input class="file_option" type="submit" value="Delete"/>
                        </form>
                    </td>
                    <td >
                        <form method="post" action="viewDocument">
                            <input type="hidden" name="docID" value="${element.getId()}"/>
                            <input class="file_option" type="submit" value="View"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div style="clear: both"></div>
</div>
</body>
</html>