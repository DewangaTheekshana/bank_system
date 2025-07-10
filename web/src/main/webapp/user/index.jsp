<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/8/2025
  Time: 1:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User | Home</title>
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


<%
    try {

        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        AccountService accountService = (AccountService) ic.lookup("java:global/banking_system_ear/ejb-module/AccountSessionBean!lk.jiat.app.core.service.AccountService");
        List<Account> account = accountService.getAccountByUserEmail(email);
        pageContext.setAttribute("account", account);

    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>

<c:choose>
    <c:when test="${not empty account}">
        <c:forEach var="account" items="${account}">
            <p>Account Number: ${account.accountNumber}</p>
            <p>Account Balance: ${account.balance}</p>
            <p>Account Type: ${account.accountType}</p>

            <c:choose>
                <c:when test="${account.status eq 'ACTIVE'}">
                    <p style="color: green">
                        Account Status: <strong>${account.status}</strong>
                    </p>
                </c:when>
                <c:otherwise>
                    <p style="color: red">
                        Account Status: <strong>${account.status}</strong>
                    </p>
                </c:otherwise>
            </c:choose>

            <a style="
                    cursor: pointer;
                    outline: 0;
                    display: inline-block;
                    font-weight: 400;
                    line-height: 1.5;
                    text-align: center;
                    background-color: transparent;
                    border: 1px solid transparent;
                    padding: 6px 12px;
                    font-size: 1rem;
                    border-radius: .25rem;
                    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
                    color: #0d6efd;
                    border-color: #0d6efd;
                    :hover {
                        color: #fff;
                        background-color: #0d6efd;
                        border-color: #0d6efd;
                    }
                " href="${pageContext.request.contextPath}/user/transaction_history.jsp?accountId=${account.id}">View Transactions</a>

            <hr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>No accounts found for this user.</p>
    </c:otherwise>
</c:choose>


<a style="
                    cursor: pointer;
                    outline: 0;
                    display: inline-block;
                    font-weight: 400;
                    line-height: 1.5;
                    text-align: center;
                    background-color: transparent;
                    border: 1px solid transparent;
                    padding: 6px 12px;
                    font-size: 1rem;
                    border-radius: .25rem;
                    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
                    color: #0d6efd;
                    border-color: #0d6efd;
                    :hover {
                        color: #fff;
                        background-color: #0d6efd;
                        border-color: #0d6efd;
                    }
                " href="${pageContext.request.contextPath}/user/money_transfer.jsp">Money Transfer</a>

</body>
</html>