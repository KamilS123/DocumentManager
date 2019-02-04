<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="css/userMainContent.css"/>
</head>
<style>
    body {
        background-image: url("img/middle.jpg");
        background-size: cover;
    }
</style>
<body>
<div id="main">
    <div id="mainContentModeratorForm">
        <form method="post" action="changeStatusToUser">
            <input type="submit" value="User"/>
        </form>
        <form method="post" action="changeStatusToModerator">
            <input type="submit" value="Moderator"/>
        </form>
        <form method="post" action="changeStatusToAdmin">
            <input type="submit" value="Admin"/>
        </form>
    </div>
    <div id="mainContentHeader">
        <h1>Document Manager<span style="color: black">${loginName} ${loginSurname}</span></h1>
        <form method="post" action="/findDocByName">
            <input type="text" name="docNameToFind" placeholder="Find document by name..."/>
        </form>
    </div>
    <div style="clear: both"></div>
</div>
<div id="menuAndManiContentDiv">
    <div id="mainContentMenu">
        <h3 id="mainContentMenuH3">MENU</h3>

        <form method="post" action="/createNewDocument">
            <input type="submit" value="Add new document"/>
        </form>

        <form method="post" action="/docMenuShow">
            <input type="submit" value="Edit/Delete"/>
        </form>

        <form>
            <input type="submit" value="Review document"/>
        </form>

        <form>
            <input type="submit" value="Download document"/>
        </form>

        <form>
            <input type="submit" value="Sort documents"/>
        </form>

        <form>
            <input type="submit" value="Send message to admin"/>
        </form>

        <form method="post" action="/changePasswordForm">
            <%--<input type="hidden" value="${loginID}" name="loginID"/>--%>
            <input type="submit" value="Change password"/>
        </form>
    </div>
    <div id="userContent">
        <table style="width: 100%">
            <c:forEach var="element" items="${docNameToFind}">
                <tr id="tableRow">
                    <td>${element.getdocument_name()}</td>
                    <td>${element.getDocument_description()}</td>
                    <td>${element.getDocument_comments()}</td>
                    <td>${element.getAdd_date()}</td>
                    <td>${element.getEdition_date()}</td>
                        <td>
                            <form method="post" action="/editDocFromList">
                                <input type="hidden" name="docID" value="${element.getId()}"/>
                               <%-- <input type="hidden" name="loginID" value="${loginID}"/>
                                <input type="hidden" value="${loginName}" name="loginName"/>
                                <input type="hidden" value="${loginSurname}" name="loginSurname"/>--%>
                                <input type="submit" value="Edit"/>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/deleteDocument">
                                <input type="hidden" name="docID" value="${element.getId()}"/>
                                <%--<input type="hidden" name="loginID" value="${loginID}"/>
                                <input type="hidden" value="${loginName}" name="loginName"/>
                                <input type="hidden" value="${loginSurname}" name="loginSurname"/>--%>
                                <input type="submit" value="Delete"/>
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