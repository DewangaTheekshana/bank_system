<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.service.TransactionService" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/8/2025
  Time: 9:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Money Transfer</h1>

<%
    try {
        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        AccountService accountService = (AccountService) ic.lookup("java:global/banking_system_ear/ejb-module/AccountSessionBean!lk.jiat.app.core.service.AccountService");

        List<Account> accounts = accountService.getAccountByUserEmail(email);

        pageContext.setAttribute("accounts", accounts);


    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>

<form action="${pageContext.request.contextPath}/transfer" method="post">


    <p>Source Account No</p>
    <select name="sourceAccountNo" required>
        <c:forEach var="account" items="${accounts}">
            <c:if test="${account.accountType eq 'SAVING'}">
                <option value="${account.accountNumber}">
                        ${account.accountNumber}
                </option>
            </c:if>
        </c:forEach>
    </select>

    <p>Destination Account No</p>
    <input type="text" name="destinationAccountNo">
    <p>Amount</p>
    <input type="text" name="amount">
    <p>Description</p>
    <input type="text" name="description">

    <br>
    <br>
    <br>

    <input type="submit" value="Transfer">
</form>
</body>
</html>
