<!DOCTYPE html>
<html>
    <head>
        <title>Payment Monitor</title>
    </head>
    <body>
        <h1>Payment Monitor</h1>
        <p class="control">
            <button id="handleFetchButton">Fetch</button>
            <input type="checkbox" name="autoFetchCheckbox" id="autoFetchCheckbox" checked />
                <label for="autoFetchCheckbox">Auto-fetch in 1s</label>
        </p>

        <div id="event-log">
        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous">
        </script>
        <script>
            $(document).ready(function() {
                let lastEventId = null;

                const fetchEvents = function() {
                    let lastEventIdFragment = 0;
                    $.ajax({
                        type: 'GET',
                        url: 'api/payment/events?paymentId=${paymentId}' + (lastEventId ?
                            "&lastEventId=" + lastEventId : ""),
                        success: function(data) {
                            console.log(data);

                            lastEventId = data.lastEventId;
                            $.each(data.events, function(i, entry) {
                                console.log(entry);

                                let moreElement = null;

                                switch (entry.type) {
                                    case 'SUCCESS':
                                    case 'AMOUNT_CHANGED':
                                        moreElement = $('<span />', {
                                            text: "Amount: " + entry.amount
                                        });
                                        break;
                                    case 'FAILURE':
                                        moreElement = $('<span />', {
                                            text: "Reason: " + entry.reason
                                        });
                                        break;
                                    case 'OPEN_URL':
                                        moreElement = $('<a />', {
                                            href: entry.urlToOpen,
                                            text: entry.urlToOpen,
                                            target: '_blank'
                                        });
                                        break;
                                    case 'ACCOUNT_NUMBER_AVAILABLE':
                                        moreElement = $('<span />', { text: entry.accountNumber });
                                        break;
                                }

                                let logElement = $('<div />');
                                logElement.append($('<b />', { text: entry.type + ': ' }));
                                moreElement && logElement.append(moreElement);

                                $('#event-log').prepend(logElement);
                            });
                        }
                    })
                };

                $('#handleFetchButton').click(fetchEvents);

                let intervalHandle = null

                const syncAutoFetchCheckbox = function() {
                    if (this.checked && !intervalHandle) {
                        intervalHandle = setInterval(fetchEvents, 1000);
                    } else if (!this.checked && intervalHandle) {
                        clearInterval(intervalHandle);
                    }
                };

                $('#autoFetchCheckbox').change(syncAutoFetchCheckbox);
                $('#autoFetchCheckbox').trigger('change');
            });
        </script>
    </body>
</html>
