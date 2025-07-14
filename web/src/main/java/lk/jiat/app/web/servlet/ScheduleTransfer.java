package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.ScheduleService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/schedule_transfer")
public class ScheduleTransfer extends HttpServlet {

    @EJB
    AccountService accountService;

    @EJB
    ScheduleService scheduleService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String loggedInUserEmail = request.getUserPrincipal().getName();

        String sourceAccountNo = request.getParameter("sourceAccountNo");
        String destinationAccountNo = request.getParameter("destinationAccountNo");
        String amountStr = request.getParameter("amount");
        String dateTimeStr = request.getParameter("onetimeDateTime");


        if (sourceAccountNo == null || destinationAccountNo == null || amountStr == null || dateTimeStr == null ||
                sourceAccountNo.isBlank() || destinationAccountNo.isBlank() || amountStr.isBlank() || dateTimeStr.isBlank()) {

            response.getWriter().println("Please fill in all fields.");
            return;
        }

        try {

            double amount = Double.parseDouble(amountStr);

            LocalDateTime scheduledDateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            if (scheduledDateTime.isBefore(LocalDateTime.now())) {
                response.getWriter().println("❌ Scheduled date and time must be in the future.");
                return;
            }

            if (amount <= 0) {
                response.getWriter().println("Amount must be greater than zero.");
                return;
            }

            Account sourceAccount = accountService.getAccountByAccountNumber(sourceAccountNo);
            Account destinationAccount = accountService.getAccountByAccountNumber(destinationAccountNo);

            if (sourceAccount == null || destinationAccount == null) {
                response.getWriter().println("Invalid account numbers.");
                return;
            }

            scheduleService.saveSchedule(sourceAccount, destinationAccount, amount, scheduledDateTime, loggedInUserEmail);

            response.sendRedirect(request.getContextPath() + "/user/index.jsp");

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid amount.");
        } catch (Exception e) {
            throw new ServletException("Error scheduling transfer", e);
        }

    }
}
