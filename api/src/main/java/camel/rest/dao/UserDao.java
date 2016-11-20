package camel.rest.dao;


import camel.rest.model.User;


public interface UserDao {
    public User findUser(String userName);

    public void register(User user);
}
