package payphone.easypay.client

import javax.inject.Inject
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/request"])
open class PaymentRequestServlet : HttpServlet() {
    @Inject
    private lateinit var serviceProvider: PaymentServiceProvider

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.setAttribute("paymentMethods", serviceProvider.getApi().paymentMethods)

        servletContext.getRequestDispatcher("/WEB-INF/jsp/request.jsp").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val paymentMethodId = req.getParameter("paymentMethodId")!!
        val amount = req.getParameter("amount").toBigDecimal()

        val paymentId = serviceProvider.getApi().beginPayment(paymentMethodId, amount)
        resp.sendRedirect("monitor?paymentId=${paymentId}")
    }
}
