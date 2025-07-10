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

    System.out.println("Fixed deposit Account Id" + accountId);
%>

</body>
</html>
