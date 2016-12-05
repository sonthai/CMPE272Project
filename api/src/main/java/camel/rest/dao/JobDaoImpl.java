package camel.rest.dao;

import camel.rest.domain.ResponseMessage;
import camel.rest.utils.Constants;
import camel.rest.utils.Utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class JobDaoImpl implements JobDao {
    @Override
    public ResponseMessage findJobs(LinkedHashMap<String, Object> jobQuery) {
        // Get Job from Dice API
        // query work: skill, city, areacode, state
        String query = flatQueryMap(jobQuery);

        List<Map<String, Object>> response = loadJobsFromDice(query);

        // Get Job from database

        return Utils.constructMsg(0, "Job List", response);
    }

    @Override
    public ResponseMessage applyHistory(String userName) {
        return null;
    }

    @Override
    public ResponseMessage applyJob(LinkedHashMap<String, Object> jobData) {
        return null;
    }

    private List<Map<String, Object>> loadJobsFromDice(String query) {
        String url = Constants.diceURL + "?" + query + "sort=2&sd=a";
        String response = Utils.doGet(url);

        return Utils.parseJobJsonString(response);
    }

    private String flatQueryMap(LinkedHashMap<String, Object> jobQuery) {
        StringBuilder res = new StringBuilder();

        for(Map.Entry<String, Object> entry: jobQuery.entrySet()) {
            if (res.length() > 0) {
                res.append("&");
            }
            res.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return res.toString();
    }


}
