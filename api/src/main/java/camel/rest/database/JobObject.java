package camel.rest.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobObject extends QueryObject {
    @Override
    public List<Map<String, Object>> parse(ResultSet rs) {
        List<Map<String, Object>> jobList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            while (rs.next()) {
                map = new HashMap<>();
                map.put("jobTitle", rs.getString("jobTitle"));
                map.put("company", rs.getInt("company"));
                map.put("date", rs.getDate("date"));
                String location =  rs.getString("city") + ", " + rs.getString("state");
                map.put("location", location);
                String detailUrl = rs.getString("detailUrl");
                if (detailUrl != null) {
                    map.put("detailUrl", detailUrl);
                }
                jobList.add(map);
            }
        } catch (Exception e) {}

        return jobList;
    }
}
