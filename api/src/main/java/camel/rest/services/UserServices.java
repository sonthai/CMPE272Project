package camel.rest.services;

import camel.rest.dao.UserDaoImpl;
import camel.rest.domain.ResponseMessage;
import org.apache.camel.Exchange;

import java.util.LinkedHashMap;

public class UserServices {
    UserDaoImpl userdao;

    public void login(Exchange exchange) {
        userdao = new UserDaoImpl();
        LinkedHashMap<String, Object> userData = (LinkedHashMap) exchange.getIn().getBody();

        ResponseMessage msg = userdao.findUser(userData, false);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
    }

    public void register(Exchange exchange) {
        userdao = new UserDaoImpl();
        LinkedHashMap<String, Object> userData = (LinkedHashMap) exchange.getIn().getBody();
        ResponseMessage msg = userdao.register(userData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");

    }

    public void createProfile(Exchange exchange) {
        userdao = new UserDaoImpl();
        LinkedHashMap<String, Object> userData = (LinkedHashMap) exchange.getIn().getBody();
        ResponseMessage msg = userdao.createProfile(userData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");

    }

    public void updateProfile(Exchange exchange) {
        userdao = new UserDaoImpl();
        LinkedHashMap<String, Object> userData = (LinkedHashMap) exchange.getIn().getBody();
        ResponseMessage msg = userdao.updateProfile(userData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");

    }

    public void getUserProfile(Exchange exchange) {
        userdao = new UserDaoImpl();
        LinkedHashMap<String, Object> userData = (LinkedHashMap) exchange.getIn().getBody();
        ResponseMessage msg = userdao.getProfile(userData);
        exchange.getIn().setBody(msg);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, "200");

    }
}
