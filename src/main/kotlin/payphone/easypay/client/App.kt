package payphone.easypay.client

import payphone.easypay.ws.PaymentRequest
import payphone.easypay.ws.PaymentService
import payphone.easypay.ws.PaymentStatusValue
import java.net.URL
import javax.xml.namespace.QName
import javax.xml.ws.Service

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Client app.")
        println("Easypay by Payphone")
        println()

        val url = URL("http://localhost:9080/PaymentService.wsdl")
        val qname = QName("http://ws.easypay.payphone/", "PaymentService")

        val service = Service.create(url, qname)
        println("Service is created.")

        // Payment methods.

        val api = service.getPort(PaymentService::class.java)
        val methods = api.paymentMethods
        println("Payment methods are retrieved.")

        println()
        println("Available payment methods: ")
        for (method in methods) {
            println("- ${method.paymentMethodId}: ${method.name}")
        }

        // Payment request.

        val request = PaymentRequest()

        print("Payment Method ID: ")
        request.paymentMethodId = readLine()!!
        print("Amount: ")
        request.amount = readLine()!!.toBigDecimal()

        println()

        // Begin payment.

        val paymentId = api.beginPayment(request)
        println("Payment process has been started, ID = $paymentId")

        // Payment status.

        loop@ while (true) {
            Thread.sleep(1000)
            val status = api.getPaymentStatus(paymentId)

            when (status.status!!) {
                PaymentStatusValue.IN_PROGRESS ->
                    println("Status: In Progress")

                PaymentStatusValue.PENDING_OPEN_URL ->
                    println("Status: Pending: Open URL: ${status.pendingOpenUrlExtra!!.url}")

                PaymentStatusValue.OK -> {
                    println("Status: OK, amount = ${status.okExtra!!.amount}")
                    break@loop
                }

                PaymentStatusValue.FAIL -> {
                    println("Status: FAIL: ${status.failExtra!!.reason}")
                    break@loop
                }
            }
        }
    }
}
