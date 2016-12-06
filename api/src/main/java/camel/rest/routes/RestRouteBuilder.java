package camel.rest.routes;

import camel.rest.services.JobServices;
import camel.rest.services.UserServices;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet")
                .host("localhost")
                .port(8181)
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .post("/login").consumes("application/json").produces("application/json").to("direct:user_login")
                .post("/register").produces("application/json").to("direct:user_register")
                .post("/user/create-profile").consumes("application/json").to("direct:create_user_profile")
                .get("/user/profile").to("direct:get_user-profile")
                .post("/job/list").produces("application/json").to("direct:job_list")
                .post("/job/apply").produces("application/json").to("direct:job_apply")
                .post("/job/history").produces("application/json").to("direct:job_history")
                .post("/job/create").produces("application/json").to("direct:job_create");

        from("direct:user_login").bean(UserServices.class, "login").end();
        from("direct:user_register").bean(UserServices.class, "register").end();
        from("direct:create_user_profile").bean(UserServices.class, "updateProfile");
        from("direct:get_user-profile").bean(UserServices.class, "getUserProfile");
        //from("direct:job_list").bean(JobServices.class, "findJobs").end();
        //from("direct:job_apply").bean(JobServices.class, "applyJob").end();
        //from("direct:job_history").bean(JobServices.class, "applyHistory").end();
        //from("direct:job_create").bean(JobServices.class, "createJob").end();

    }
}
