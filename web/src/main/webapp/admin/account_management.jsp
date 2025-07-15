<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="javax.naming.NamingException" %><%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/15/2025
  Time: 5:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Account Management</h1>

<%
    try {
        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        AccountService accountService = (AccountService) ic.lookup(
                "java:global/banking_system_ear/ejb-module/AccountSessionBean!lk.jiat.app.core.service.AccountService"
        );

        String searchEmail = request.getParameter("searchEmail");

        List<Account> allAccounts;
        if (searchEmail != null && !searchEmail.trim().isEmpty()) {
            allAccounts = accountService.getAccountByUserEmail(searchEmail.trim());

            if (allAccounts == null) {
                allAccounts = java.util.Collections.emptyList();
            }

        } else {
            allAccounts = accountService.getAllAccounts();
        }

        pageContext.setAttribute("allAccounts", allAccounts);

    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>

<form method="post" action="">
    <label for="searchEmail">Search by Email:</label>
    <input type="text" name="searchEmail" id="searchEmail" value="${param.searchEmail}">
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/admin/account_management.jsp">Reset</a>
</form>

<c:choose>
    <c:when test="${not empty allAccounts}">

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Account Number</th>
                <th>Account Type</th>
                <th>Account Created Date</th>
                <th>Status</th>
            </tr>
            <c:forEach var="account" items="${allAccounts}">
            <tr>
                <td>${account.id}</td>
                <td>
                        ${account.customer.firstName}
                        ${account.customer.lastName}
                </td>
                <td>${account.customer.email}</td>
                <td>${account.accountNumber}</td>
                <td>${account.accountType}</td>
                <td>${account.createdAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${account.status eq 'ACTIVE'}">
                            <form action="${pageContext.request.contextPath}/changeAccountStatus" method="post">
                                <input type="hidden" name="accountNo" value="${account.accountNumber}">
                                <input type="submit" name="status" value="Active">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/changeAccountStatus" method="post">
                                <input type="hidden" name="accountNo" value="${account.accountNumber}">
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
        <p>No Account Found.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
