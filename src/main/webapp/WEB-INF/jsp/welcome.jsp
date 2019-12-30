<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Witaj</title>
</head>

<body>
<h1>Witaj</h1>
<p>Strona umożliwia przechowywanie i udostępnianie użytkownikom notatek w postaci plików .pdf.</p>
<a href="/login">Zaloguj się!</a>
Nie masz konta? <a href="/signup">Zarejestruj się!</a>

<h1>kontener</h1>
<div class="container">
    <table>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>