package lk.jiat.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.app.core.model.ScheduledStatusType;
import lk.jiat.app.core.model.Scheduled_Transfer;
import lk.jiat.app.core.service.ScheduleService;

import java.io.IOException;

@WebServlet("/user/cancel")
public class canselSchedule extends HttpServlet {

    @EJB
    private ScheduleService scheduleService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        try {
            long transferId = Long.parseLong(id);
            Scheduled_Transfer transaction = scheduleService.getScheduleTransactionById(transferId);

            if (transaction == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Scheduled transfer not found.");
                return;
            }

            transaction.setStatus(ScheduledStatusType.FAILED);
            scheduleService.updateSchedule(transaction);

            response.setContentType("text/plain");
            response.getWriter().write("Scheduled transfer cancelled.");

            response.sendRedirect(request.getContextPath() + "/user/scheduled_transaction.jsp");


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid transfer ID.");
        }


    }
}
