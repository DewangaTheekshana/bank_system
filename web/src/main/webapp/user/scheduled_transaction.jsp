<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.service.ScheduleService" %>
<%@ page import="lk.jiat.app.core.model.Scheduled_Transfer" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.model.ScheduledStatusType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/18/2025
  Time: 12:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Scheduled Transaction</h1>

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
            ScheduleService scheduleService = (ScheduleService) ic.lookup("java:global/banking_system_ear/ejb-module/ScheduleSessionBean!lk.jiat.app.core.service.ScheduleService");

            if (accountId != null) {
                List<Scheduled_Transfer> scheduleTransactionsList = scheduleService.getScheduleTransactionsByAccountId(accountId, email);

                pageContext.setAttribute("scheduleTransactionsList", scheduleTransactionsList);

            }


        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    %>

    <c:choose>
        <c:when test="${not empty scheduleTransactionsList}">
            <c:forEach var="transaction" items="${scheduleTransactionsList}">
                <p>Transaction ID: ${transaction.id}</p>
                <p>Transaction Account No: ${transaction.sourceAccount.accountNumber}</p>
                <p>Transaction Amount: ${transaction.amount}</p>
                <p>Transaction Related Account No: ${transaction.targetAccount.accountNumber}</p>
                <p>Transaction Related Account Name:
                        ${transaction.targetAccount.customer.firstName}
                        ${transaction.targetAccount.customer.lastName}
                </p>
                <a href="${pageContext.request.contextPath}/user/cancel?id=${transaction.id}">Cancel</a>
                <hr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>No transactions found for this user.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>
