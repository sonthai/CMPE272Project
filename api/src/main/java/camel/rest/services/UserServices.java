package camel.rest.services;

import camel.rest.dao.UserDao;
import camel.rest.model.User;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;

public class UserServices {
    public void login(Exchange exchange) {
       // User user = userDao.getUserInfo();
        //String bodyMessage = "Hello, world";
        User user = new User();
        user.setUserName("sdthai");
        user.setEmail("son.ccsf@gmail.com");
        user.setPassword("1234545");

        exchange.getIn().setBody(user);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }
}
