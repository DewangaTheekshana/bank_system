package lk.jiat.app.web.servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.Transaction;
import lk.jiat.app.core.service.TransactionService;

import java.io.IOException;

@WebServlet("/user/transferReceipt")
public class TransferReceipt extends HttpServlet {

    @EJB
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Transaction ID is required");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transaction ID");
            return;
        }

        Transaction transaction = transactionService.getTransactionByTransactionId(id);
        if (transaction == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=transaction_" + id + ".pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Transaction Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Empty line

            // Table with 2 columns
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            // Helper method to add rows
            table.addCell(new Phrase("Transaction ID", headerFont));
            table.addCell(String.valueOf(transaction.getId()));

            table.addCell(new Phrase("Source Account", headerFont));
            table.addCell(transaction.getAccount().getAccountNumber());

            table.addCell(new Phrase("Destination Account", headerFont));
            table.addCell(transaction.getRelatedAccount().getAccountNumber());

            table.addCell(new Phrase("Amount", headerFont));
            table.addCell(String.format("%.2f", transaction.getAmount()));

            table.addCell(new Phrase("Description", headerFont));
            table.addCell(transaction.getDescription());

            table.addCell(new Phrase("Date & Time", headerFont));
            table.addCell(transaction.getCreatedAt().toString());

            document.add(table);

            Paragraph thankYou = new Paragraph("Thank you for banking with us.");
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            document.close();

        } catch (Exception e) {
            throw new ServletException("Error generating PDF", e);
        }

    }

}
