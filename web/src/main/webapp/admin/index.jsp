<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/8/2025
  Time: 1:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin | Home</title>
</head>
<body>
    <c:if test="${not empty pageContext.request.userPrincipal}">
        <h1>Welcome, ${pageContext.request.userPrincipal.name}</h1>

        <%--        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">--%>
        <%--            <h2>Your role is: <strong>ADMIN</strong></h2>--%>
        <%--        </c:if>--%>
        <%--        <c:if test="${pageContext.request.isUserInRole('USER')}">--%>
        <%--            <h2>Your role is: <strong>USER</strong></h2>--%>
        <%--        </c:if>--%>

        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </c:if>

</body>
</html>
