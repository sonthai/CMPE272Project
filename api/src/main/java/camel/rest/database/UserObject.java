package camel.rest.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserObject extends QueryObject {
    @Override
    public List<Map<String, Object>> parse(ResultSet rs) {
        List<Map<String, Object>> userList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            while (rs.next()) {
                map = new HashMap<>();
                map.put("userName", rs.getString("userName"));
                map.put("role", rs.getInt("role"));
                map.put("firstName", rs.getString("firstName"));
                map.put("lastName", rs.getString("lastName"));
                map.put("phoneNo", rs.getString("phoneNo"));
                userList.add(map);
            }
        } catch (Exception e) {}

        return userList;
    }
}
