package main;

import camel.rest.dao.UserDao;
import camel.rest.routes.RestRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
    public void run() throws Exception {
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext context = new SpringCamelContext(appContext);
        //context.addComponent("properties", new PropertiesComponent("sampleconfig.properties"));
        context.addRoutes(new RestRouteBuilder());
        context.start();
        System.in.read();
        context.stop();
    }

    public static void main(String[] args) throws Exception {
        new Starter().run();
    }
}