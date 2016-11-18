package camel.rest.dao;


import camel.rest.model.User;
import org.apache.camel.language.Bean;


public class UserDao {
    public User getUserInfo() {
        User user = new User();
        user.setUserName("sdthai");
        user.setEmail("son.ccsf@gmail.com");
        user.setPassword("1234545");

        return user;
    }
}
