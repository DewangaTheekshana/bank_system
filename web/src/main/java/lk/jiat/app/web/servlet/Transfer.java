package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.TransferService;

import java.io.IOException;
import java.util.List;

@WebServlet("/transfer")
public class Transfer extends HttpServlet {

    @EJB
    private TransferService transferService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sourceAccountNo = request.getParameter("sourceAccountNo");
        String destinationAccountNo = request.getParameter("destinationAccountNo");
        String amountStr = request.getParameter("amount");
        String description = request.getParameter("description");

        if (sourceAccountNo == null || sourceAccountNo.isBlank() ||
                destinationAccountNo == null || destinationAccountNo.isBlank() ||
                amountStr == null || amountStr.isBlank() ||
                description == null || description.isBlank()) {

            response.setContentType("text/plain");
            response.getWriter().println("Please fill in all transaction fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                response.setContentType("text/plain");
                response.getWriter().println("Amount must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            response.setContentType("text/plain");
            response.getWriter().println("Invalid amount value.");
            return;
        }

        String email = request.getUserPrincipal().getName();

        Account desAcc = accountService.getAccountByAccountNumber(destinationAccountNo);
        List<Account> loginUserAccount = accountService.getAccountByUserEmail(email);
        String accountNumber = loginUserAccount.get(0).getAccountNumber();

        if (!accountNumber.equals(sourceAccountNo)) {

            response.setContentType("text/plain");
            response.getWriter().println("Account number does not match.");

        }else if (desAcc == null) {

            response.setContentType("text/plain");
            response.getWriter().println("Destination Account Account not found.");

        }else if (desAcc.getAccountType().equals("FIXED")){

            response.setContentType("text/plain");
            response.getWriter().println("Destination Account Account type is fixed deposit account.");

        }else {

            transferService.transferAmount(sourceAccountNo, destinationAccountNo, description, amount);

            response.sendRedirect(request.getContextPath() + "/user/index.jsp");

        }

    }
}
