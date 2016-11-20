package camel.rest.routes;

import camel.rest.services.UserServices;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet")
                .host("localhost")
                .port(4000)
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .get("/login").produces("application/json").to("direct:user_login")
                .post("/register").produces("application/json").to("direct:user_register");

        from("direct:user_login").bean(UserServices.class, "login").end();
        from("direct:user_register").bean(UserServices.class, "register").end();

    }
}
