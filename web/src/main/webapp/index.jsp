<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome,</h1>

<c:choose>
    <c:when test="${empty pageContext.request.userPrincipal}">
        <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
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
