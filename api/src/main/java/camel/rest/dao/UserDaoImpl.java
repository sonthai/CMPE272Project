package camel.rest.dao;

import camel.rest.database.QueryObject;
import camel.rest.database.UserObject;
import camel.rest.domain.ResponseMessage;
import camel.rest.utils.Constants;
import camel.rest.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class UserDaoImpl implements UserDao {
    Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public ResponseMessage findUser(LinkedHashMap<String, Object> userData, boolean checkUserExist) {
        ResponseMessage response = null;
        if (checkUserExist) {
            LinkedHashMap<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("userName", userData.get("userName"));
            userData = tmp;
        } else {
            userData.put("password", encryptWithMD5((String) userData.get("password")));
        }

        QueryObject queryObject = new UserObject();
        queryObject.setOperation("SELECT");
        queryObject.setQueryFields(new String[] {"*"});
        queryObject.setTable("user");
        String whereClause = Utils.flattenKeyValuePair(userData, "AND");
        queryObject.setWhereClause(whereClause);

        queryObject.executeQuery();

        List<Map<String, Object>> rows = queryObject.getRecords();

        if (rows.size() == 0 && !checkUserExist) {
            response = Utils.constructMsg(1, Constants.FAIL_LOGIN, null);
        } else if (rows.size() > 0){
            if (!checkUserExist) {
                //List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                //Map<String, Object> map = new LinkedHashMap<>();
                //map.put("role", rows.get(0).get("role"));
                //resultList.add(map);
                response = Utils.constructMsg(0, Constants.SUCCESS_LOGIN, rows);
            } else {
                response = Utils.constructMsg(1, Constants.FAIL_REGISTER, null);
            }
        }

        return response;
    }

    @Override
    public ResponseMessage register(LinkedHashMap<String, Object> userData) {
        ResponseMessage response = findUser(userData, true);
        if (response == null) {
            userData.put("password", encryptWithMD5((String) userData.get("password")));
            QueryObject queryObject = new UserObject();
            queryObject.setOperation("INSERT");
            queryObject.setTable("user");
            List<String> insertValues = Utils.flattenMap(userData);
            queryObject.setValues(insertValues);
            queryObject.executeQuery();
            response = Utils.constructMsg(0, Constants.SUCCESS_REGISTER, null);
        }

        return  response;
    }

    private String encryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("Failed to encrypt password");
        }
        return null;
    }
}
