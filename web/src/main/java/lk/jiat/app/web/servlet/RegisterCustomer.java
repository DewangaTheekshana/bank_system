package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.mail.VerificationMail;
import lk.jiat.app.core.model.*;
import lk.jiat.app.core.provider.MailServiceProvider;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.CustomerService;
import lk.jiat.app.core.service.RegisterService;
import lk.jiat.app.core.service.UserService;
import lk.jiat.app.core.util.Encryption;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@WebServlet("/admin/registerCustomer")
public class RegisterCustomer extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @EJB
    private AccountService accountService;

    @EJB
    private RegisterService registerService;

    @EJB
    private UserService userService;

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
            registerService.registerAccount(email, generateAccountNumber(), "FIXED", fixedDeposit, BigDecimal.valueOf(5.0), Status.ACTIVE, LocalDateTime.now());
        }


        String pass = generatePassword();
        String encryptPass = Encryption.encrypt(pass);

        userService.addUser(email, encryptPass, UserType.USER);

        System.out.println("password"+" "+ pass +" "+ encryptPass);

        VerificationMail mail = new VerificationMail(email, pass);
        MailServiceProvider.getInstance().sendMail(mail);

        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private String generateAccountNumber() {
        int min = 100_000_000;
        int max = 999_999_999;
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        return String.valueOf(randomNum);
    }

    private String generatePassword() {
        Random random = new Random();
        int password = 100_000 + random.nextInt(900_000); // ensures 6 digits
        return String.valueOf(password);
    }

    private double parseDoubleOrZero(String str) {
        if (str == null || str.trim().isEmpty()) return 0.0;
        return Double.parseDouble(str);
    }
}
