package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Transaction;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.TransactionService;
import lk.jiat.app.core.service.TransferService;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/transfer")
public class Transfer extends HttpServlet {

    @EJB
    private TransferService transferService;

    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

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
            return;

        }

        if (desAcc == null) {

            response.setContentType("text/plain");
            response.getWriter().println("Destination Account Account not found.");
            return;

        }

        if (loginUserAccount.get(0).getStatus().name().equals("INACTIVE")) {

            response.setContentType("text/plain");
            response.getWriter().println("Inactive account found.");
            return;

        }

        if (desAcc.getAccountType().equals("FIXED")) {

            response.setContentType("text/plain");
            response.getWriter().println("Destination Account Account type is fixed deposit account.");
            return;

        }

        if (loginUserAccount.get(0).getBalance() < amount) {

            response.setContentType("text/plain");
            response.getWriter().println("Your available balance is insufficient.");
            return;

        }

        transferService.transferAmount(sourceAccountNo, destinationAccountNo, description, amount);


        response.sendRedirect(request.getContextPath()+ "/user/transaction_success.jsp");

    }
}
