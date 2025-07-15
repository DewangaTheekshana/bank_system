<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.service.TransactionService" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="lk.jiat.app.core.service.ScheduleService" %>
<%@ page import="lk.jiat.app.core.model.*" %>
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

    <a href="register_customer.jsp">Register Customer</a>
    <a href="customer_management.jsp">Customer Management</a>
    <a href="account_management.jsp">Account Management</a>

    <h1>Admin Dashboard</h1>


    <%

        try {

            String email = request.getUserPrincipal().getName();

            InitialContext ic = new InitialContext();
            AccountService accountService = (AccountService) ic.lookup("java:global/banking_system_ear/ejb-module/AccountSessionBean!lk.jiat.app.core.service.AccountService");
            List<Account> account = accountService.getAllActiveAccounts(Status.ACTIVE);
            pageContext.setAttribute("activeAccount", account.size());
            pageContext.setAttribute("account", account);

            TransactionService transactionService = (TransactionService) ic.lookup("java:global/banking_system_ear/ejb-module/TransactionSessionBean!lk.jiat.app.core.service.TransactionService");
            List<Transaction> dailyTransactionVolume = transactionService.getDailyTransactionVolume(LocalDate.now());

            double totalVolume = dailyTransactionVolume.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            pageContext.setAttribute("totalVolume", totalVolume);

            pageContext.setAttribute("dailyTransactionVolume", dailyTransactionVolume);

            ScheduleService scheduleService = (ScheduleService) ic.lookup("java:global/banking_system_ear/ejb-module/ScheduleSessionBean!lk.jiat.app.core.service.ScheduleService");
            List<Scheduled_Transfer> activeSchedules = scheduleService.getActiveSchedules(ScheduledStatusType.ACTIVE);

            pageContext.setAttribute("activeSchedules", activeSchedules);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    %>


<c:choose>
    <c:when test="${not empty account}">


        <p>Active Account Count: ${account.size()}</p>

        <p>Today Total Transaction Volume: Rs.${totalVolume}</p>

        <p>Active Scheduled Transaction Count: ${activeSchedules.size()}</p>

        <hr>
    </c:when>
    <c:otherwise>
        <p>No account found</p>
    </c:otherwise>
</c:choose>

</body>
</html>
