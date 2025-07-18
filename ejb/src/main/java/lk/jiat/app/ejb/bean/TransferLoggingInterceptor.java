package lk.jiat.app.ejb.bean;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.servlet.jsp.PageContext;
import lk.jiat.app.core.model.Audit;
import lk.jiat.app.core.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Interceptor
public class TransferLoggingInterceptor {

    private static final String LOG_FILE = "transfer_logs.txt";

    @PersistenceContext
    private EntityManager em;

    @AroundInvoke
    public Object logTransfer(InvocationContext ctx) throws Exception {
        LocalDateTime start = LocalDateTime.now();

        System.out.println("Start: " + start);

        Object result = null;
        Exception toThrow = null;
        boolean success = true;

        try {
            result = ctx.proceed();
        } catch (Exception e) {
            toThrow = e;
            success = false;
        }

        LocalDateTime end = LocalDateTime.now();

        StringBuilder details = new StringBuilder();
        details.append("Method: ").append(ctx.getMethod().getName()).append(", ");
        details.append("Parameters: ");
        Object[] params = ctx.getParameters();
        if (params != null && params.length > 0) {
            for (Object p : params) {
                details.append(p).append(", ");
            }
            details.setLength(details.length() - 2); // remove last comma & space
        } else {
            details.append("none");
        }
        details.append(", Duration: ").append(java.time.Duration.between(start, end).toMillis()).append(" ms");

        Audit audit = new Audit(
                ctx.getMethod().getName(),
                start,
                success,
                details.toString()
        );

        em.persist(audit);

        System.out.println("Writing log to: " + new File(LOG_FILE).getAbsolutePath());

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)))) {
            out.print("Timestamp: " + start + "\n");
            out.print(details.toString() + "\n");
            if (!success) {
                out.print("Exception: " + toThrow.getMessage() + "\n");
            }
            out.print("------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (toThrow != null) {
            throw toThrow;
        }

        return result;
    }
}
