package camel.rest.services;

import camel.rest.dao.UserDao;
import camel.rest.dao.UserDaoImpl;
import camel.rest.model.User;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;

public class UserServices {
    UserDaoImpl userdao;

    public void login(Exchange exchange) {
        userdao = new UserDaoImpl();
        User user = userdao.findUser("");
        exchange.getIn().setBody(user);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void register(Exchange exchange) {
    }
}
