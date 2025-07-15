package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.*;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.CustomerService;
import lk.jiat.app.core.service.RegisterService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/registerCustomer")
public class RegisterCustomer extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @EJB
    private AccountService accountService;

    @EJB
    private RegisterService registerService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phoneNumber");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");

        String savingInitialDepositStr = request.getParameter("savingInitialDeposit");
        String createFixedStr = request.getParameter("createFixed");
        String fixedInitialDepositStr = request.getParameter("fixedInitialDeposit");

        double savingDeposit = parseDoubleOrZero(savingInitialDepositStr);
        boolean createFixed = createFixedStr != null;
        double fixedDeposit = parseDoubleOrZero(fixedInitialDepositStr);

        if (firstName == null || lastName == null || email == null || phone == null || dob == null || address == null || savingDeposit == 0) {
            response.getWriter().println("plz fill all fields");
            return;
        }

        if (customerService.findByEmail(email) != null) {
            response.getWriter().println("email already exists");
            return;
        }

        if (customerService.findByPhone(phone) != null) {
            response.getWriter().println("phone already exists");
            return;
        }

        if (savingDeposit <= 0) {
            response.getWriter().println("savingDeposit must be greater than 0");
            return;
        }

        LocalDate ValidDob = LocalDate.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);

        if (ValidDob.isAfter(LocalDate.now())) {
            response.getWriter().println("Date of birth cannot be in the future.");
            return;
        }

        // Create Customer
        Customer customer = new Customer(
                firstName,
                lastName,
                email,
                phone,
                address,
                LocalDate.parse(dob),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Status.INACTIVE
        );


        registerService.registerCustomer(customer);
        registerService.registerAccount(email, generateAccountNumber(), "SAVING", savingDeposit, BigDecimal.valueOf(2.0), Status.ACTIVE, LocalDateTime.now());

        if (createFixed) {
            registerService.registerAccount(email, generateAccountNumber(),"FIXED", fixedDeposit, BigDecimal.valueOf(5.0), Status.ACTIVE, LocalDateTime.now());
        }

        response.sendRedirect(request.getContextPath() + "/admin/register_customer.jsp");
    }

    private String generateAccountNumber() {
        int min = 100_000_000;
        int max = 999_999_999;
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        return String.valueOf(randomNum);
    }

    private double parseDoubleOrZero(String str) {
        if (str == null || str.trim().isEmpty()) return 0.0;
        return Double.parseDouble(str);
    }
}
