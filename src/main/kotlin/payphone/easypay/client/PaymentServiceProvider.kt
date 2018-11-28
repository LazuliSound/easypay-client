package payphone.easypay.client

import payphone.easypay.core.ws.PaymentService
import java.net.URL
import javax.annotation.ManagedBean
import javax.xml.namespace.QName
import javax.xml.ws.Service

@ManagedBean
open class PaymentServiceProvider {
    private var cachedApi: PaymentService? = null

    fun getApi(): PaymentService {
        val localApi = cachedApi

        if (localApi != null) {
            return localApi
        }

        val url = URL("http://167.205.35.211:8080/easypay/PaymentService?wsdl")
        val qName = QName(
                "http://ws.core.easypay.payphone/", "PaymentService")

        val service = Service.create(url, qName)
        val newApi = service.getPort(PaymentService::class.java)
        cachedApi = newApi

        return newApi
    }
}
