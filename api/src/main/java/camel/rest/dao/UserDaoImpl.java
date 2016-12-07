package camel.rest.dao;

import camel.rest.database.QueryObject;
import camel.rest.database.RecruiterProfile;
import camel.rest.database.UserObject;
import camel.rest.database.UserProfileObject;
import camel.rest.domain.ResponseMessage;
import camel.rest.utils.Constants;
import camel.rest.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    @Override
    public ResponseMessage updateProfile(LinkedHashMap<String, Object> userData) {
        ResponseMessage response = null;
        // 0 is job seeker, 1 is job recruiter
        int role = (int) userData.remove("role");

        // Update user DB
        QueryObject userQuery = new UserObject();
        userQuery.setOperation("UPDATE");
        userQuery.setTable("user");


        Map<String, Object> userDB = new LinkedHashMap<>();
        if (userData.get("phoneNo") != null) {
            userDB.put("phoneNo", userData.get("phoneNo"));
            userData.remove("phoneNo");
        }
        if (userData.get("password") != null) {
            userDB.put("password", encryptWithMD5((String) userData.get("password")));
            userData.remove("password");
        }

        if (userDB.size() > 0) {
            String updateData = Utils.flattenKeyValuePair(userDB, ",");
            List<String> data = new ArrayList<String>();
            data.add(updateData);
            userQuery.setValues(new ArrayList<String>(data));
            String whereClause = "userName='" + userData.get("userName") + "'";
            userQuery.setWhereClause(whereClause);
            userQuery.executeQuery();
        }

        // Update user profile
        if (userData.size() > 0) {
            if (role == 0) {
                QueryObject profileQuery = new UserProfileObject();
                profileQuery.setOperation("UPDATE");
                profileQuery.setTable("user_profile");
                String userName = (String) userData.remove("userName");
                String profileData = Utils.flattenKeyValuePair(userData, ",");
                List<String> data = new ArrayList<String>();
                data.add(profileData);
                profileQuery.setValues(new ArrayList<String>(data));
                String whereClause = "userName='" + userName + "'";
                profileQuery.setWhereClause(whereClause);
                profileQuery.executeQuery();
            } else if (role == 1){
                QueryObject profileQuery = new RecruiterProfile();
                profileQuery.setOperation("UPDATE");
                profileQuery.setTable("recruiter_profile");
                String userName = (String) userData.remove("userName");
                String profileData = Utils.flattenKeyValuePair(userData, ",");
                List<String> data = new ArrayList<String>();
                data.add(profileData);
                profileQuery.setValues(new ArrayList<String>(data));
                String whereClause = "userName='" + userName + "'";
                profileQuery.setWhereClause(whereClause);
                profileQuery.executeQuery();

            }
        }


        return Utils.constructMsg(0, Constants.SUCCESS_UPDATE_PROFILE, null);
    }

    @Override
    public ResponseMessage createProfile(LinkedHashMap<String, Object> userData) {
        ResponseMessage response = null;
        // 0 is job seeker, 1 is job recruiter
        int role = (int) userData.remove("role");

        // Update user/recruiter profile table
        QueryObject queryObject = new QueryObject();
        queryObject.setOperation("INSERT");

        List<String> insertValues = Utils.flattenMap(userData);
        queryObject.setValues(insertValues);
        if (role == 0) {
            queryObject.setTable("user_profile");
        } else {
            queryObject.setTable("recruiter_profile");
        }
        queryObject.executeQuery();
        response = Utils.constructMsg(0, Constants.SUCCESS_CREATE_PROFILE, null);

        return response;
    }

    @Override
    public ResponseMessage getProfile(LinkedHashMap<String, Object> userData) {
        ResponseMessage response = null;
        // 0 is job seeker, 1 is job recruiter
        int role = (int) userData.remove("role");

        QueryObject userObject = new UserObject();
        userObject.setOperation("SELECT");
        userObject.setQueryFields(new String[] {"*"});
        userObject.setTable("user");
        String whereClause = Utils.flattenKeyValuePair(userData, "AND");
        userObject.setWhereClause(whereClause);

        userObject.executeQuery();

        List<Map<String, Object>> rows = userObject.getRecords();
        List<Map<String, Object>> profileRow = null;
        if (role == 0) {
            QueryObject queryObject = new UserProfileObject();
            queryObject.setOperation("SELECT");
            queryObject.setQueryFields(new String[]{"*"});
            queryObject.setTable("user_profile");
            whereClause = Utils.flattenKeyValuePair(userData, "AND");
            queryObject.setWhereClause(whereClause);

            queryObject.executeQuery();
            profileRow = queryObject.getRecords();
        } else if (role == 1) {
            QueryObject queryObject = new RecruiterProfile();
            queryObject.setOperation("SELECT");
            queryObject.setQueryFields(new String[]{"*"});
            queryObject.setTable("recruiter_profile");
            whereClause = Utils.flattenKeyValuePair(userData, "AND");
            queryObject.setWhereClause(whereClause);

            queryObject.executeQuery();
            profileRow = queryObject.getRecords();
        }

        if (profileRow.size() > 0) {
            rows.get(0).putAll(profileRow.get(0));
            rows.get(0).put("isNewUser", 0);
        } else {
            rows.get(0).put("isNewUser", 1);
        }

        response = Utils.constructMsg(0, "", rows);
        return response;
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
