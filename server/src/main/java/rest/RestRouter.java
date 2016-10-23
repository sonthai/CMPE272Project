package rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import services.HelloWorldService;

public class RestRouter extends RouteBuilder {
    public void configure() throws Exception {
        restConfiguration().component("restlet")
                //.host("127.0.0.1")
                .port(8080)
                .bindingMode(RestBindingMode.auto);

        rest("/app")
                .get("/sayHello").produces("application/json").to("direct:sayHello");

        from("direct:sayHello").bean(new HelloWorldService(), "sayHello");

    }
}
