package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.service.CustomerService;
import lk.jiat.app.core.service.UserService;

import java.io.IOException;

@WebServlet("/changeUserStatus")
public class ChangeUserStatus extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String status = request.getParameter("status");
        String email = request.getParameter("userEmail");

        String s = "";

        if (status.equals("Active")) {
            s = customerService.UpdateCustomerStatus(email, Status.INACTIVE);
        }else {
            s = customerService.UpdateCustomerStatus(email, Status.ACTIVE);
        }

        if (s.equals("Customer updated")){
            response.sendRedirect(request.getContextPath() + "/admin/customer_management.jsp");
        }else {
            response.getWriter().print("Customer updated Error");
        }



    }
}
