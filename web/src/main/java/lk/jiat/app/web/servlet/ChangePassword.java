package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.service.UserService;
import lk.jiat.app.core.util.Encryption;

import java.io.IOException;

@WebServlet("/user/changePassword")
public class ChangePassword extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String email = request.getUserPrincipal().getName();

        response.setContentType("text/plain");

        User user = userService.getUserByEmail(email);


        if (currentPassword == null || newPassword == null || confirmPassword == null || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            response.getWriter().write("All fields are required.");
            return;
        }

        if (user == null) {
            response.getWriter().write("User not found.");
            return;
        }

        String currentHash = Encryption.encrypt(currentPassword);

        if (!currentHash.equals(user.getPasswordHash())) {
            response.getWriter().write("Current password is incorrect.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            response.getWriter().write("New password and confirm password do not match.");
            return;
        }

        String newHash = Encryption.encrypt(newPassword);
        user.setPasswordHash(newHash);
        userService.updateUser(user);

        response.sendRedirect(request.getContextPath() + "/user/index.jsp");
    }
}
