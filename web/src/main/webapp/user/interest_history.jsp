<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.model.Transaction" %>
<%@ page import="lk.jiat.app.core.service.TransactionService" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.service.InterestService" %>
<%@ page import="lk.jiat.app.core.model.InterestAccrual" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/15/2025
  Time: 6:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Interest Accrual History</h1>

<c:if test="${empty param.account_id}">
    <%
        response.sendRedirect(request.getContextPath() + "/user");
    %>
</c:if>

<%
    String accountIdStr = request.getParameter("account_id");
    Long accountId = null;
    if (accountIdStr != null) {
        accountId = Long.parseLong(accountIdStr);
        pageContext.setAttribute("selectedAccountId", accountId);
    }

    System.out.println("interest history id"+accountId);
%>

<%
    try {
        String email = request.getUserPrincipal().getName();

        InitialContext ic = new InitialContext();
        InterestService interestService = (InterestService) ic.lookup("java:global/banking_system_ear/ejb-module/InterestSessionBean!lk.jiat.app.core.service.InterestService");

        if (accountId != null) {
            List<InterestAccrual> interestAccrualsList = interestService.getInterestAccruals(accountId, email);

            pageContext.setAttribute("interestAccrualsList", interestAccrualsList);

        }


    } catch (NamingException e) {
        throw new RuntimeException(e);
    }
%>


<c:choose>
    <c:when test="${not empty interestAccrualsList}">
        <c:forEach var="interest" items="${interestAccrualsList}">
            <p>Interest ID: ${interest.id}</p>
            <p>Interest Account No: ${interest.account.accountNumber}</p>
            <p>Interest Amount: ${interest.amount}</p>
            <p>Interest Date: ${interest.accrualDate}</p>
            <hr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>No Interest found for this user.</p>
    </c:otherwise>
</c:choose>


</body>
</html>
