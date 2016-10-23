package services;


import org.apache.camel.Exchange;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldService {
    public void sayHello(Exchange exchange) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("message", "Hello, world");
        exchange.getIn().setBody(message);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }
}
