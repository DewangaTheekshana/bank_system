<%@ page import="jakarta.ejb.EJB" %>
<%@ page import="lk.jiat.app.core.service.TransactionService" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.model.User" %>
<%@ page import="lk.jiat.app.core.model.Transaction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transaction Success</title>
</head>
<body>

<h1>Transaction Success ✔</h1>

<%
    long txnId = 0;
    try {
        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        TransactionService transactionService = (TransactionService) ic.lookup(
                "java:global/banking_system_ear/ejb-module/TransactionSessionBean!lk.jiat.app.core.service.TransactionService");

        User user = (User) request.getSession().getAttribute("user");

        Transaction transaction = transactionService.getTransactionByCustomer(user.getCustomer().getId());
        txnId = transaction.getId();

        System.out.println("Transaction ID: " + txnId);

    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>

<p>
    <a href="<%= request.getContextPath() %>/transferReceipt?id=<%= txnId %>">Download Receipt</a>
</p>

<p>
    <a href="index.jsp">Back To Home</a>
</p>

</body>
</html>
