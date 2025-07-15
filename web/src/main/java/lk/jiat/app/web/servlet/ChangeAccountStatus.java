package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.service.AccountService;

import java.io.IOException;

@WebServlet("/changeAccountStatus")
public class ChangeAccountStatus extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String accountNo = request.getParameter("accountNo");

        String a = "";

        if (status.equals("Active")) {
            a = accountService.UpdateAccountStatus(accountNo, Status.INACTIVE);
        }else {
            a = accountService.UpdateAccountStatus(accountNo, Status.ACTIVE);
        }

        if (a.equals("Account updated")){
            response.sendRedirect(request.getContextPath() + "/admin/account_management.jsp");
        }else {
            response.getWriter().print("Account updated Error");
        }

    }
}
