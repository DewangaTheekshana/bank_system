        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="lk.jiat.app.core.model.User" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.app.core.model.Account" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.app.core.service.AccountService" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.app.core.service.UserService" %>
        <%@ page import="lk.jiat.app.core.util.Encryption" %><%--
  Created by IntelliJ IDEA.
  User: HUNT GADGETS
  Date: 7/15/2025
  Time: 3:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Profile</h1>
    <%

        User user;
        try {

            String email = request.getUserPrincipal().getName();

            InitialContext ic = new InitialContext();
            UserService userService = (UserService) ic.lookup("java:global/banking_system_ear/ejb-module/UserSessionBean!lk.jiat.app.core.service.UserService");
            user = userService.getUserByEmail(email);
            pageContext.setAttribute("user", user);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    %>

    <c:choose>
        <c:when test="${not empty user}">


            <p>First Name: ${user.customer.firstName}</p>
            <p>Last Name: ${user.customer.lastName}</p>
            <p>UserName: ${user.username}</p>
            <p>Date Of Birth: ${user.customer.dateOfBirth}</p>
            <p>Email: ${user.customer.email}</p>
            <p>Mobile: ${user.customer.phoneNumber}</p>
            <p>Address: ${user.customer.address}</p>

            <!-- Button to open modal -->
            <button type="button" onclick="document.getElementById('changePasswordModal').style.display='block'">
                Change Password
            </button>

            <hr>
        </c:when>
        <c:otherwise>
            <p>No account found</p>
        </c:otherwise>
    </c:choose>



    <div id="changePasswordModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
    background-color: rgba(0,0,0,0.5);">
        <div style="background:#fff; margin:10% auto; padding:20px; width:300px; border-radius:5px; position:relative;">
            <h3>Change Password</h3>

            <form action="${pageContext.request.contextPath}/user/changePassword" method="post">
                <input type="password" name="currentPassword" placeholder="Current password" required><br><br>
                <input type="password" name="newPassword" placeholder="New password" required><br><br>
                <input type="password" name="confirmPassword" placeholder="Confirm password" required><br><br>
                <button type="submit">Update Password</button>
            </form>
        </div>
    </div>


</body>
</html>
