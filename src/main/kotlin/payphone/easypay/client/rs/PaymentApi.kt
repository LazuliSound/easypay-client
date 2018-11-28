package payphone.easypay.client.rs

import payphone.easypay.client.PaymentServiceProvider
import payphone.easypay.core.ws.PaymentEventsBlock
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam

@Path("/payment")
@Produces("application/json")
open class PaymentApi {
    @Inject
    private lateinit var serviceProvider: PaymentServiceProvider

    @GET
    @Path("/events")
    open fun getStatus(
            @QueryParam("paymentId") paymentId: String,
            @QueryParam("lastEventId") lastEventId: Long?): PaymentEventsBlock {
        return serviceProvider.getApi().getPaymentEvents(paymentId, lastEventId)
    }
}
