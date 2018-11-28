<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment Console</title>
        <style>
            ul.form {
                list-style: none;
                padding: 0;

                display: table;
                border-spacing: 0.2em;
            }

            ul.form > li {
                margin: .2em 0;

                display: table-row;
            }

            ul.form > li > * {
                display: table-cell;
            }
        </style>
    </head>
    <body>
        <h1>Payment Console</h1>

        <form method="POST">
            <ul class="form">
                <li>
                    <label for="paymentMethodId">Account Number:</label>
                    <select name="paymentMethodId">
                        <c:forEach items="${paymentMethods}" var="method">
                            <option value="${method.paymentMethodId}">${method.name}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <label for="amount">Amount:</label>
                    <input type="text" name="amount" />
                </li>
            </ul>

            <button type="submit">Submit</button>
        </form>
    </body>
</html>
