# EasyPay (client)

**EasyPay** is a toy payment service made for IF4050 Service-Oriented Software Development course. This repository contains an example how to use and integrate **EasyPay** service.

The **EasyPay** itself is available here, at [nieltg/easypay](https://github.com/nieltg/easypay).

## Usage

### Payment Request Page

This page provides a mechanism to request a payment. This page can be accessed at `/easypay-client/request`.

![Payment Console](doc/Screenshot_2018-11-28%20Payment%20Console.png)

### Payment Monitor Page

This page provides a mechanism to listen to payment events. This page will be presented after making a payment request using Payment Request Page.

![Payment Monitor](doc/Screenshot_2018-11-28%20Payment%20Monitor.png)

## Integration

### API

#### `getPaymentMethods(): List<PaymentMethod>`

Usage of this method is available here, at [`PaymentRequestServlet.kt`](https://github.com/LazuliSound/easypay-client/blob/master/src/main/kotlin/payphone/easypay/client/PaymentRequestServlet.kt#L15). It is displayed to the user using a JSP page which is available at [`request.jsp`](https://github.com/LazuliSound/easypay-client/blob/master/src/main/webapp/WEB-INF/jsp/request.jsp#L34).

#### `beginPayment(paymentMethodId: String, amount: BigDecimal): String`

Usage of this method is available at [`PaymentRequestServlet.kt`](https://github.com/LazuliSound/easypay-client/blob/master/src/main/kotlin/payphone/easypay/client/PaymentRequestServlet.kt#L24).

#### `getPaymentEvents(paymentMethodId: String, lastEventId: Long?): PaymentEventsBlock`

Usage of this method is primarily available on JavaScript code at [`monitor.jsp`](https://github.com/LazuliSound/easypay-client/blob/master/src/main/webapp/WEB-INF/jsp/monitor.jsp#L26). Notice that this code at [`PaymentApi.kt`](https://github.com/LazuliSound/easypay-client/blob/master/src/main/kotlin/payphone/easypay/client/rs/PaymentApi.kt#L22) only redirect REST request which is understandable by JavaScript into SOAP request.

## Credits

**EasyPay** is bought to you by **payphone** team.

- 13515071 Daniel Pintara ([nieltg](https://github.com/nieltg))
- 13515136 Lazuardi Firdaus ([LazuliSound](https://github.com/LazuliSound))
- 18215036 	Ahmad Fawwaz Zuhdi ([18215036](https://github.com/18215036))
