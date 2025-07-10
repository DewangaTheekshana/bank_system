<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/7/2025
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Login</h1>
<c:choose>
    <c:when test="${empty pageContext.request.userPrincipal}">
        <form method="POST" action="${pageContext.request.contextPath}/login">
            <table>
                <tr>
                    <th>Email</th>
                    <td><input type="email" name="email"></td>
                </tr>
                <tr>
                    <th>Password</th>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </table>
        </form>
    </c:when>

    <c:otherwise>
        <c:choose>
            <c:when test="${pageContext.request.isUserInRole('ADMIN')}">
                <c:redirect url="/admin/index.jsp"/>
            </c:when>

            <c:when test="${pageContext.request.isUserInRole('USER')}">
                <c:redirect url="/user/index.jsp"/>
            </c:when>

            <c:otherwise>
                <p>Invalid user role. Please contact support.</p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

</body>
</html>
