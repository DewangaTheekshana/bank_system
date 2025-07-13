<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.model.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.service.TransactionService" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.service.UserService" %>
<%@ page import="lk.jiat.app.core.model.User" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="lk.jiat.app.core.model.Account" %><%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/9/2025
  Time: 2:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transactional History</title>
</head>
<body>
    <h1>Transactional History</h1>


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
            TransactionService transactionService = (TransactionService) ic.lookup("java:global/banking_system_ear/ejb-module/TransactionSessionBean!lk.jiat.app.core.service.TransactionService");

            if (accountId != null) {
                List<Transaction> transactionList = transactionService.getTransactionsByAccountId(accountId, email);

                pageContext.setAttribute("transactionList", transactionList);

            }


        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    %>

    <c:choose>
        <c:when test="${not empty transactionList}">
            <c:forEach var="transaction" items="${transactionList}">
                <p>Transaction ID: ${transaction.id}</p>
                <p>Transaction Account No: ${transaction.account.accountNumber}</p>
                <p>Transaction Amount: ${transaction.amount}</p>
                <p>Transaction Description: ${transaction.description}</p>
                <p>Transaction Related Account No: ${transaction.relatedAccount.accountNumber}</p>
                <p>Transaction Related Account Name:
                        ${transaction.relatedAccount.customer.firstName}
                        ${transaction.relatedAccount.customer.lastName}
                </p>
                <c:choose>
                    <c:when test="${transaction.account.id != selectedAccountId}">
                        <p style="color: green">Credited</p>
                    </c:when>
                    <c:otherwise>
                        <p style="color: red">Debited</p>
                    </c:otherwise>
                </c:choose>
                <hr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>No transactions found for this user.</p>
        </c:otherwise>
    </c:choose>



</body>
</html>
