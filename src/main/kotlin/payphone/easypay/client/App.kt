package payphone.easypay.client

import payphone.easypay.core.ws.PaymentEventType
import payphone.easypay.core.ws.PaymentRequest
import payphone.easypay.core.ws.PaymentService
import java.net.URL
import javax.xml.namespace.QName
import javax.xml.ws.Service

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Client app.")
        println("Easypay by Payphone")
        println()

        val url = URL("http://167.205.35.211:8080/easypay/PaymentService?wsdl")
        val qname = QName("http://ws.core.easypay.payphone/", "PaymentService")

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

        var lastEventId: Long? = null

        loop@ while (true) {
            Thread.sleep(1000)

            val eventsBlock = api.getPaymentEvents(paymentId, lastEventId)
            val events = eventsBlock.events

            println("EventBlock: len = ${events.size}, lastEventId = ${eventsBlock.lastEventId}")

            for (event in events) {
                when (event.type!!) {
                    PaymentEventType.OPEN_URL ->
                        println("Event: OPEN_URL, url = ${event.urlToOpen}")

                    PaymentEventType.AMOUNT_CHANGED ->
                        println("Event: AMOUNT_CHANGED, amount = ${event.amount}")

                    PaymentEventType.SUCCESS -> {
                        println("Event: SUCCESS, amount = ${event.amount}")
                        break@loop
                    }

                    PaymentEventType.FAILURE -> {
                        println("Event: FAILURE, reason = ${event.reason}")
                        break@loop
                    }

                }
            }

            lastEventId = eventsBlock.lastEventId
        }
    }
}
