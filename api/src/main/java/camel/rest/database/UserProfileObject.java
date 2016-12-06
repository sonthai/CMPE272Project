package camel.rest.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileObject extends QueryObject {
    @Override
    public List<Map<String, Object>> parse(ResultSet rs) {
        List<Map<String, Object>> userProfileList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            while (rs.next()) {
                map = new HashMap<>();
                //map.put("userName", rs.getString("userName"));
                //map.put("id", rs.getInt("id"));
                map.put("work_experience", rs.getString("work_experience"));
                map.put("education", rs.getString("education"));
                map.put("skilss", rs.getString("skills"));
                userProfileList.add(map);
            }
        } catch (Exception e) {}

        return userProfileList;
    }
}