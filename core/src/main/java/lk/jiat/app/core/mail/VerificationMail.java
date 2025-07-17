package lk.jiat.app.core.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

import java.util.Base64;

public class VerificationMail extends Mailable {

    private final String to;
    private final String password;

    public VerificationMail(String to, String password) {
        this.to = to;
        this.password = password;
    }

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Bank Login Password");

        String encoded = Base64.getEncoder().encodeToString(to.getBytes());
        String link = "http://localhost:8080/banking_system/verify?id=" + encoded;

        String body = String.format(
                "<html>\n" +
                        "<body>\n" +
                        "<p>Hello,</p>\n" +
                        "<p>First click this link to activate your account:</p>\n" +
                        "<p><a href=\"%s\">%s</a></p>\n" +
                        "<p>This is your Bank website login password: <strong>%s</strong></p>\n" +
                        "<p>Thank you!</p>\n" +
                        "</body>\n" +
                        "</html>",
                link, link, password
        );


        message.setContent(body, "text/html; charset=utf-8");
    }
}
