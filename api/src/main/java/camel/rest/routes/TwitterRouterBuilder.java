package camel.rest.routes;

import camel.rest.services.TwitterService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class TwitterRouterBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet")
                .host("localhost")
                .port(9191)
                .bindingMode(RestBindingMode.auto);

        rest("/twitter")
                .get("/user_timeline").produces("application/json").to("direct:user_timeline");

        from("direct:user_timeline").bean(new TwitterService(), "getUserTimeline");
    }
}
