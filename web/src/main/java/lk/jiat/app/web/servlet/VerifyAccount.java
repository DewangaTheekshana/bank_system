package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.service.CustomerService;
import lk.jiat.app.core.service.UserService;

import java.io.IOException;
import java.util.Base64;

@WebServlet("/verify")
public class VerifyAccount extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        byte[] bytes = Base64.getDecoder().decode(id);
        String email = new String(bytes);

        Customer customer = customerService.findByEmail(email);
        if (customer != null) {
            customer.setStatus(Status.ACTIVE);
            customerService.updateCustomer(customer);
        }

        response.sendRedirect(request.getContextPath() + "/user");

    }
}
