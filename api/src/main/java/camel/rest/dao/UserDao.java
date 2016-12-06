package camel.rest.dao;


import camel.rest.domain.ResponseMessage;

import java.util.LinkedHashMap;


public interface UserDao {
    public ResponseMessage findUser(LinkedHashMap<String, Object> userData, boolean checkUserExists);

    public ResponseMessage register(LinkedHashMap<String, Object> userData);
    public ResponseMessage updateProfile(LinkedHashMap<String, Object> userData);
    public ResponseMessage createProfile(LinkedHashMap<String, Object> userData);
    public ResponseMessage getProfile(LinkedHashMap<String, Object> userData);
}
