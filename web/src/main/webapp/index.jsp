<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome,</h1>

<c:if test="${empty pageContext.request.userPrincipal}">
    <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
</c:if>
<c:if test="${not empty pageContext.request.userPrincipal}">
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</c:if>

</body>
</html>
