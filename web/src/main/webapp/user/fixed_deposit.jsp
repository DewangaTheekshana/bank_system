<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="java.util.Optional" %>
<%@ page import="lk.jiat.app.core.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/10/2025
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${empty param.accountId}">
    <%
        response.sendRedirect(request.getContextPath() + "/user");
    %>
</c:if>


<%
    String accountIdStr = request.getParameter("accountId");
    Long accountId = null;
    if (accountIdStr != null) {
        accountId = Long.parseLong(accountIdStr);
        pageContext.setAttribute("selectedAccountId", accountId);
    }
%>

<%
    try {
        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        AccountService accountService = (AccountService) ic.lookup("java:global/banking_system_ear/ejb-module/AccountSessionBean!lk.jiat.app.core.service.AccountService");

        if (accountId != null) {
            Account fixDepositAccount = accountService.getAccountByAccountIdAndEmailAndAccountType(accountId, email, "FIXED");

            pageContext.setAttribute("fixDepositAccount", fixDepositAccount);

        }


    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>


<h1>Fixed Account Details</h1>

<c:choose>
    <c:when test="${not empty fixDepositAccount}">
        <p>Account Number: ${fixDepositAccount.accountNumber}</p>
        <p>Account Balance: ${fixDepositAccount.balance}</p>
        <p>Account Interest Rate: ${fixDepositAccount.interestRate} %</p>
        <p>Account Status: ${fixDepositAccount.status}</p>
        <hr>
    </c:when>
    <c:otherwise>
        <p>No account found</p>
    </c:otherwise>
</c:choose>

</body>
</html>
