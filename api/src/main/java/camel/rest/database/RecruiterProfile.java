package camel.rest.database;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruiterProfile extends QueryObject {
    @Override
    public List<Map<String, Object>> parse(ResultSet rs) {
        List<Map<String, Object>> recruiterProfileList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            while (rs.next()) {
                map = new HashMap<>();
                map.put("companyInfo", rs.getString("companyInfo"));
                map.put("note", rs.getString("note"));
                map.put("email", rs.getString("email"));
                recruiterProfileList.add(map);
            }
        } catch (Exception e) {}

        return recruiterProfileList;
    }

}
