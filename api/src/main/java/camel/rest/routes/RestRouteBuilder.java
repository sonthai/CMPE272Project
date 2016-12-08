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
                .enableCORS(true)
                .bindingMode(RestBindingMode.json);

        rest("/api")
                .post("/login").consumes("application/json").produces("application/json").to("direct:user_login")
                .post("/register").produces("application/json").to("direct:user_register")
                .post("/user/create-profile").consumes("application/json").to("direct:create_user_profile")
                .post("/user/update-profile").consumes("application/json").to("direct:update_user_profile")
                .post("/user/profile").consumes("application/json").to("direct:get_user_profile")
                .post("/job/list").produces("application/json").to("direct:job_list")
                .post("/job/tracking").produces("application/json").to("direct:job_tracking")
                .post("/job/apply").produces("application/json").to("direct:job_apply")
                .post("/job/create").produces("application/json").to("direct:job_create")
                .get("/job/company-list").produces("application/json").to("direct:company-list");

        from("direct:user_login").bean(UserServices.class, "login").end();
        from("direct:user_register").bean(UserServices.class, "register").end();
        from("direct:create_user_profile").bean(UserServices.class, "createProfile");
        from("direct:update_user_profile").bean(UserServices.class, "updateProfile");
        from("direct:get_user_profile").bean(UserServices.class, "getUserProfile");
        from("direct:job_list").bean(JobServices.class, "findJobs").end();
        from("direct:job_tracking").bean(JobServices.class, "jobTracking").end();
        from("direct:job_apply").bean(JobServices.class, "applyJob").end();
        from("direct:job_create").bean(JobServices.class, "createJob").end();
        from("direct:company-list").bean(JobServices.class, "getCompanyList").end();

    }
}
