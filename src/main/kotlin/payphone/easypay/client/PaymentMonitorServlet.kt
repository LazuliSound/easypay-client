package payphone.easypay.client

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/monitor"])
open class PaymentMonitorServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.setAttribute("paymentId", req.getParameter("paymentId"))

        servletContext.getRequestDispatcher("/WEB-INF/jsp/monitor.jsp").forward(req, resp)
    }
}
