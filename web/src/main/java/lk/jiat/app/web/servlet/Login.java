package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.exception.LoginFailedException;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.model.UserType;
import lk.jiat.app.core.service.UserService;
import lk.jiat.app.core.util.Encryption;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(email, Encryption.encrypt(password)));

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

        System.out.println("status: " + status);

        User user = userService.getUserByEmail(email);

        if (user.getCustomer().getStatus().equals(Status.ACTIVE)) {

            if (status == AuthenticationStatus.SUCCESS) {

                if (user.getUserType() == UserType.ADMIN) {
                    request.getSession().setAttribute("admin", user);
                    response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
                } else if (user.getUserType() == UserType.USER) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/user/index.jsp");

                } else {
                    throw new LoginFailedException("Inactive User");
                }


            } else {
                throw new LoginFailedException("Invalid email or password");
            }

        } else {
            request.getSession().invalidate();
            throw new LoginFailedException("Invalid User Role");
        }

    }
}
