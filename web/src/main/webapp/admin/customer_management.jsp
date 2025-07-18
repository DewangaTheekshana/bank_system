<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.service.UserService" %>
<%@ page import="lk.jiat.app.core.model.User" %>
<%@ page import="java.util.Collections" %><%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/15/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Customer Management</h1>

<%

    try {

        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        UserService userService = (UserService) ic.lookup("java:global/banking_system_ear/ejb-module/UserSessionBean!lk.jiat.app.core.service.UserService");

        String searchEmail = request.getParameter("searchEmail");

        List<User> allUser;
        if (searchEmail != null && !searchEmail.trim().isEmpty()){
            User user = userService.getUserByEmail(searchEmail.trim());

            if (user != null){
                allUser = Collections.singletonList(user);
            }else {
                allUser = Collections.emptyList();
            }

        }else {
            allUser = userService.getAllUsers();
        }

        pageContext.setAttribute("allUsers", allUser);

    } catch (NamingException e) {
        throw new RuntimeException(e);
    }

%>

<form method="post" action="">
    <label for="searchEmail">Search by Email:</label>
    <input type="text" name="searchEmail" id="searchEmail" value="${param.searchEmail}">
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/admin/customer_management.jsp">Reset</a>
</form>

<br/>

<c:choose>
    <c:when test="${not empty allUsers}">

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>DOB</th>
                <th>Mobile</th>
                <th>Account Created Date</th>
                <th>Status</th>
            </tr>
            <c:forEach var="user" items="${allUsers}">
            <tr>
                <td>${user.id}</td>
                <td>
                        ${user.customer.firstName}
                        ${user.customer.lastName}
                </td>
                <td>${user.customer.email}</td>
                <td>${user.customer.dateOfBirth}</td>
                <td>${user.customer.phoneNumber}</td>
                <td>${user.customer.createdAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.customer.status eq 'ACTIVE'}">
                            <form action="${pageContext.request.contextPath}/admin/changeUserStatus" method="post">
                                <input type="hidden" name="userEmail" value="${user.customer.email}">
                                <input type="submit" name="status" value="Active">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/admin/changeUserStatus" method="post">
                                <input type="hidden" name="userEmail" value="${user.customer.email}">
                                <input type="submit" name="status" value="Inactive">
                            </form>
                        </c:otherwise>
                    </c:choose>
                </td>
            <tr>
                </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p>No User Found.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
