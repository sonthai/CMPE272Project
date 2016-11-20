package camel.rest;

import camel.rest.database.DatabaseConnection;
import camel.rest.routes.RestRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Starter {
    public void run() throws Exception {
        CamelContext context =  new DefaultCamelContext();
        //context.addComponent("properties", new PropertiesComponent("sampleconfig.properties"));
        context.addRoutes(new RestRouteBuilder());
        //DatabaseConnection conn = new DatabaseConnection();
        //conn.getDataSource();
        context.start();
        System.in.read();
        context.stop();
    }



    public static void main(String[] args) throws Exception {
        new Starter().run();
    }
}