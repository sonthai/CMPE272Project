package camel.rest.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JobHistory extends QueryObject {
    @Override
    public List<Map<String, Object>> parse(ResultSet rs) {
        List<Map<String, Object>> jobList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            while (rs.next()) {
                map = new HashMap<>();
                map.put("jobTitle", rs.getString("jobTitle"));
                map.put("company", rs.getString("companyID"));
                map.put("date", rs.getDate("date"));

                map.put("id", rs.getInt("id"));
                map.put("detailUrl", rs.getString("detailUrl"));
                map.put("location", rs.getString("location"));
                map.put("userName", rs.getString("userName"));


                jobList.add(map);
            }
        } catch (Exception e) {
        }

        return jobList;
    }
}
