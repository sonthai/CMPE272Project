package camel.rest.routes;

import camel.rest.services.UserServices;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet")
                .host("localhost")
                .port(6060)
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .get("/register").produces("application/json").to("direct:user_register");

        from("direct:user_register").bean(UserServices.class, "login").end();
    }
}
